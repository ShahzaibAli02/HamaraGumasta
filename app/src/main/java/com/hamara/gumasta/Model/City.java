
package com.hamara.gumasta.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("city")
    @Expose
    private String city;

    public  City(){}

    public  City(String city)
    {
        this.city=city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Override
    public String toString() {

        return getCity();
    }
}
