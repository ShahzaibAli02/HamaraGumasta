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
import com.hamara.gumasta.Adapters.ServiceAdapter;
import com.hamara.gumasta.Model.Area;
import com.hamara.gumasta.Model.Areas;
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

public class LicenseType extends AppCompatActivity {



    RecyclerView recyclerView;
    List<Service>  servicesList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_type);
        initViews();
        loadServices();
    }

    private void initViews()
    {
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void loadServices()
    {

        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(this);
        progressDialog.show();
        RetrofitClient.createClient().services().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                progressDialog.dismiss();
                try
                {
                    String json=response.body().string();

                    Type type=new TypeToken<Services>(){}.getType();

                    Services services=new Gson().fromJson(json,type);

                    if(services.getStatus()==200 && services.getMessage().equalsIgnoreCase("Success"))
                    {
                        servicesList.clear();
                        servicesList.addAll(services.getServices());
                        recyclerView.setAdapter(new ServiceAdapter(servicesList,getActivity()));
                    }
                    else
                    {
                        Util.showSnackBar(getActivity(),services.getMessage());
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
            Util.showSnackBar(this,"Please Select Atleast One License Type");
            return;
        }
        else
        {
            report.setServices(licenseType.toString());
            startActivity(new Intent(this,BusinessNature.class));
        }



    }
}