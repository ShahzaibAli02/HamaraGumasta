package com.hamara.gumasta.Activities.Authentications;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hamara.gumasta.Activities.Authentications.CreateNewPass;
import com.hamara.gumasta.R;

public class VerifyPhoneNum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_num);
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickVerify(View view) {
        startActivity(new Intent(this, CreateNewPass.class));
    }
}