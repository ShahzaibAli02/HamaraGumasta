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
    @POST("report")
    Call<ResponseBody> report
            (
            @Field("user_id") String user_id,@Field("name") String name
            ,@Field("email") String email,@Field("phone") String phone
            ,@Field("state") String state,@Field("city") String city
            ,@Field("area") String area,@Field("services") String services
            ,@Field("plaining_type") String plaining_type,@Field("created_at") String created_at
            ,@Field("nature_business") String nature_business

          );



    @FormUrlEncoded
    @POST("area")
    Call<ResponseBody> area(@Field("state") String state,@Field("city") String city);


    @POST("states")
    Call<ResponseBody> states();

    @POST("services")
    Call<ResponseBody> services();



    @FormUrlEncoded
    @POST("signup")
    Call<ResponseBody> signup(@Field("name") String name, @Field("email") String email,@Field("phone") String phone, @Field("password") String password);
}