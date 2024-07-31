package com.cspl.tourtravelapps.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.cspl.tourtravelapps.model.LoginDatum;

import java.util.List;

/**
 * Created by HP on 07/31/2018.
 */

public class LoginResponse {

    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("LoginData")
    @Expose
    private List<LoginDatum> loginData = null;

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

    public List<LoginDatum> getLoginData() {
        return loginData;
    }

    public void setLoginData(List<LoginDatum> loginData) {
        this.loginData = loginData;
    }

}
