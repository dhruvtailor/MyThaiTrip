package com.cspl.tourtravelapps.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.cspl.tourtravelapps.model.PackageDatum;

import java.util.List;

/**
 * Created by HP on 08/02/2018.
 */

public class PackageResponse {
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("PackageData")
    @Expose
    private List<PackageDatum> packageData = null;

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

    public List<PackageDatum> getPackageData() {
        return packageData;
    }

    public void setPackageData(List<PackageDatum> packageData) {
        this.packageData = packageData;
    }
}
