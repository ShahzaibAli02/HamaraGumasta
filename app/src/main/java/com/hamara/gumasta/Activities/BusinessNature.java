package com.hamara.gumasta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamara.gumasta.Model.ApiResponse;
import com.hamara.gumasta.Model.Report;
import com.hamara.gumasta.Model.User;
import com.hamara.gumasta.ProgressDialogManager;
import com.hamara.gumasta.R;
import com.hamara.gumasta.RetrofitClient;
import com.hamara.gumasta.SharedPreference.SharedPref;
import com.hamara.gumasta.Util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessNature extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{


    EditText editTextBusNature;
    CheckBox checkboxImmediately;
    CheckBox checkboxlt30;
    HashMap<Integer,CheckBox> checkBoxHashMap=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_nature);
        initViews();
    }

    private void initViews()
    {

        editTextBusNature=findViewById(R.id.editTextBusNature);



        checkboxImmediately=findViewById(R.id.checkboxImmediately);
        checkboxlt30=findViewById(R.id.checkboxlt30);
        checkboxlt30.setOnCheckedChangeListener(this);
        checkboxImmediately.setOnCheckedChangeListener(this);



        checkBoxHashMap.put(R.id.txtImmediatley,checkboxImmediately);
        checkBoxHashMap.put(R.id.txtLT30,checkboxlt30);
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickSubmit(View view)
    {

        Report report=Report.getInstance();
        if(TextUtils.isEmpty(editTextBusNature.getText()))
        {
            editTextBusNature.setError("Required Field");
            editTextBusNature.requestFocus();
            return;
        }
        if(checkboxImmediately.isChecked())
        {
            report.setPlaining_type("immediately");
        }
        else
        if(checkboxlt30.isChecked())
        {
            report.setPlaining_type("Less than 30 days");
        }
        else
        {
            Util.showSnackBar(this,"Please Select Planning Type");
            return;
        }
        report.setNature_business(editTextBusNature.getText().toString());
        report.updateCreated_at();


        uploadReport();

    }

    private void uploadReport()
    {

        Report report=Report.getInstance();
        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(getActivity());
        progressDialog.show();
        RetrofitClient.createClient().report(report.getUser_id(),report.getName(),report.getEmail(),report.getPhone(),report.getState(),report.getCity(),report.getArea(),report.getServices(),report.getPlaining_type(),report.getCreated_at(),report.getNature_business()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {

                progressDialog.dismiss();
                try
                {
                    String json=response.body().string();
                    System.out.println("JSON : "+json);
                    Type type=new TypeToken<ApiResponse>(){}.getType();

                    ApiResponse apiResponse=new Gson().fromJson(json,type);

                    if(apiResponse.getMessage().equalsIgnoreCase("report submited Successfully"))
                    {

                        startActivity(new Intent(getActivity(), ThankYouScreen.class));
                    }
                    else
                    {
                        //  Util.showSnackBar(getActivity(),"Phone Or Password Is Invalid");
                        Util.showSnackBar(getActivity(),apiResponse.getMessage());
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
        return this;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


        if(isChecked)
        {
            if(buttonView.getId()==R.id.checkboxImmediately)
            {
                checkboxlt30.setChecked(false);
            }
            else
            {
                checkboxImmediately.setChecked(false);
            }
        }

    }



    public void onClickTextPlanning(View view) {
        CheckBox checkBox = checkBoxHashMap.get(view.getId());
        checkBox.setChecked(!checkBox.isChecked());
    }
}