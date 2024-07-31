package com.cspl.tourtravelapps.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.cspl.tourtravelapps.R;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by HP on 07/31/2018.
 */

public class Common {

    public static String IMG_PATH = "http://mytms.in/AmazingThailandAPI/PackageImg/";
    

    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context, R.style.DialogBox);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        return dialog;
    }

    // get Battery Level
    public static int getBatteryLevel(Context c) {

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = c.getApplicationContext().registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;

        return (int) (batteryPct * 100);
    }

    // get Ip Address
    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // get Mac Address
    public static String getMacAddress(){
        try {

            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    //date
    public static String getDate(int selectedYear, int selectedMonth, int selectedDay) {
        return String.format("%02d", selectedDay) + "-" + String.format("%02d", selectedMonth) + "-" + String.format("%04d", selectedYear);
    }

    public static String convertDateFormat(String date_string, String dateformat, String returnDateformat) throws ParseException {
        //SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date initDate = new SimpleDateFormat(dateformat).parse(date_string);
        SimpleDateFormat formatter = new SimpleDateFormat(returnDateformat);
        String Parsedate = formatter.format(initDate);
        return Parsedate;
    }

    public static String convertDateFormat_FrontToBack(String date_string) throws ParseException {
        return convertDateFormat(date_string, "dd-MM-yyyy", "yyyy-MM-dd");
    }

    public static String getDateMonthName(String date_string){
        int monthPos = date_string.indexOf("-");
        int lastPos = date_string.lastIndexOf("-");
        int monthNumeric = Integer.valueOf(date_string.substring(monthPos+1,lastPos));
        String monthName = null;
        switch (monthNumeric){
            case 1:
                monthName="Jan";
                break;
            case 2:
                monthName="Feb";
                break;
            case 3:
                monthName="Mar";
                break;
            case 4:
                monthName="Apr";
                break;
            case 5:
                monthName="May";
                break;
            case 6:
                monthName="Jun";
                break;
            case 7:
                monthName="Jul";
                break;
            case 8:
                monthName="Aug";
                break;
            case 9:
                monthName="Sep";
                break;
            case 10:
                monthName="Oct";
                break;
            case 11:
                monthName="Nov";
                break;
            case 12:
                monthName="Dec";
                break;
        }
        String return_date=date_string.substring(0,monthPos)+" "+ monthName +" "+date_string.substring(lastPos+1);
        return return_date;
    }

    public static void showSnack(View view, String msg)
    {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .show();
    }


}
