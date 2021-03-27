package com.hamara.gumasta.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Report
{

    private  static final Report model=new Report();
    public  static Report getInstance ()
    {
        return model;
    }



    String  user_id;

    public String getNature_business() {
        return nature_business;
    }

    public void setNature_business(String nature_business) {
        this.nature_business = nature_business;
    }

    String nature_business;
    String  name;
    String  email;
    String  phone;
    String  state;
    String  city;
    String  area;
    String  services;
    String  plaining_type;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getPlaining_type() {
        return plaining_type;
    }

    public void setPlaining_type(String plaining_type) {
        this.plaining_type = plaining_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void updateCreated_at() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
        this.created_at = sdf.format(new Date());
    }

    String  created_at;



}
