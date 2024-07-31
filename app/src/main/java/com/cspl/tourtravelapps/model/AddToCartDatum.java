package com.cspl.tourtravelapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 08/14/2018.
 */

public class AddToCartDatum {

    @SerializedName("SrNo")
    @Expose
    private Integer srNo;
    @SerializedName("Cart_Package_ID")
    @Expose
    private Integer cartPackageID;
    @SerializedName("Cart_User_ID")
    @Expose
    private Integer cartUserID;
    @SerializedName("NoOfChild")
    @Expose
    private Integer noOfChild;
    @SerializedName("NoOfAdults")
    @Expose
    private Integer noOfAdults;
    @SerializedName("NoOfInFant")
    @Expose
    private Integer noOfInFant;
    @SerializedName("Travel_Date")
    @Expose
    private String travelDate;
    @SerializedName("Total_Amount")
    @Expose
    private Integer totalAmount;
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
    @SerializedName("Flight_No")
    @Expose
    private String flightNo;
    @SerializedName("Flight_Arrival_Time")
    @Expose
    private String flightArrivalTime;
    @SerializedName("Approx_Departure_Time")
    @Expose
    private String approxDepartureTime;
    @SerializedName("Vehicle_Type")
    @Expose
    private String vehicleType;
    @SerializedName("No_of_Vehicle")
    @Expose
    private Integer noOfVehicle;
    @SerializedName("Cust_Pickup_Time")
    @Expose
    private String custPickupTime;
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

    public Integer getCartPackageID() {
        return cartPackageID;
    }

    public void setCartPackageID(Integer cartPackageID) {
        this.cartPackageID = cartPackageID;
    }

    public Integer getCartUserID() {
        return cartUserID;
    }

    public void setCartUserID(Integer cartUserID) {
        this.cartUserID = cartUserID;
    }

    public Integer getNoOfChild() {
        return noOfChild;
    }

    public void setNoOfChild(Integer noOfChild) {
        this.noOfChild = noOfChild;
    }

    public Integer getNoOfAdults() {
        return noOfAdults;
    }

    public void setNoOfAdults(Integer noOfAdults) {
        this.noOfAdults = noOfAdults;
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

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
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

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getFlightArrivalTime() {
        return flightArrivalTime;
    }

    public void setFlightArrivalTime(String flightArrivalTime) {
        this.flightArrivalTime = flightArrivalTime;
    }

    public String getApproxDepartureTime() {
        return approxDepartureTime;
    }

    public void setApproxDepartureTime(String approxDepartureTime) {
        this.approxDepartureTime = approxDepartureTime;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getNoOfVehicle() {
        return noOfVehicle;
    }

    public void setNoOfVehicle(Integer noOfVehicle) {
        this.noOfVehicle = noOfVehicle;
    }

    public String getCustPickupTime() {
        return custPickupTime;
    }

    public void setCustPickupTime(String custPickupTime) {
        this.custPickupTime = custPickupTime;
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
