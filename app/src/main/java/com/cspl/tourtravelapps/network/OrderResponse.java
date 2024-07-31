package com.cspl.tourtravelapps.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.cspl.tourtravelapps.model.OrderDatum;

import java.util.List;

/**
 * Created by HP on 08/22/2018.
 */

public class OrderResponse {
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("OrderData")
    @Expose
    private List<OrderDatum> orderData = null;

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

    public List<OrderDatum> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<OrderDatum> orderData) {
        this.orderData = orderData;
    }
}
