package com.cspl.tourtravelapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 07/31/2018.
 */

public class LoginDatum {

    @SerializedName("User_ID")
    @Expose
    private Integer userID;
    @SerializedName("User_Type")
    @Expose
    private Integer userType;
    @SerializedName("User_Email")
    @Expose
    private String userEmail;
    @SerializedName("User_Mobile_No")
    @Expose
    private String userMobileNo;
    @SerializedName("User_Password")
    @Expose
    private String userPassword;
    @SerializedName("Credits")
    @Expose
    private Double credits;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }
}
