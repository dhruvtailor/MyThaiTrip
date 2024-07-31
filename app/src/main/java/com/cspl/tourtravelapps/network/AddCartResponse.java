package com.cspl.tourtravelapps.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.cspl.tourtravelapps.model.AddToCartDatum;

import java.util.List;

/**
 * Created by HP on 08/14/2018.
 */

public class AddCartResponse {

    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("AddToCartData")
    @Expose
    private List<AddToCartDatum> addToCartData = null;

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

    public List<AddToCartDatum> getAddToCartData() {
        return addToCartData;
    }

    public void setAddToCartData(List<AddToCartDatum> addToCartData) {
        this.addToCartData = addToCartData;
    }

}
