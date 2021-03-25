package com.hamara.gumasta;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class Util
{

    public static  boolean checkForEmpty(EditText[] editText)
    {
        for(EditText editText1:editText)
        {
            if(TextUtils.isEmpty(editText1.getText().toString()))
            {
                editText1.setError("Required Field");
                editText1.requestFocus();
                return true;
            }
        }
        return false;
    }

    public  static  void  showSnackBar(Activity activity, String Message)
    {
        Snackbar.make(activity.findViewById(android.R.id.content),Message, BaseTransientBottomBar.LENGTH_LONG).show();
    }

}
