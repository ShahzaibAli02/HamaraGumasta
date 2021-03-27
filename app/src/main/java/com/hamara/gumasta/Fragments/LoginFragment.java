package com.hamara.gumasta.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamara.gumasta.Activities.Authentications.ForgotPassword;
import com.hamara.gumasta.Activities.Authentications.Login_SignUp;
import com.hamara.gumasta.Activities.SetLocation;
import com.hamara.gumasta.Model.ApiResponse;
import com.hamara.gumasta.Model.User;
import com.hamara.gumasta.ProgressDialogManager;
import com.hamara.gumasta.R;
import com.hamara.gumasta.RetrofitClient;
import com.hamara.gumasta.SharedPreference.SharedPref;
import com.hamara.gumasta.Util;
import com.hbb20.CountryCodePicker;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment  implements View.OnClickListener {




    TextView txtforgotPass;
    TextView txtSignUp;
    Button btnLogin;
    CheckBox checkboxRemMe;
    CountryCodePicker ccp;
    com.google.android.material.textfield.TextInputEditText editTextPhone;
    com.google.android.material.textfield.TextInputEditText editTextPass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    private void initViews(View view)
    {




        editTextPhone=view.findViewById(R.id.editTextPhone);


        editTextPass=view.findViewById(R.id.editTextPass);
        checkboxRemMe=view.findViewById(R.id.checkboxRemMe);

        btnLogin=view.findViewById(R.id.btnLogin);
        txtforgotPass=view.findViewById(R.id.txtforgotPass);
        txtSignUp=view.findViewById(R.id.txtSignUp);



        btnLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        txtforgotPass.setOnClickListener(this);


        ccp=view.findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(editTextPhone);

    }


    @Override
    public void onClick(View v)
    {

        if(v==txtforgotPass)
        {

            startActivity(new Intent(getActivity(), ForgotPassword.class));
        }

        if(v==txtSignUp)
        {

            ((Login_SignUp)getActivity()).txtSignUp.performClick();
        }

        if(v==btnLogin)
        {

            if(!Util.checkForEmpty(new EditText[]{editTextPhone,editTextPass}))
            {
                if(!ccp.isValidFullNumber())
                {
                    editTextPhone.setError("Enter Valid Number");
                    editTextPhone.requestFocus();
                }
                else
                authenticate();
            }

        }


    }

    private void authenticate()
    {

        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(getActivity());
        progressDialog.show();
        RetrofitClient.createClient().login(ccp.getFullNumberWithPlus(),editTextPass.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {

                progressDialog.dismiss();
                try
                {
                    String json=response.body().string();

                    Type type=new TypeToken<User>(){}.getType();

                    User User=new Gson().fromJson(json,type);

                    if(User.getMessage().equalsIgnoreCase("User Valid"))
                    {


                        SharedPref.setRemMe(getActivity(),checkboxRemMe.isChecked());
                        SharedPref.SaveUser(getActivity(),json);
                        startActivity(new Intent(getActivity(), SetLocation.class));
                    }
                    else
                    {
                      //  Util.showSnackBar(getActivity(),"Phone Or Password Is Invalid");
                        Util.showSnackBar(getActivity(),User.getMessage());
                    }


                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Util.showSnackBar(getActivity(),"ERROR");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                progressDialog.dismiss();
                Util.showSnackBar(getActivity(),t.getMessage());
            }
        });


    }
}