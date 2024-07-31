package com.cspl.tourtravelapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ANDROID on 10-08-2021.
 */

public class Data {
    @SerializedName("tickers")
    @Expose
    private List<Ticker> tickers = null;

    public List<Ticker> getTickers() {
        return tickers;
    }

    public void setTickers(List<Ticker> tickers) {
        this.tickers = tickers;
    }
}
