package com.cspl.tourtravelapps.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cspl.tourtravelapps.MyApplication;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.RetrofitAPI.ApiClient;
import com.cspl.tourtravelapps.RetrofitAPI.ApiInterface;
import com.cspl.tourtravelapps.adapter.OrdersAdapter;
import com.cspl.tourtravelapps.helper.SessionManager;
import com.cspl.tourtravelapps.model.OrderDatum;
import com.cspl.tourtravelapps.network.OrderRequest;
import com.cspl.tourtravelapps.network.OrderResponse;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener{
    private RecyclerView recyclerOrders;
    private static ProgressDialog pDialog;
    private int userId;
    private SessionManager sessionManeger;
    private int flagID;
    private List<OrderDatum> orderData;
    private OrdersAdapter orderAdapter;

    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        initializecontrols(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupOrdersRecycler();
    }

    private void setupOrdersRecycler() {
        recyclerOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        getOrderData();
    }

    private void initializecontrols(View view){
        recyclerOrders = view.findViewById(R.id.recyclerOrders);
        pDialog = new ProgressDialog(getContext(),R.style.DialogBox);
        pDialog.setCancelable(false);
        sessionManeger = new SessionManager(getContext());
        userId = sessionManeger.getKeyUserId();
        flagID=0;
    }


    private void getOrderData(){
        boolean isInternetConnected = ConnectivityReceiver.isConnected();

        if (!isInternetConnected) {
            Snackbar.make(getActivity().findViewById(R.id.frag_order), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        } else {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            pDialog.setMessage("Getting your orders...");
            showDialog();

            Call<OrderResponse> call = apiInterface.getOrder(new OrderRequest(flagID,userId));

            call.enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    try{
                        if (response.isSuccessful()){
//                            Log.d("Status", String.valueOf(response.body().getStatusCode()));

                            if(response.body().getStatusCode()==1){
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        hideDialog();
                                    }
                                }, 1000);

                                orderData = response.body().getOrderData();
//                                Log.d("Order Count", String.valueOf(orderData.size()));
                                orderAdapter = new OrdersAdapter(getContext(),orderData);
                                recyclerOrders.setAdapter(orderAdapter);
                                orderAdapter.notifyDataSetChanged();
                            } else {
                                if (pDialog.isShowing())
                                    hideDialog();
                                Snackbar.make(getActivity().findViewById(R.id.frag_order), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        } else {
                            if (pDialog.isShowing())
                                hideDialog();
                            Snackbar.make(getActivity().findViewById(R.id.frag_order), response.message(), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }catch (Exception e){
                        if (pDialog.isShowing())
                            hideDialog();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {
                    if (pDialog.isShowing())
                        hideDialog();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getOrderData();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });

        }
    }

    private void checkConnection() {
        boolean isInternetConnected = ConnectivityReceiver.isConnected();
        showView(isInternetConnected);
    }

    private void showView(boolean isInternetConnected) {
        if (!isInternetConnected) {
            Snackbar.make(getActivity().findViewById(R.id.fragment_location), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
            
        }
    }

    private static void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
