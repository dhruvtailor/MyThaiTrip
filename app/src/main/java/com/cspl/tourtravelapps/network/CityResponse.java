package com.cspl.tourtravelapps.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.cspl.tourtravelapps.model.CityDatum;

import java.util.List;

/**
 * Created by HP on 08/01/2018.
 */

public class CityResponse {
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("CityData")
    @Expose
    private List<CityDatum> cityData = null;

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

    public List<CityDatum> getCityData() {
        return cityData;
    }

    public void setCityData(List<CityDatum> cityData) {
        this.cityData = cityData;
    }
}
