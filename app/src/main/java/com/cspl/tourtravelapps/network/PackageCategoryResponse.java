package com.cspl.tourtravelapps.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.cspl.tourtravelapps.model.PackageCategoryDatum;

import java.util.List;

/**
 * Created by HP on 08/11/2018.
 */

public class PackageCategoryResponse {
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("PackageCategoryData")
    @Expose
    private List<PackageCategoryDatum> packageCategoryData = null;

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

    public List<PackageCategoryDatum> getPackageCategoryData() {
        return packageCategoryData;
    }

    public void setPackageCategoryData(List<PackageCategoryDatum> packageCategoryData) {
        this.packageCategoryData = packageCategoryData;
    }
}
