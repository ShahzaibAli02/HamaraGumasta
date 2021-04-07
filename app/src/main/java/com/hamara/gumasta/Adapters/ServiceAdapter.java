package com.hamara.gumasta.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hamara.gumasta.Model.Service;
import com.hamara.gumasta.R;

import java.util.List;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private List<Service> listData;
    Context context;
    public ServiceAdapter(List<Service> listData, Context context) {
        this.listData = listData;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lyt_service,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        Service service=listData.get(position);
        holder.txtLicense.setText(service.getTitle());
        holder.txtLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.checkboxLicense.setChecked(!holder.checkboxLicense.isChecked());

            }
        });
        holder.checkboxLicense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                service.setChecked(isChecked);
                listData.set(position,service);
            }
        });

       holder.checkboxLicense.setChecked(service.getChecked());

    }




    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView txtLicense;
        CheckBox checkboxLicense;
        public ViewHolder(View itemView) {
            super(itemView);

            txtLicense=itemView.findViewById(R.id.txtLicense);
            checkboxLicense=itemView.findViewById(R.id.checkboxLicense);




        }
    }

}
