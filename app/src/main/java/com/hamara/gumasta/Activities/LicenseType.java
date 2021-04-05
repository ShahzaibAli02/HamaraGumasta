package com.hamara.gumasta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamara.gumasta.Adapters.CategoryAdapter;
import com.hamara.gumasta.Adapters.ServiceAdapter;
import com.hamara.gumasta.Interfaces.RecyclerViewStateListener;
import com.hamara.gumasta.Model.Area;
import com.hamara.gumasta.Model.Areas;
import com.hamara.gumasta.Model.Categories;
import com.hamara.gumasta.Model.Category;
import com.hamara.gumasta.Model.Report;
import com.hamara.gumasta.Model.Service;
import com.hamara.gumasta.Model.Services;
import com.hamara.gumasta.ProgressDialogManager;
import com.hamara.gumasta.R;
import com.hamara.gumasta.RetrofitClient;
import com.hamara.gumasta.Util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LicenseType extends AppCompatActivity  implements RecyclerViewStateListener {



    RecyclerView recyclerView;
    List<Service>  servicesList=new ArrayList<>();
    List<Category>  categoriesList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_type);
        initViews();
        loadCategories();
    }

    private void loadCategories()
    {
        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(this);
        progressDialog.show();
        RetrofitClient.createClient().categories().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                progressDialog.dismiss();
                try
                {
                    String json=response.body().string();

                    Type type=new TypeToken<Categories>(){}.getType();

                    Categories categories=new Gson().fromJson(json,type);

                    if(categories.getStatus()==200 && categories.getMessage().equalsIgnoreCase("Success"))
                    {
                        categoriesList.clear();
                        categoriesList.addAll(categories.getCategories());
                        recyclerView.setAdapter(new CategoryAdapter(categoriesList,getActivity(),LicenseType.this));
                    }
                    else
                    {

                        Util.showSnackBar(getActivity(),categories.getMessage());
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
                progressDialog.dismiss();
                Util.showSnackBar(getActivity(),t.getMessage());
            }
        });
    }

    private void initViews()
    {
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private Activity getActivity() {
        return  this;
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickSubmit(View view)
    {

        StringBuilder licenseType= null;
        Report report=Report.getInstance();
        for(Service service:servicesList)
        {

            if(service.getChecked())
            {
                if(licenseType==null)
                {
                    licenseType = new StringBuilder(service.getTitle());
                }
                else
                {
                    licenseType.append(",").append(service.getTitle());
                }
            }


        }

        if(licenseType==null)
        {
            Util.showSnackBar(this,"Please Select Atleast One Service Type");
            return;
        }
        else
        {
            report.setServices(licenseType.toString());
            startActivity(new Intent(this,BusinessNature.class));
        }



    }

    @Override
    public void onCheckedStateChanged(Service service, boolean isChecked)
    {

        if(isChecked)
        {
            addifNotExsist(service);
        }
        else
        {
            remvifExsist(service);
        }

    }

    @Override
    public void onClick(View view) {
        onClickSubmit(view);
    }

    private void remvifExsist(Service service) {
        for (int i = 0, servicesListSize = servicesList.size(); i < servicesListSize; i++)
        {
            Service service1 = servicesList.get(i);
            if (service1.getId().equals(service.getId())) {
                servicesList.remove(i);
                return;
            }
        }

    }

    private void addifNotExsist(Service service)
    {

        for(Service service1:servicesList)
        {
            if(service1.getId().equals(service.getId()))
            {
                return;
            }
        }
        servicesList.add(service);

    }
}