package com.hamara.gumasta.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamara.gumasta.Activities.Login_SignUp;
import com.hamara.gumasta.Activities.MainActivity;
import com.hamara.gumasta.R;

public class SignUpFragment extends Fragment implements View.OnClickListener {



    TextView txtLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view)
    {

        txtLogin=view.findViewById(R.id.txtLogin);


        txtLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v==txtLogin)
        {
            ((Login_SignUp)getActivity()).txtLogIn.performClick();
        }

    }
}