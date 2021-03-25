package com.hamara.gumasta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamara.gumasta.Activities.Authentications.Login_SignUp;
import com.hamara.gumasta.Model.ApiResponse;
import com.hamara.gumasta.Model.Area;
import com.hamara.gumasta.Model.Areas;
import com.hamara.gumasta.Model.Cities;
import com.hamara.gumasta.Model.City;
import com.hamara.gumasta.Model.State;
import com.hamara.gumasta.Model.States;
import com.hamara.gumasta.ProgressDialogManager;
import com.hamara.gumasta.R;
import com.hamara.gumasta.RetrofitClient;
import com.hamara.gumasta.Util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetLocation extends AppCompatActivity {


    Spinner spinnerStates;
    Spinner spinnerCity;
    Spinner spinnerArea;


    List<State> listStates=new ArrayList<>();
    List<City> listCities=new ArrayList<>();
    List<Area> listAreas=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        listStates.add(new State("Select State"));
        listCities.add(new City("Select City"));
        listAreas.add(new Area("Select Area"));


        initViews();
        loadStates();
    }

    private void initViews()
    {

        spinnerStates=findViewById(R.id.edittextStates);
        spinnerCity=findViewById(R.id.spinnerCity);
        spinnerArea=findViewById(R.id.spinnerArea);



        spinnerCity.setEnabled(false);
        spinnerArea.setEnabled(false);

        spinnerStates.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.lyt_spinner,R.id.txtSpinner,listStates));
        spinnerCity.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.lyt_spinner,R.id.txtSpinner,listCities));
        spinnerArea.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.lyt_spinner,R.id.txtSpinner,listAreas));





        spinnerStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0)
                    return;

                if(!spinnerCity.isEnabled())
                {
                    spinnerCity.setEnabled(true);
                    spinnerCity.setBackground(getDrawable(R.drawable.back_white_stroke));

                }
                loadCities();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                    return;

                if(!spinnerArea.isEnabled())
                {
                    spinnerArea.setEnabled(true);
                    spinnerArea.setBackground(getDrawable(R.drawable.back_white_stroke));

                }

                loadAreas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadAreas()
    {

        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(this);
        progressDialog.show();
        RetrofitClient.createClient().area(spinnerStates.getSelectedItem().toString(),spinnerCity.getSelectedItem().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                progressDialog.dismiss();
                try
                {
                    String json=response.body().string();

                    Type type=new TypeToken<Areas>(){}.getType();

                    Areas areas=new Gson().fromJson(json,type);

                    if(areas.getStatus()==200)
                    {
                        listAreas.clear();
                        listAreas.add(new Area("Select Area"));
                        listAreas.addAll(areas.getAreas());

                        spinnerArea.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.lyt_spinner,R.id.txtSpinner,listAreas));
                    }
                    else
                    {
                        Util.showSnackBar(getActivity(),areas.getMessage());
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

    private void loadStates()
    {

        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(this);
        progressDialog.show();
        RetrofitClient.createClient().states().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                progressDialog.dismiss();
                try
                {
                    String json=response.body().string();

                    Type type=new TypeToken<States>(){}.getType();

                    States states=new Gson().fromJson(json,type);

                    if(states.getStatus()==200)
                    {
                        listStates.clear();
                        listStates.add(new State("Select State"));
                        listStates.addAll(states.getStates());


                        listAreas.clear();
                        listAreas.add(new Area("Select Area"));


                        spinnerArea.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.lyt_spinner,R.id.txtSpinner,listAreas));
                        spinnerStates.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.lyt_spinner,R.id.txtSpinner,listStates));
                    }
                    else
                    {
                        Util.showSnackBar(getActivity(),states.getMessage());
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

    private void loadCities()
    {

        AlertDialog progressDialog = ProgressDialogManager.getProgressDialog(this);
        progressDialog.show();
        RetrofitClient.createClient().cities(spinnerStates.getSelectedItem().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                progressDialog.dismiss();
                try
                {
                    String json=response.body().string();

                    Type type=new TypeToken<Cities>(){}.getType();

                    Cities cities=new Gson().fromJson(json,type);
                    listCities.clear();
                    listCities.add(new City("Select City"));
                    listCities.addAll(cities.getCities());

                    if(cities.getStatus()==200)
                    {
                        spinnerCity.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.lyt_spinner,R.id.txtSpinner,listCities));

                    }
                    else
                    {
                        Util.showSnackBar(getActivity(),cities.getMessage());
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

    private Activity getActivity() {
        return this;
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickSubmit(View view) {
        startActivity(new Intent(this,LicenseType.class));
    }
}