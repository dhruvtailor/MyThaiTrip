package com.cspl.tourtravelapps.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cspl.tourtravelapps.MyApplication;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.RetrofitAPI.ApiClient;
import com.cspl.tourtravelapps.RetrofitAPI.ApiInterface;
import com.cspl.tourtravelapps.helper.Common;
import com.cspl.tourtravelapps.helper.SessionManager;
import com.cspl.tourtravelapps.network.SupportRequest;
import com.cspl.tourtravelapps.network.SupportResponse;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportFragment extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener {

    private EditText supportName;
    private EditText supportEmail;
    private EditText supportMessage;
    private TextView supportSend;
    private SessionManager sessionManager;
    private ProgressDialog dialog;

    public SupportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        initializeviews(view);

        supportSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                addSupportDetail();
            }
        });

        return view;
    }

    private void initializeviews(View view){

        sessionManager = new SessionManager(getContext());
        supportName = (EditText) view.findViewById(R.id.supportName);
        supportEmail = (EditText) view.findViewById(R.id.supportEmail);
        supportEmail.setText(sessionManager.getKeyUserEmail().toString());
        supportMessage = (EditText) view.findViewById(R.id.supportMessage);
        supportSend = (TextView) view.findViewById(R.id.supportSend);

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

    private boolean isValidName(String name) {
        if (name != null && name.length() > 3) {
            return true;
        }
        return false;
    }

    private boolean isValidMessage(String message) {
        if (message != null && message.length() > 3) {
            return true;
        }
        return false;
    }



    private void addSupportDetail(){
        boolean isConnected = ConnectivityReceiver.isConnected();

        if(!isConnected){
            Snackbar.make(getActivity().findViewById(R.id.frag_support),R.string.no_internet,Snackbar.LENGTH_LONG)
                    .show();
        }
        else {
            final String name = supportName.getText().toString();
            final String email = supportEmail.getText().toString();
            final String message = supportMessage.getText().toString();
            if(isValidName(name)&&isValidMessage(message)){

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                dialog = Common.showProgressDialog(getContext());
                dialog.show();

                int user_Id = sessionManager.getKeyUserId();
//                Log.d("user_Id",String.valueOf(user_Id));



                Call<SupportResponse> call = apiInterface.addSupportDetails(new SupportRequest(user_Id,name,email,message));

                call.enqueue(new Callback<SupportResponse>() {
                    @Override
                    public void onResponse(Call<SupportResponse> call, Response<SupportResponse> response) {
                        try {
                            if(response.isSuccessful()){
                                if(response.body().getStatusCode()==1) {
//                                    Log.d("status", String.valueOf(response.body().getStatusCode()));
//                                    Log.d("message", String.valueOf(response.body().getMessage()));
                                    closeProgressDialog();
                                    supportName.setText("");
                                    supportEmail.setText("");
                                    supportMessage.setText("");
                                    Snackbar.make(getActivity().findViewById(R.id.frag_support), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                            .show();
                                } else {
                                    if(dialog.isShowing()){
                                        closeProgressDialog();
                                        supportName.setText("");
                                        supportEmail.setText("");
                                        supportMessage.setText("");
//                                        Log.d("response",response.body().getMessage());
                                        Snackbar.make(getActivity().findViewById(R.id.frag_support), response.body().getMessage(),Snackbar.LENGTH_LONG)
                                                .show();
                                    }
                                }
                            }else {
                                if(dialog.isShowing()){
                                    closeProgressDialog();
                                    supportName.setText("");
                                    supportEmail.setText("");
                                    supportMessage.setText("");
//                                    Log.d("response",response.body().getMessage());
                                    Snackbar.make(getActivity().findViewById(R.id.frag_support), response.message(),Snackbar.LENGTH_LONG)
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
                    public void onFailure(Call<SupportResponse> call, Throwable t) {
                        closeProgressDialog();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                        builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                        builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                addSupportDetail();
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
                    }
                });

            }
            else {
                if (!isValidName(name)) {
                    supportName.setError("Please enter valid name");
                }

                if (!isValidMessage(message)) {
                    supportName.setError("Please enter valid message");
                }
            }
        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            Snackbar.make(getActivity().findViewById(R.id.frag_support),R.string.no_internet,Snackbar.LENGTH_LONG)
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
