package com.hamara.gumasta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hamara.gumasta.R;

public class BusinessNature extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_nature);
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickSubmit(View view) {
        startActivity(new Intent(this,ThankYouScreen.class));
    }
}