package com.cspl.tourtravelapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 08/22/2018.
 */

public class OrderDatum {
    @SerializedName("SrNo")
    @Expose
    private Integer srNo;
    @SerializedName("Order_ID")
    @Expose
    private Integer orderID;
    @SerializedName("Order_Package_ID")
    @Expose
    private Integer orderPackageID;
    @SerializedName("Order_User_ID")
    @Expose
    private Integer orderUserID;
    @SerializedName("NoOfChild")
    @Expose
    private Integer noOfChild;
    @SerializedName("NoOfAdult")
    @Expose
    private Integer noOfAdult;
    @SerializedName("NoOfInFant")
    @Expose
    private Integer noOfInFant;
    @SerializedName("Travel_Date")
    @Expose
    private String travelDate;
    @SerializedName("Total_Amount")
    @Expose
    private double totalAmount;
    @SerializedName("Cust_Name")
    @Expose
    private String custName;
    @SerializedName("Cust_Email")
    @Expose
    private String custEmail;
    @SerializedName("Cust_Phone")
    @Expose
    private String custPhone;
    @SerializedName("Hotel_Name")
    @Expose
    private String hotelName;
    @SerializedName("Hotel_Short_Address")
    @Expose
    private String hotelShortAddress;
    @SerializedName("IsConfirmed")
    @Expose
    private Boolean isConfirmed;
    @SerializedName("Category_Name")
    @Expose
    private String categoryName;
    @SerializedName("Package_Img")
    @Expose
    private String packageImg;

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getOrderPackageID() {
        return orderPackageID;
    }

    public void setOrderPackageID(Integer orderPackageID) {
        this.orderPackageID = orderPackageID;
    }

    public Integer getOrderUserID() {
        return orderUserID;
    }

    public void setOrderUserID(Integer orderUserID) {
        this.orderUserID = orderUserID;
    }

    public Integer getNoOfChild() {
        return noOfChild;
    }

    public void setNoOfChild(Integer noOfChild) {
        this.noOfChild = noOfChild;
    }

    public Integer getNoOfAdult() {
        return noOfAdult;
    }

    public void setNoOfAdult(Integer noOfAdult) {
        this.noOfAdult = noOfAdult;
    }

    public Integer getNoOfInFant() {
        return noOfInFant;
    }

    public void setNoOfInFant(Integer noOfInFant) {
        this.noOfInFant = noOfInFant;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelShortAddress() {
        return hotelShortAddress;
    }

    public void setHotelShortAddress(String hotelShortAddress) {
        this.hotelShortAddress = hotelShortAddress;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPackageImg() {
        return packageImg;
    }

    public void setPackageImg(String packageImg) {
        this.packageImg = packageImg;
    }
}
