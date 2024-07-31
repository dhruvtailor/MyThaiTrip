package com.cspl.tourtravelapps;

import android.app.Application;

import com.cspl.tourtravelapps.helper.GlobalConstants;
import com.cspl.tourtravelapps.helper.PrefUtils;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;

/**
 * Created by HP on 07/31/2018.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        PrefUtils.setBoolean(getApplicationContext(), GlobalConstants.CONNECTSTATE, false);
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
