package com.cspl.tourtravelapps.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.cspl.tourtravelapps.model.PackageReview;

import java.util.List;

/**
 * Created by ANDROID-PC on 14/09/2018.
 */

public class ReviewResponse {
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("PackageReview")
    @Expose
    private List<PackageReview> packageReview = null;

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

    public List<PackageReview> getPackageReview() {
        return packageReview;
    }

    public void setPackageReview(List<PackageReview> packageReview) {
        this.packageReview = packageReview;
    }
}
