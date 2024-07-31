package com.cspl.tourtravelapps.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cspl.tourtravelapps.MyApplication;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.RetrofitAPI.ApiClient;
import com.cspl.tourtravelapps.RetrofitAPI.ApiInterface;
import com.cspl.tourtravelapps.helper.Common;
import com.cspl.tourtravelapps.helper.SessionManager;
import com.cspl.tourtravelapps.interactor.AuthInnerInteractor;
import com.cspl.tourtravelapps.network.LoginRequest;
import com.cspl.tourtravelapps.network.LoginResponse;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInFragment extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener {
    private AuthInnerInteractor innerInteractor;
    private EditText signInEmail;
    private EditText signInPassword;
    private ImageView signInBackImageView;
    private TextView signIn;
    private TextView forgetPassword;
    private TextView signUp;
    private ProgressDialog dialog;
    private SessionManager sessionManager;
    private Common c;
    private LinearLayout signUpLayout;


    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance(AuthInnerInteractor innerInteractor) {
        SignInFragment fragment = new SignInFragment();
        fragment.innerInteractor = innerInteractor;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        view.setBackgroundColor(Color.WHITE);

//        signInEmail =(EditText) view.findViewById(R.id.signinEmail);
//        signInPassword = (EditText) view.findViewById(R.id.signInPassword);
        initializecontrols(view);

        signInBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                innerInteractor.popForgetPassword();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                checkLogin();
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                innerInteractor.switchToForgetPassword();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                innerInteractor.switchToSignUp();

            }
        });

        return view;
    }

    // get Battery Level
    private int getBatteryLevel() {

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getContext().registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;

        return (int) (batteryPct * 100);
    }

    // get Ip Address
    private String getIpAddress() {
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
    private String getMacAddress(){
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

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 3) {
            return true;
        }
        return false;
    }

    private void initializecontrols(View v){
        sessionManager = new SessionManager(getContext());
        signInEmail =(EditText) v.findViewById(R.id.signinEmail);
        signInPassword = (EditText) v.findViewById(R.id.signInPassword);
        signInBackImageView = (ImageView) v.findViewById(R.id.sign_in_back_iv);
        signIn = (TextView) v.findViewById(R.id.signIn);
        forgetPassword = (TextView) v.findViewById(R.id.forgetPassword);
        signUp = (TextView) v.findViewById(R.id.switchSignUp);
        signUpLayout = (LinearLayout) v.findViewById(R.id.sign_up_layout);
//        if(sessionManager.getKeyUserType().equals("1")){
//
//            signUpLayout.setVisibility(View.GONE);
//        }

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void closeProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void SetUserDetails(String userType, String userEmail, String userPhone, int userId,String userCredits) {
        sessionManager.setLogin(true);
        sessionManager.setKeyUserEmail(userEmail);
        sessionManager.setKeyUserPhone(userPhone);
        sessionManager.setKeyUserType(userType);
        sessionManager.setKeyUserId(userId);
        sessionManager.setKeyUserCredits(userCredits);
    }

    private void checkLogin(){
        boolean isConnected = ConnectivityReceiver.isConnected();

        if(!isConnected){
            Snackbar.make(getActivity().findViewById(R.id.fragment_sigin),R.string.no_internet,Snackbar.LENGTH_LONG)
                    .show();
        }
        else {
            final String email = signInEmail.getText().toString();
            final String pass = signInPassword.getText().toString();

            if(isValidEmail(email) && isValidPassword(pass)){
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                dialog = Common.showProgressDialog(getContext());
                dialog.show();

//                String DeviceSN = Build.SERIAL;
                String IP = getIpAddress();
                String MACID = getMacAddress();
                int Battery = getBatteryLevel();
//                int userType = Integer.parseInt(sessionManager.getKeyUserType());
//                Log.d("IP:",IP);
//                Log.d ("Mac:",MACID);
                Call<LoginResponse> call = apiInterface.login(new LoginRequest(email, pass, MACID, IP, Battery));

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        try {
                            if(response.isSuccessful()){
                                if(response.body().getStatusCode()== 1){

//                                    Log.d("status", String.valueOf(response.body().getStatusCode()));
                                    String UserType = String.valueOf(response.body().getLoginData().get(0).getUserType());
                                    if(UserType.equals("1") || UserType.equals("2") || UserType.equals("3")) {
                                        closeProgressDialog();
                                        SetUserDetails(String.valueOf(response.body().getLoginData().get(0).getUserType()),response.body().getLoginData().get(0).getUserEmail(),response.body().getLoginData().get(0).getUserMobileNo(),response.body().getLoginData().get(0).getUserID(),String.valueOf(response.body().getLoginData().get(0).getCredits()));

//                                    Log.d("UserID", String.valueOf(response.body().getLoginData().get(0).getUserID()));
//                                    Log.d("UserType", UserType);
//                                    Log.d("Credits",String.valueOf(response.body().getLoginData().get(0).getCredits()));
//                                    Log.d("CreditsSession",sessionManager.getKeyUserCredits());

                                        innerInteractor.switchToMain();
                                    } else {
                                        closeProgressDialog();
                                        signInEmail.setText("");
                                        signInPassword.setText("");
                                        Snackbar.make(getActivity().findViewById(R.id.fragment_sigin), "Invalid Credentials..!!",Snackbar.LENGTH_LONG)
                                                .show();
                                    }
                                }
                                else {
                                    if(dialog.isShowing()){
                                        closeProgressDialog();
                                        signInEmail.setText("");
                                        signInPassword.setText("");
//                                        Log.d("response",response.body().getMessage());
                                        Snackbar.make(getActivity().findViewById(R.id.fragment_sigin), response.body().getMessage(),Snackbar.LENGTH_LONG)
                                                .show();
                                    }
                                }
                            }
                            else {
                                if(dialog.isShowing()){
                                    closeProgressDialog();
                                    signInEmail.setText("");
                                    signInPassword.setText("");
//                                    Log.d("response",response.body().getMessage());
                                    Snackbar.make(getActivity().findViewById(R.id.fragment_sigin), response.body().getMessage()                                                          ,Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            }
                        }catch (Exception e){
                            if(dialog.isShowing()){
                                closeProgressDialog();
                            }
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        closeProgressDialog();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                        builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                        builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                checkLogin();
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
                    }
                });

            }
            else{

                if (!isValidPassword(pass)) {
                    signInPassword.setError("Invalid Password");
                    signInPassword.requestFocus();
                }

                if (!isValidEmail(email)) {
                    signInEmail.setError("Invalid Email");
                    signInEmail.requestFocus();
                }
            }
        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            Snackbar.make(getActivity().findViewById(R.id.fragment_sigin),R.string.no_internet,Snackbar.LENGTH_LONG)
                    .show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkConnection();
    }

}

