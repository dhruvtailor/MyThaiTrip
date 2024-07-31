package com.cspl.tourtravelapps.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cspl.tourtravelapps.MyApplication;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.RetrofitAPI.ApiClient;
import com.cspl.tourtravelapps.RetrofitAPI.ApiInterface;
import com.cspl.tourtravelapps.helper.Common;
import com.cspl.tourtravelapps.helper.SessionManager;
import com.cspl.tourtravelapps.interactor.AuthInnerInteractor;
import com.cspl.tourtravelapps.network.SignUpRequest;
import com.cspl.tourtravelapps.network.SignUpResponse;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener{
    private AuthInnerInteractor innerInteractor;
    private static ProgressDialog pDialog;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private EditText edtPhone;
    private SharedPreferences shp;
    private int userType;
    private EditText edtTAT;
    private EditText edtCompanyName;
    private ImageView signupbackImageView;
    private TextView signUp;
    private TextView switchSignIn;
    private String email;
    private String pass;
    private String confirmpass;
    private String tat;
    private String phone;
    private String company;
    private String name;
    private ProgressDialog dialog;
    private SessionManager sessionManager;
    private EditText edtName;


    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(AuthInnerInteractor innerInteractor) {
        SignUpFragment fragment = new SignUpFragment();
        fragment.innerInteractor = innerInteractor;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        view.setBackgroundColor(Color.WHITE);

        initializecontrols(view);

//        checkConnection();

//        getCountryList();
        signupbackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                innerInteractor.popForgetPassword();
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString();
                pass = edtPassword.getText().toString();
                confirmpass = edtConfirmPassword.getText().toString();
                tat = edtTAT.getText().toString();
                phone = edtPhone.getText().toString();
                company = edtCompanyName.getText().toString();
                name = edtName.getText().toString();
                hideKeyboard(v);

                if(userType==1){
                    signUpAgent();
                }

                if(userType==3){
                    signUpCustomer();
                }
            }
        });

        switchSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                innerInteractor.popForgetPassword();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void initializecontrols(View view){
        sessionManager = new SessionManager(getContext());

        userType = Integer.parseInt(sessionManager.getKeyUserType());
        edtEmail = (EditText) view.findViewById(R.id.email);
        edtPassword = (EditText) view.findViewById(R.id.password);
        edtPassword.setNextFocusDownId(R.id.passwordConfirm);
        edtConfirmPassword = (EditText) view.findViewById(R.id.passwordConfirm);
        edtPhone = (EditText) view.findViewById(R.id.phone);
        edtCompanyName = (EditText) view.findViewById(R.id.companyName);
        edtTAT = (EditText) view.findViewById(R.id.tatNumber);
        edtName = (EditText)view.findViewById(R.id.name);
        signupbackImageView = (ImageView) view.findViewById(R.id.sign_up_back_iv);
        signUp = (TextView) view.findViewById(R.id.signUp);
        switchSignIn = (TextView) view.findViewById(R.id.switchSignIn);
        pDialog = new ProgressDialog(getContext(),R.style.DialogBox);
        pDialog.setCancelable(false);

        if(userType==1){
            edtCompanyName.setVisibility(View.VISIBLE);
            edtTAT.setVisibility(View.VISIBLE);
        }
    }

    private void closeProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void SetUserDetails(String userType, String userEmail,String userPhone,int userId) {
        sessionManager.setLogin(true);
        sessionManager.setKeyUserEmail(userEmail);
        sessionManager.setKeyUserPhone(userPhone);
        sessionManager.setKeyUserType(userType);
        sessionManager.setKeyUserId(userId);
    }


    //Sign Up Agent

    private void signUpAgent(){

        boolean isConnected = ConnectivityReceiver.isConnected();

        if(!isConnected){
            Snackbar.make(getActivity().findViewById(R.id.fragment_signup),R.string.no_internet,Snackbar.LENGTH_LONG)
                    .show();
        }else {
            if(isValidEmail(email) && isValidPassword(pass) && isValidConfirmPass(confirmpass,pass) &&
                    isValidTAT(tat) && isValidPhone(phone) && isValidCompany(company)){
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

                dialog = Common.showProgressDialog(getContext());
                dialog.show();

                Call<SignUpResponse> call = apiInterface.signup(new SignUpRequest(userType,email,pass,phone,company,tat));

                call.enqueue(new Callback<SignUpResponse>() {
                    @Override
                    public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                        try {
                            if(response.isSuccessful()){
                                if(response.body().getStatusCode()==1){

//                                    Log.d("status", String.valueOf(response.body().getStatusCode()));

                                    closeProgressDialog();
                                    SetUserDetails(String.valueOf(userType),response.body().getRegisterData().get(0).getUserEmail(),response.body().getRegisterData().get(0).getUserMobileNo(),response.body().getRegisterData().get(0).getUserID());

//                                    Log.d("userEmail", String.valueOf(response.body().getRegisterData().get(0).getUserEmail()));
//                                    Log.d("UserType", String.valueOf(userType));
                                    innerInteractor.switchToMain();
                                } else {
                                    closeProgressDialog();
                                    edtEmail.setText("");
                                    edtPassword.setText("");
                                    edtConfirmPassword.setText("");
                                    edtPhone.setText("");
                                    edtCompanyName.setText("");
                                    edtTAT.setText("");
//                                    Log.d("response",response.body().getMessage());
                                    Snackbar.make(getActivity().findViewById(R.id.fragment_signup), response.body().getMessage(),Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            }
                            else {
                                if(dialog.isShowing()){
                                    closeProgressDialog();
                                    edtEmail.setText("");
                                    edtPassword.setText("");
                                    edtConfirmPassword.setText("");
                                    edtPhone.setText("");
                                    edtCompanyName.setText("");
                                    edtTAT.setText("");
//                                    Log.d("response",response.body().getMessage());
                                    Snackbar.make(getActivity().findViewById(R.id.fragment_signup), response.message(),Snackbar.LENGTH_LONG)
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
                    public void onFailure(Call<SignUpResponse> call, Throwable t) {
                        closeProgressDialog();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                        builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                        builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                signUpCustomer();
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
                    }
                });
//            innerInteractor.switchToMain();
            }
            else {
                if (!isValidEmail(email)) {
                    edtEmail.setError("Invalid Email");
                }

                if (!isValidPassword(pass)) {
                    edtPassword.setError("Invalid Password");
                }

                if(!isValidConfirmPass(confirmpass,pass)){
                    edtConfirmPassword.setError("Password does not match");
                }

                if(!isValidTAT(tat)){
                    edtTAT.setError("Invalid TAT");
                }

                if(!isValidPhone(phone)){
                    edtPhone.setError("Invalid Phone");
                }

                if(!isValidCompany(company)){
                    edtCompanyName.setError("Invalid Company Name");
                }
            }
        }

    }

    //Sign Up Customer

    private void signUpCustomer(){

        boolean isConnected = ConnectivityReceiver.isConnected();

        if(!isConnected){
            Snackbar.make(getActivity().findViewById(R.id.fragment_signup),R.string.no_internet,Snackbar.LENGTH_LONG)
                    .show();
        }
        else{
            if(isValidEmail(email) && isValidPassword(pass) && isValidConfirmPass(confirmpass,pass) &&
                    isValidPhone(phone) && isValidName(name)){
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

                dialog = Common.showProgressDialog(getContext());
                dialog.show();

                Call<SignUpResponse> call = apiInterface.signup(new SignUpRequest(userType,email,name,pass,phone));

                call.enqueue(new Callback<SignUpResponse>() {
                    @Override
                    public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                        try {
                            if(response.isSuccessful()){
                                if(response.body().getStatusCode()==1){

//                                    Log.d("status", String.valueOf(response.body().getStatusCode()));

                                    closeProgressDialog();
                                    SetUserDetails(String.valueOf(userType),response.body().getRegisterData().get(0).getUserEmail(),response.body().getRegisterData().get(0).getUserMobileNo(),response.body().getRegisterData().get(0).getUserID());

//                                    Log.d("userEmail", String.valueOf(response.body().getRegisterData().get(0).getUserEmail()));
//                                    Log.d("UserType", String.valueOf(userType));
                                    innerInteractor.switchToMain();
                                } else {
                                    if(dialog.isShowing()){
                                        closeProgressDialog();
                                        edtEmail.setText("");
                                        edtPassword.setText("");
                                        edtConfirmPassword.setText("");
                                        edtPhone.setText("");
//                                        Log.d("response",response.body().getMessage());
                                        Snackbar.make(getActivity().findViewById(R.id.fragment_signup), response.body().getMessage(),Snackbar.LENGTH_LONG)
                                                .show();
                                    }
                                }
                            }
                            else {
                                if(dialog.isShowing()){
                                    closeProgressDialog();
                                    edtEmail.setText("");
                                    edtPassword.setText("");
                                    edtConfirmPassword.setText("");
                                    edtPhone.setText("");
//                                    Log.d("response",response.body().getMessage());
                                    Snackbar.make(getActivity().findViewById(R.id.fragment_signup), response.message(),Snackbar.LENGTH_LONG)
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
                    public void onFailure(Call<SignUpResponse> call, Throwable t) {
                        closeProgressDialog();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                        builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                        builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                signUpCustomer();
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
                    }
                });

//            innerInteractor.switchToMain();
            }
            else {

                if(!isValidPhone(phone)){
                    edtPhone.setError("Invalid Phone");
                    edtPhone.requestFocus();
                }

                if(!isValidConfirmPass(confirmpass,pass)){
                    edtConfirmPassword.setError("Password does not match");
                    edtConfirmPassword.requestFocus();
                }

                if (!isValidPassword(pass)) {
                    edtPassword.setError("Invalid Password");
                    edtPassword.requestFocus();
                }

                if(!isValidName(name)){
                    edtName.setError("Invalid Name");
                    edtName.requestFocus();
                }

                if (!isValidEmail(email)) {
                    edtEmail.setError("Invalid Email");
                    edtEmail.requestFocus();
                }


            }
        }

    }

    private boolean isValidConfirmPass(String confirmpass,String pass) {
        if(!confirmpass.isEmpty() && confirmpass.equals(pass)){
            return true;
        }
        return false;
    }

    private static void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 3) {
            return true;
        }
        return false;
    }

    private boolean isValidTAT(String tat){
        String TAT_PATTERN = "^\\d{2}[-/]\\d{5}$";
        Pattern pattern = Pattern.compile(TAT_PATTERN);
        Matcher matcher = pattern.matcher(tat);
        return matcher.matches();
    }

    private boolean isValidPhone(String phone){
        if(phone !=null && phone.length()>3){
            return true;
        }
        return false;
    }

    private boolean isValidCompany(String company){
        if(company !=null && company.length()>3){
            return true;
        }
        return false;
    }

    private boolean isValidName(String name){
        if(name !=null && name.length()>3){
            return true;
        }
        return false;
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            Snackbar.make(getActivity().findViewById(R.id.fragment_signup),R.string.no_internet,Snackbar.LENGTH_LONG)
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
