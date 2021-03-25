package com.hamara.gumasta.Activities.Authentications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hamara.gumasta.R;

public class CreateNewPass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_pass);
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickSave(View view)
    {

        Toast.makeText(this,"Save Clicked",Toast.LENGTH_LONG).show();

    }
}