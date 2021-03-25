package com.hamara.gumasta.Interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GumastaService {
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);


    @FormUrlEncoded
    @POST("cities")
    Call<ResponseBody> cities(@Field("state") String state);

    @FormUrlEncoded
    @POST("area")
    Call<ResponseBody> area(@Field("state") String state,@Field("city") String city);


    @POST("states")
    Call<ResponseBody> states();


    @FormUrlEncoded
    @POST("signup")
    Call<ResponseBody> signup(@Field("name") String name, @Field("email") String email,@Field("phone") String phone, @Field("password") String password);
}