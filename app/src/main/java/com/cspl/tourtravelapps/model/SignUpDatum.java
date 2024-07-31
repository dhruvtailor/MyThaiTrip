package com.cspl.tourtravelapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 08/01/2018.
 */

public class SignUpDatum {
    @SerializedName("User_Email")
    @Expose
    private String userEmail;
    @SerializedName("User_Mobile_No")
    @Expose
    private String userMobileNo;
    @SerializedName("User_Password")
    @Expose
    private String userPassword;
    @SerializedName("User_ID")
    @Expose
    private Integer userID;
    @SerializedName("Credits")
    @Expose
    private Double credits;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }


    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }
}
