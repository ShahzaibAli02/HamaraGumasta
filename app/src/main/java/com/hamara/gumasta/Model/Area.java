
package com.hamara.gumasta.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Area {

    @SerializedName("area")
    @Expose
    private String area;

    public  Area(){}

    public  Area(String area){
        this.area=area;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return area;
    }
}
