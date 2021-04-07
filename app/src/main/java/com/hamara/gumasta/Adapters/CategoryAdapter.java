package com.hamara.gumasta.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamara.gumasta.Activities.ServicesActivity;
import com.hamara.gumasta.Model.Category;
import com.hamara.gumasta.Model.Services;
import com.hamara.gumasta.R;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> listData;
    Context context;

    public CategoryAdapter(List<Category> listData, Context context) {
        this.listData = listData;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, ServicesActivity.class).putExtra("Cat_Id",category.getId()));
            }
        });
    }



    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView txtCatName;
        public ViewHolder(View itemView)
        {
            super(itemView);
            txtCatName=itemView.findViewById(R.id.txtCatName);
        }
    }

}
