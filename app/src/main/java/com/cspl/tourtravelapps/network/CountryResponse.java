package com.cspl.tourtravelapps.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.cspl.tourtravelapps.model.CountryDatum;

import java.util.List;

/**
 * Created by HP on 08/02/2018.
 */

public class CountryResponse {
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("CountryData")
    @Expose
    private List<CountryDatum> countryData = null;

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

    public List<CountryDatum> getCountryData() {
        return countryData;
    }

    public void setCountryData(List<CountryDatum> countryData) {
        this.countryData = countryData;
    }
}
