package com.cspl.tourtravelapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 08/11/2018.
 */

public class PackageCategoryDatum {

    @SerializedName("SrNo")
    @Expose
    private Integer srNo;
    @SerializedName("Category_SubPackage_ID")
    @Expose
    private Integer categorySubPackageID;
    @SerializedName("Category_Package_ID")
    @Expose
    private Integer categoryPackageID;
    @SerializedName("Category_Name")
    @Expose
    private String categoryName;
    @SerializedName("Category_description")
    @Expose
    private String categoryDescription;
    @SerializedName("Category_rate")
    @Expose
    private Integer categoryRate;
    @SerializedName("Package_Type_Name")
    @Expose
    private String packageTypeName;
    @SerializedName("Agent_Category_Child_Rate")
    @Expose
    private Integer agentCategoryChildRate;
    @SerializedName("Agent_Category_Adult_Rate")
    @Expose
    private Integer agentCategoryAdultRate;
    @SerializedName("Agent_Category_Infant_Rate")
    @Expose
    private Integer agentCategoryInfantRate;
    @SerializedName("Cust_Category_Child_Rate")
    @Expose
    private Integer custCategoryChildRate;
    @SerializedName("Cust_Category_Adult_Rate")
    @Expose
    private Integer custCategoryAdultRate;
    @SerializedName("Cust_Category_Infant_Rate")
    @Expose
    private Integer custCategoryInfantRate;
    @SerializedName("Car_Rate")
    @Expose
    private Integer carRate;
    @SerializedName("Innova_Rate")
    @Expose
    private Integer innovaRate;
    @SerializedName("Van_Rate")
    @Expose
    private Integer vanRate;
    @SerializedName("Package_Img")
    @Expose
    private String packageImg;


    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public Integer getCategorySubPackageID() {
        return categorySubPackageID;
    }

    public void setCategorySubPackageID(Integer categorySubPackageID) {
        this.categorySubPackageID = categorySubPackageID;
    }

    public Integer getCategoryPackageID() {
        return categoryPackageID;
    }

    public void setCategoryPackageID(Integer categoryPackageID) {
        this.categoryPackageID = categoryPackageID;
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

    public String getPackageTypeName() {
        return packageTypeName;
    }

    public void setPackageTypeName(String packageTypeName) {
        this.packageTypeName = packageTypeName;
    }

    public Integer getAgentCategoryChildRate() {
        return agentCategoryChildRate;
    }

    public void setAgentCategoryChildRate(Integer agentCategoryChildRate) {
        this.agentCategoryChildRate = agentCategoryChildRate;
    }

    public Integer getAgentCategoryAdultRate() {
        return agentCategoryAdultRate;
    }

    public void setAgentCategoryAdultRate(Integer agentCategoryAdultRate) {
        this.agentCategoryAdultRate = agentCategoryAdultRate;
    }

    public Integer getAgentCategoryInfantRate() {
        return agentCategoryInfantRate;
    }

    public void setAgentCategoryInfantRate(Integer agentCategoryInfantRate) {
        this.agentCategoryInfantRate = agentCategoryInfantRate;
    }

    public Integer getCustCategoryChildRate() {
        return custCategoryChildRate;
    }

    public void setCustCategoryChildRate(Integer custCategoryChildRate) {
        this.custCategoryChildRate = custCategoryChildRate;
    }

    public Integer getCustCategoryAdultRate() {
        return custCategoryAdultRate;
    }

    public void setCustCategoryAdultRate(Integer custCategoryAdultRate) {
        this.custCategoryAdultRate = custCategoryAdultRate;
    }

    public Integer getCustCategoryInfantRate() {
        return custCategoryInfantRate;
    }

    public void setCustCategoryInfantRate(Integer custCategoryInfantRate) {
        this.custCategoryInfantRate = custCategoryInfantRate;
    }

    public Integer getCarRate() {
        return carRate;
    }

    public void setCarRate(Integer carRate) {
        this.carRate = carRate;
    }

    public Integer getInnovaRate() {
        return innovaRate;
    }

    public void setInnovaRate(Integer innovaRate) {
        this.innovaRate = innovaRate;
    }

    public Integer getVanRate() {
        return vanRate;
    }

    public void setVanRate(Integer vanRate) {
        this.vanRate = vanRate;
    }


    public String getPackageImg() {
        return packageImg;
    }

    public void setPackageImg(String packageImg) {
        this.packageImg = packageImg;
    }


}
