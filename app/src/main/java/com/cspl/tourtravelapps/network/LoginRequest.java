package com.cspl.tourtravelapps.network;

/**
 * Created by HP on 07/31/2018.
 */

public class LoginRequest {
    private String User_Email;
    private String User_Password;
    private String Mac_ID;
    private String IP_Address;
    private int Battery;


    public LoginRequest(String userEmail, String userPassword, String macId, String IPAdd, int battery) {
        User_Email = userEmail;
        User_Password = userPassword;
        Mac_ID = macId;
        this.IP_Address = IPAdd;
        Battery = battery;
    }

}
