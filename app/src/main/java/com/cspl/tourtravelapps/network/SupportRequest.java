package com.cspl.tourtravelapps.network;

/**
 * Created by HP on 08/04/2018.
 */

public class SupportRequest {

    private int User_ID;
    private String Name;
    private String Email;
    private String Message;

    public SupportRequest(int user_ID, String name, String email, String message) {
        User_ID = user_ID;
        Name = name;
        Email = email;
        Message = message;
    }
}
