package com.cspl.tourtravelapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ANDROID on 11-08-2021.
 */

public class AMZE {
    @SerializedName("thb")
    @Expose
    private Double thb;

    public Double getThb() {
        return thb;
    }

    public void setThb(Double thb) {
        this.thb = thb;
    }
}
