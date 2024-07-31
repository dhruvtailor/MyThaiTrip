package com.cspl.tourtravelapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ANDROID on 10-08-2021.
 */

public class Ticker {
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("last_price")
    @Expose
    private String lastPrice;
    @SerializedName("quote_volume_24h")
    @Expose
    private String quoteVolume24h;
    @SerializedName("base_volume_24h")
    @Expose
    private String baseVolume24h;
    @SerializedName("high_24h")
    @Expose
    private String high24h;
    @SerializedName("low_24h")
    @Expose
    private String low24h;
    @SerializedName("open_24h")
    @Expose
    private String open24h;
    @SerializedName("close_24h")
    @Expose
    private String close24h;
    @SerializedName("best_ask")
    @Expose
    private String bestAsk;
    @SerializedName("best_ask_size")
    @Expose
    private String bestAskSize;
    @SerializedName("best_bid")
    @Expose
    private String bestBid;
    @SerializedName("best_bid_size")
    @Expose
    private String bestBidSize;
    @SerializedName("fluctuation")
    @Expose
    private String fluctuation;
    @SerializedName("url")
    @Expose
    private String url;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getQuoteVolume24h() {
        return quoteVolume24h;
    }

    public void setQuoteVolume24h(String quoteVolume24h) {
        this.quoteVolume24h = quoteVolume24h;
    }

    public String getBaseVolume24h() {
        return baseVolume24h;
    }

    public void setBaseVolume24h(String baseVolume24h) {
        this.baseVolume24h = baseVolume24h;
    }

    public String getHigh24h() {
        return high24h;
    }

    public void setHigh24h(String high24h) {
        this.high24h = high24h;
    }

    public String getLow24h() {
        return low24h;
    }

    public void setLow24h(String low24h) {
        this.low24h = low24h;
    }

    public String getOpen24h() {
        return open24h;
    }

    public void setOpen24h(String open24h) {
        this.open24h = open24h;
    }

    public String getClose24h() {
        return close24h;
    }

    public void setClose24h(String close24h) {
        this.close24h = close24h;
    }

    public String getBestAsk() {
        return bestAsk;
    }

    public void setBestAsk(String bestAsk) {
        this.bestAsk = bestAsk;
    }

    public String getBestAskSize() {
        return bestAskSize;
    }

    public void setBestAskSize(String bestAskSize) {
        this.bestAskSize = bestAskSize;
    }

    public String getBestBid() {
        return bestBid;
    }

    public void setBestBid(String bestBid) {
        this.bestBid = bestBid;
    }

    public String getBestBidSize() {
        return bestBidSize;
    }

    public void setBestBidSize(String bestBidSize) {
        this.bestBidSize = bestBidSize;
    }

    public String getFluctuation() {
        return fluctuation;
    }

    public void setFluctuation(String fluctuation) {
        this.fluctuation = fluctuation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
