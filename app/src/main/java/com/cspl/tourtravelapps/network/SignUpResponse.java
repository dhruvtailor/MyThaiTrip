package com.cspl.tourtravelapps.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.cspl.tourtravelapps.model.SignUpDatum;

import java.util.List;

/**
 * Created by HP on 08/01/2018.
 */

public class SignUpResponse {
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("RegisterData")
    @Expose
    private List<SignUpDatum> registerData = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SignUpDatum> getRegisterData() {
        return registerData;
    }

    public void setRegisterData(List<SignUpDatum> registerData) {
        this.registerData = registerData;
    }
}
