
package com.hamara.gumasta.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {

    @SerializedName("state")
    @Expose
    private String state;
    public  State(){}
    public  State(String state)
    {
        this.state=state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return getState();
    }
}
