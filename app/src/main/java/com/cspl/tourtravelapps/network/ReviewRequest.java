package com.cspl.tourtravelapps.network;

/**
 * Created by ANDROID-PC on 14/09/2018.
 */

public class ReviewRequest {
    private int FlagID;
    private int PackageID;

    public ReviewRequest(int flagID, int packageID) {
        FlagID = flagID;
        PackageID = packageID;
    }
}
