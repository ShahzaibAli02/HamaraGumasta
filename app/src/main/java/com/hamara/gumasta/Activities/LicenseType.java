package com.hamara.gumasta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamara.gumasta.Adapters.CategoryAdapter;
import com.hamara.gumasta.Model.Categories;
import com.hamara.gumasta.Model.Category;
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

public class LicenseType extends AppCompatActivity  {



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
                        recyclerView.setAdapter(new CategoryAdapter(categoriesList,getActivity()));
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




}