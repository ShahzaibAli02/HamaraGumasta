package com.hamara.gumasta.Activities.Authentications;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hamara.gumasta.R;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onclickSend(View view)
    {
        startActivity(new Intent(this, OtpVerification.class));
    }
}