package com.cspl.tourtravelapps.network;

/**
 * Created by HP on 08/02/2018.
 */

public class PackageRequest {

    private int Package_City_ID;
    private int User_Type;

    public PackageRequest(int package_City_ID, int user_Type) {
        Package_City_ID = package_City_ID;
        User_Type = user_Type;
    }
}
