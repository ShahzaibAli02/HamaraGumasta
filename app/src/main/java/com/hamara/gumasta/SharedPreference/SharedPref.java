package com.hamara.gumasta.SharedPreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamara.gumasta.Model.User;

import java.lang.reflect.Type;

public class SharedPref
{



    public  static  void SaveUser(Context context,String json)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.hamara.gumasta", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Response",json);
        editor.apply();

    }
    public  static  void setRemMe(Context context,boolean isRem)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.hamara.gumasta", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("RemMe",isRem);
        editor.apply();

    }
    public  static  boolean isRemMe(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.hamara.gumasta", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("RemMe",false);

    }

    public  static User getUser(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.hamara.gumasta", Context.MODE_PRIVATE);
        String response = sharedPreferences.getString("Response", "");
        Type type = new TypeToken<User>() {}.getType();
        return new Gson().fromJson(response,type);

    }

}
