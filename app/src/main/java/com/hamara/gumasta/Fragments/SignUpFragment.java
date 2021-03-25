package com.hamara.gumasta.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamara.gumasta.Activities.Authentications.Login_SignUp;
import com.hamara.gumasta.Model.ApiResponse;
import com.hamara.gumasta.ProgressDialogManager;
import com.hamara.gumasta.R;
import com.hamara.gumasta.RetrofitClient;
import com.hamara.gumasta.Util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment implements View.OnClickListener {



    TextView txtLogin;
    Button btnSignUp;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPhone;
    EditText editTextPass;
    EditText editTextConfPass;
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


        editTextName=view.findViewById(R.id.editTextName);
        editTextEmail=view.findViewById(R.id.editTextEmail);
        editTextPhone=view.findViewById(R.id.editTextPhone);
        editTextPass=view.findViewById(R.id.editTextPass);
        editTextConfPass=view.findViewById(R.id.editTextConfPass);


        txtLogin=view.findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(this);


        btnSignUp=view.findViewById(R.id.btnSignUp);


        btnSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v==txtLogin)
        {
            ((Login_SignUp) Objects.requireNonNull(getActivity())).txtLogIn.performClick();
            return;
        }


        if(v==btnSignUp)
        {
            if(!Util.checkForEmpty(new EditText[]{editTextName,editTextEmail,editTextPhone,editTextPass,editTextConfPass}))
            {

                if(editTextPass.getText().toString().equals(editTextConfPass.getText().toString()))
                {
                    signUp();
                }
                else
                {
                    editTextPass.setError("Password Miss Match");
                    editTextPass.requestFocus();

                }

            }

        }

    }

    private void signUp()
    {

        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(getActivity());
        progressDialog.show();

        RetrofitClient.createClient().signup(editTextName.getText().toString(),editTextEmail.getText().toString(),editTextPhone.getText().toString(),editTextPass.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                progressDialog.dismiss();
                try
                {
                    String json=response.body().string();

                    Type type=new TypeToken<ApiResponse>(){}.getType();

                    ApiResponse apiResponse=new Gson().fromJson(json,type);

                    if(apiResponse.getMessage().equals("member signup Successfully"))
                    {
                        Util.showSnackBar(getActivity(),"Account Created Successfully");
                        ((Login_SignUp)getActivity()).txtLogIn.performClick();
                    }
                    else
                    {
                        Util.showSnackBar(getActivity(),apiResponse.getMessage());
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