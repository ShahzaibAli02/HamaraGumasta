package com.hamara.gumasta;

import com.hamara.gumasta.Interfaces.GumastaService;

import retrofit2.Retrofit;

public class RetrofitClient
{

   private static String BASE_URL="http://dailindia.in/public/gumasta/index.php/api/";


    public static GumastaService createClient()
    {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build().create(GumastaService.class);
    }

}
