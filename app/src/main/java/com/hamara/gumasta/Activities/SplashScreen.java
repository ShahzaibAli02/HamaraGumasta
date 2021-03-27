package com.hamara.gumasta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.hamara.gumasta.Activities.Authentications.Login_SignUp;
import com.hamara.gumasta.R;
import com.hamara.gumasta.SharedPreference.SharedPref;

public class SplashScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        new CountDownTimer(3000,1000)
        {
            @Override
            public void onFinish()
            {


                finish();

                if(SharedPref.isRemMe(SplashScreen.this))
                {
                    startActivity(new Intent(SplashScreen.this, SetLocation.class));
                }
                else
                {
                    startActivity(new Intent(SplashScreen.this, Login_SignUp.class));
                }

            }

            @Override
            public void onTick(long millisUntilFinished) {
                // DO NOTHING
            }
        }.start();


    }


}