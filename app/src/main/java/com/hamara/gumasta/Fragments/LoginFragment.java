package com.hamara.gumasta.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hamara.gumasta.Activities.ForgotPassword;
import com.hamara.gumasta.Activities.Login_SignUp;
import com.hamara.gumasta.Activities.SetLocation;
import com.hamara.gumasta.R;

public class LoginFragment extends Fragment  implements View.OnClickListener {




    TextView txtforgotPass;
    TextView txtSignUp;
    Button btnLogin;
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


        btnLogin=view.findViewById(R.id.btnLogin);
        txtforgotPass=view.findViewById(R.id.txtforgotPass);
        txtSignUp=view.findViewById(R.id.txtSignUp);



        btnLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        txtforgotPass.setOnClickListener(this);

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
            startActivity(new Intent(getActivity(), SetLocation.class));
        }


    }
}