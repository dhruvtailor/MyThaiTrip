package com.cspl.tourtravelapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 08/02/2018.
 */

public class PackageDatum {
    @SerializedName("Package_ID")
    @Expose
    private Integer packageID;
    @SerializedName("Package_Name")
    @Expose
    private String packageName;
    @SerializedName("Package_Type_Name")
    @Expose
    private String packageTypeName;
    @SerializedName("Package_Img")
    @Expose
    private String packageImg;
    @SerializedName("Package_Info")
    @Expose
    private String packageInfo;
    @SerializedName("Package_City_ID")
    @Expose
    private Integer packageCityID;
    @SerializedName("Average_Time")
    @Expose
    private String averageTime;
    @SerializedName("Package_Rate")
    @Expose
    private Integer packageRate;
    @SerializedName("Customer_Child_Rate")
    @Expose
    private Integer customerChildRate;
    @SerializedName("Customer_Adult_Rate")
    @Expose
    private Integer customerAdultRate;
    @SerializedName("Customer_InFant_Rate")
    @Expose
    private Integer customerInFantRate;
    @SerializedName("Agent_Child_Rate")
    @Expose
    private Integer agentChildRate;
    @SerializedName("Agent_Adult_Rate")
    @Expose
    private Integer agentAdultRate;
    @SerializedName("Agent_InFant_Rate")
    @Expose
    private Integer agentInFantRate;
    @SerializedName("PreOrderHours")
    @Expose
    private Integer preOrderHours;
    @SerializedName("PickupTime")
    @Expose
    private String pickupTime;
    @SerializedName("Payment_Mode")
    @Expose
    private String paymentMode;
    @SerializedName("Category_Name")
    @Expose
    private String categoryName;
    @SerializedName("Category_description")
    @Expose
    private String categoryDescription;
    @SerializedName("Category_rate")
    @Expose
    private Integer categoryRate;
    @SerializedName("IsAddedSubCategory")
    @Expose
    private Boolean isAddedSubCategory;


    public Integer getPackageID() {
        return packageID;
    }

    public void setPackageID(Integer packageID) {
        this.packageID = packageID;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageTypeName() {
        return packageTypeName;
    }

    public void setPackageTypeName(String packageTypeName) {
        this.packageTypeName = packageTypeName;
    }

    public String getPackageImg() {
        return packageImg;
    }

    public void setPackageImg(String packageImg) {
        this.packageImg = packageImg;
    }

    public String getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(String packageInfo) {
        this.packageInfo = packageInfo;
    }

    public Integer getPackageCityID() {
        return packageCityID;
    }

    public void setPackageCityID(Integer packageCityID) {
        this.packageCityID = packageCityID;
    }

    public String getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(String averageTime) {
        this.averageTime = averageTime;
    }

    public Integer getPackageRate() {
        return packageRate;
    }

    public void setPackageRate(Integer packageRate) {
        this.packageRate = packageRate;
    }

    public Integer getCustomerChildRate() {
        return customerChildRate;
    }

    public void setCustomerChildRate(Integer customerChildRate) {
        this.customerChildRate = customerChildRate;
    }

    public Integer getCustomerAdultRate() {
        return customerAdultRate;
    }

    public void setCustomerAdultRate(Integer customerAdultRate) {
        this.customerAdultRate = customerAdultRate;
    }

    public Integer getCustomerInFrontRate() {
        return customerInFantRate;
    }

    public void setCustomerInFrontRate(Integer customerInFantRate) {
        this.customerInFantRate = customerInFantRate;
    }

    public Integer getPreOrderHours() {
        return preOrderHours;
    }

    public void setPreOrderHours(Integer preOrderHours) {
        this.preOrderHours = preOrderHours;
    }

    public Integer getAgentChildRate() {
        return agentChildRate;
    }

    public void setAgentChildRate(Integer agentChildRate) {
        this.agentChildRate = agentChildRate;
    }

    public Integer getAgentAdultRate() {
        return agentAdultRate;
    }

    public void setAgentAdultRate(Integer agentAdultRate) {
        this.agentAdultRate = agentAdultRate;
    }

    public Integer getAgentInFrontRate() {
        return agentInFantRate;
    }

    public void setAgentInFrontRate(Integer agentInFantRate) {
        this.agentInFantRate = agentInFantRate;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Integer getCategoryRate() {
        return categoryRate;
    }

    public void setCategoryRate(Integer categoryRate) {
        this.categoryRate = categoryRate;
    }

    public Boolean getIsAddedSubCategory() {
        return isAddedSubCategory;
    }

    public void setIsAddedSubCategory(Boolean isAddedSubCategory) {
        this.isAddedSubCategory = isAddedSubCategory;
    }
}
