package com.hamara.gumasta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamara.gumasta.Adapters.ServiceAdapter;
import com.hamara.gumasta.Model.Report;
import com.hamara.gumasta.Model.Service;
import com.hamara.gumasta.ProgressDialogManager;
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

public class ServicesActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<Service> servicesList=new ArrayList<>();
    String Cat_Id="-1";
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        Cat_Id=getIntent().getExtras().getString("Cat_Id","-1");
        initViews();
        loadCategories();
    }

    private void loadCategories()
    {
        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(this);
        progressDialog.show();
        RetrofitClient.createClient().services(Cat_Id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {

                progressDialog.dismiss();
                try
                {
                    String json=response.body().string();

                    Type type=new TypeToken<com.hamara.gumasta.Model.Services>(){}.getType();

                    com.hamara.gumasta.Model.Services services=new Gson().fromJson(json,type);
                    servicesList.clear();

                    servicesList.addAll(services.getServices());

                    if(services.getStatus()==200 && services.getMessage().equalsIgnoreCase("Success") && services.getServices()!=null && services.getServices().size()>0)
                    {

                        recyclerView.setAdapter(new ServiceAdapter(servicesList, getActivity()));
                    }
                    else
                    if(services.getServices()!=null || services.getServices().size()<0)
                    {

                        btnSubmit.setVisibility(View.GONE);
                        Snackbar.make(findViewById(android.R.id.content),"No services found for this category", BaseTransientBottomBar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }).show();
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
            }
        });
    }

    private void initViews()
    {
        recyclerView=findViewById(R.id.recyclerView);
        btnSubmit=findViewById(R.id.btnSubmit);
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
}