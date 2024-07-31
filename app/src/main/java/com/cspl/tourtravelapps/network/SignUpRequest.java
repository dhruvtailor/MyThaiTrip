package com.cspl.tourtravelapps.network;

/**
 * Created by HP on 08/01/2018.
 */

public class SignUpRequest {

    private int User_Type;
    private String User_Email;
    private String User_Name;
    private String User_Password;
    private String User_Mobile_No;
    private String Company_Name;
    private String TAT_No;

    public SignUpRequest(int userType,String userEmail, String userName,String userPassword, String userMobileNo) {
        User_Type = userType;
        User_Email = userEmail;
        User_Name = userName;
        User_Password = userPassword;
        User_Mobile_No = userMobileNo;
    }

    public SignUpRequest(int userType,String userEmail, String userPassword, String userMobileNo,String companyName, String tatNo) {
        User_Type = userType;
        User_Email = userEmail;
        User_Password = userPassword;
        User_Mobile_No = userMobileNo;
        Company_Name = companyName;
        TAT_No = tatNo;
    }
}
