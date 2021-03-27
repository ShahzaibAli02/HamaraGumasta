package com.hamara.gumasta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.hamara.gumasta.Model.Report;
import com.hamara.gumasta.R;
import com.hamara.gumasta.Util;

import java.util.HashMap;

public class LicenseType extends AppCompatActivity {


    CheckBox checkboxGstLic;
    CheckBox checkboxITR;
    CheckBox checkboxLabour;
    CheckBox checkboxFood;


    HashMap<Integer,String> checkBoxText=new HashMap<Integer, String>();
    HashMap<Integer,CheckBox> checkBoxHashMap=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_type);
        initViews();
    }

    private void initViews()
    {






        checkboxGstLic=findViewById(R.id.checkboxGstLic);
        checkboxITR=findViewById(R.id.checkboxITR);
        checkboxLabour=findViewById(R.id.checkboxLabour);
        checkboxFood=findViewById(R.id.checkboxFood);

        checkBoxText.put(R.id.checkboxGstLic,"GST License");
        checkBoxText.put(R.id.checkboxITR,"ITR License");
        checkBoxText.put(R.id.checkboxLabour,"LABOUR License");
        checkBoxText.put(R.id.checkboxFood,"FOOD License");

        checkBoxHashMap.put(R.id.txtGstLicense,checkboxGstLic);
        checkBoxHashMap.put(R.id.txtITRLicense,checkboxITR);
        checkBoxHashMap.put(R.id.txtLabourLicense,checkboxLabour);
        checkBoxHashMap.put(R.id.txtFoodLicense,checkboxFood);



    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickSubmit(View view)
    {

        StringBuilder licenseType= null;
        Report report=Report.getInstance();
        for(CheckBox checkBox:new CheckBox[]{checkboxGstLic,checkboxITR,checkboxFood,checkboxLabour})
        {

            if(checkBox.isChecked())
            {
                if(licenseType==null)
                {
                    licenseType = new StringBuilder(checkBoxText.get(checkBox.getId()));
                }
                else
                {
                    licenseType.append(",").append(checkBoxText.get(checkBox.getId()));
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

    public void onClickTxtLicenseType(View view)
    {

        CheckBox checkBox = checkBoxHashMap.get(view.getId());
        checkBox.setChecked(!checkBox.isChecked());

    }
}