package com.cspl.tourtravelapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 08/02/2018.
 */

public class CountryDatum {
    @SerializedName("Country_ID")
    @Expose
    private Integer countryID;
    @SerializedName("Country_Name")
    @Expose
    private String countryName;
    @SerializedName("Country_Rate")
    @Expose
    private Integer countryRate;

    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getCountryRate() {
        return countryRate;
    }

    public void setCountryRate(Integer countryRate) {
        this.countryRate = countryRate;
    }
}
