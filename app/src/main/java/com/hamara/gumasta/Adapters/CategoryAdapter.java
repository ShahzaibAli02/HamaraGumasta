package com.hamara.gumasta.Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.hamara.gumasta.Interfaces.RecyclerViewStateListener;
import com.hamara.gumasta.Model.Categories;
import com.hamara.gumasta.Model.Category;
import com.hamara.gumasta.Model.Service;
import com.hamara.gumasta.Model.Services;
import com.hamara.gumasta.R;
import com.hamara.gumasta.RetrofitClient;
import com.hamara.gumasta.Util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> listData;
    Context context;
    RecyclerViewStateListener onServiceCheckStateChanged;

    public CategoryAdapter(List<Category> listData, Context context,RecyclerViewStateListener onServiceCheckStateChanged) {
        this.listData = listData;
        this.onServiceCheckStateChanged=onServiceCheckStateChanged;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lyt_category,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {


        Category category=listData.get(position);
        holder.txtCatName.setText(category.getCategory());
        loadSubItems(holder,position);
    }

    private void loadSubItems(ViewHolder holder, int position)
    {

        holder.recyclerView.setVisibility(View.GONE);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        holder.spin_kit.setVisibility(View.VISIBLE);

        if(position==listData.size()-1)
        {
            holder.btnSubmit.setVisibility(View.VISIBLE);
            holder.btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onServiceCheckStateChanged.onClick(v);
                }
            });
        }
        else
            holder.btnSubmit.setVisibility(View.GONE);
        RetrofitClient.createClient().services(listData.get(position).getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {

                holder.spin_kit.setVisibility(View.GONE);
                try
                {
                    String json=response.body().string();

                    Type type=new TypeToken<Services>(){}.getType();

                    Services services=new Gson().fromJson(json,type);

                    if(services.getStatus()==200 && services.getMessage().equalsIgnoreCase("Success") && services.getServices()!=null && services.getServices().size()>0)
                    {
                        holder.recyclerView.setVisibility(View.VISIBLE);
                        holder.recyclerView.setAdapter(new ServiceAdapter(services.getServices(), getActivity(), new RecyclerViewStateListener() {
                            @Override
                            public void onCheckedStateChanged(Service service, boolean isChecked) {
                              onServiceCheckStateChanged.onCheckedStateChanged(service,isChecked);
                            }

                            @Override
                            public void onClick(View view)
                            {
                               // DO NOTHING
                            }
                        }));
                    }

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Util.showSnackBar(getActivity(),"ERROR");
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
               holder.recyclerView.setVisibility(View.GONE);
                holder.spin_kit.setVisibility(View.GONE);
            }
        });
    }

    private Activity getActivity() {
        return (Activity) context;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView txtCatName;
        RecyclerView recyclerView;
        SpinKitView spin_kit;
        Button btnSubmit;
        public ViewHolder(View itemView)
        {
            super(itemView);
            spin_kit=itemView.findViewById(R.id.spin_kit);
            btnSubmit=itemView.findViewById(R.id.btnSubmit);
            txtCatName=itemView.findViewById(R.id.txtCatName);
            recyclerView=itemView.findViewById(R.id.recyclerView);
        }
    }

}
