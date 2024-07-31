package com.cspl.tourtravelapps.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HP on 07/31/2018.
 */

public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();

    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Amz_Thi_Pref";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_USER_TYPE = "User_Type";
    private static final String KEY_USER_ID = "User_ID";
    private static final String KEY_USER_EMAIL = "UserEmail";
    private static final String KEY_USER_PHONE = "UserPhone";
    private static final String KEY_USER_CREDITS = "UserCredits";
    private static final String KEY_CITY_ID= "CityID";
    private static final String KEY_CITY_NAME = "CityName";
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        editor.commit();

//        Log.d(TAG, "session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getKeyUserType() {
        return pref.getString(KEY_USER_TYPE,null);
    }

    public String getKeyUserEmail() {
        return pref.getString(KEY_USER_EMAIL,null);
    }

    public void setKeyUserType(String User_Type) {
        editor.putString(KEY_USER_TYPE,User_Type);
        editor.commit();
    }

    public void setKeyUserEmail(String userEmail){
        editor.putString(KEY_USER_EMAIL,userEmail);
    }

    public void setKeyUserCredits(String userCredits){
        editor.putString(KEY_USER_CREDITS,userCredits);
        editor.commit();
    }

    public String getKeyUserCredits() {
        return pref.getString(KEY_USER_CREDITS,null);
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }

    public void setKeyCityId(int cityID){
        editor.putInt(KEY_CITY_ID,cityID);
        editor.commit();
    }

    public int getKeyCityId(){
        return pref.getInt(KEY_CITY_ID,0);
    }

    public void setKeyCityName(String cityName){
        editor.putString(KEY_CITY_NAME,cityName);
        editor.commit();
    }

    public String getKeyCityName(){
        return pref.getString(KEY_CITY_NAME,null);
    }

    public void setKeyUserId(int userId){
        editor.putInt(KEY_USER_ID,userId);
        editor.commit();
    }

    public void setKeyUserPhone(String userPhone) {
        editor.putString(KEY_USER_PHONE,userPhone);
        editor.commit();
    }

    public String getKeyUserPhone() {
        return pref.getString(KEY_USER_PHONE,null);
    }

    public int getKeyUserId(){
        return pref.getInt(KEY_USER_ID,0);
    }

    public boolean isCityPresent(){
        return pref.contains(KEY_CITY_NAME);
    }

    public void removeCity(){
        editor.remove(KEY_CITY_ID);
        editor.remove(KEY_CITY_NAME);
        editor.commit();
    }
}
