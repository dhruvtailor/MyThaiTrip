package com.cspl.tourtravelapps.network;

import com.cspl.tourtravelapps.model.ExchangeRates;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ANDROID on 11-08-2021.
 */

public class THBToUSDRateResponse {
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("last_updated")
    @Expose
    private Integer lastUpdated;
    @SerializedName("exchange_rates")
    @Expose
    private ExchangeRates exchangeRates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Integer getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Integer lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ExchangeRates getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(ExchangeRates exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

}
