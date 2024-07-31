package com.cspl.tourtravelapps.network;

import com.cspl.tourtravelapps.model.AMZE;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ANDROID on 11-08-2021.
 */

public class AMZERateResponse {
    @SerializedName("the-amaze-world")
    @Expose
    private AMZE aMZE;

    public AMZE getAMZE() {
        return aMZE;
    }

    public void setAMZE(AMZE aMZE) {
        this.aMZE = aMZE;
    }
}
