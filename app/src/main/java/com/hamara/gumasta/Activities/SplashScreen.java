package com.hamara.gumasta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.hamara.gumasta.Activities.Authentications.Login_SignUp;
import com.hamara.gumasta.R;

public class SplashScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


       /* RetrofitClient.createClient().signup("shzaib","alishahzaib@gmail.com","030387687394","12345").enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("ERROR  :"+t.getMessage());
            }

        });

        */

        new CountDownTimer(3000,1000)
        {
            @Override
            public void onFinish()
            {

                finish();
                startActivity(new Intent(SplashScreen.this, Login_SignUp.class));

            }

            @Override
            public void onTick(long millisUntilFinished) {
                // DO NOTHING
            }
        }.start();


    }


}