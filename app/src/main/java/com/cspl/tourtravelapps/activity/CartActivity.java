package com.cspl.tourtravelapps.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cspl.tourtravelapps.MyApplication;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.RetrofitAPI.ApiClient;
import com.cspl.tourtravelapps.RetrofitAPI.ApiInterface;
import com.cspl.tourtravelapps.adapter.CartAdapter;
import com.cspl.tourtravelapps.helper.Common;
import com.cspl.tourtravelapps.helper.SessionManager;
import com.cspl.tourtravelapps.model.AddToCartDatum;
import com.cspl.tourtravelapps.network.AMZEExchnageRateResponse;
import com.cspl.tourtravelapps.network.AMZERateResponse;
import com.cspl.tourtravelapps.network.AddCartRequest;
import com.cspl.tourtravelapps.network.AddCartResponse;
import com.cspl.tourtravelapps.network.OrderRequest;
import com.cspl.tourtravelapps.network.OrderResponse;
import com.cspl.tourtravelapps.network.THBToUSDRateResponse;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by a_man on 23-01-2018.
 */

public class CartActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    private RecyclerView cartRecycler;
    private LinearLayout checkOut;
    private SessionManager sessionManager;
    private int userId;
    private ProgressDialog dialog;
    private int flagID = 0;
    private List<AddToCartDatum> cartData;
    private CartAdapter cartAdapter;
    private TextView cartCount;
    private double subTotalAmount=0;
    private TextView subTotal;
    private TextView serviceFee;
    private int serviceChargePercent = 1;
    private double serviceCharge;
    private double totalAmount;
    private TextView total;
    private String userType;
    private double userCredits;
    private TextView creditAgent;
    private LinearLayout creditLayout;
//    private Double rateUSD = 0.0;
    private Double rateAMZE = 0.0;
    private LinearLayout llQR;
    private ImageView ivQR;
    private TextView tvAddress;
    private TextView tvAmount;
    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initialisecontrols();

        getCartData();

//        getTHD_USDRate();



        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAMZERate();

//                Toast.makeText(CartActivity.this, "AMZE " + String.valueOf(totalAMZE), Toast.LENGTH_LONG).show();

                if(cartData==null || cartData.size()==0){
                    Snackbar.make(findViewById(R.id.cart),"No packages available in your cart", Snackbar.LENGTH_LONG)
                            .show();
                }
                else {
                    if(userType.equals("1")||userType.equals("2")){
                        if(userCredits!=0 && userCredits > totalAmount && cartData!=null){

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
                            alertDialog.setTitle("Confirm Checkout").setMessage("Are you sure you want to checkout?");
                            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    placeOrder();
//                            Snackbar.make(((Activity) context).findViewById(android.R.id.content),"Item Deleted",Snackbar.LENGTH_SHORT).show();
                                }
                            });

                            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            AlertDialog alert = alertDialog.create();
                            alert.show();
//                            Snackbar.make(findViewById(R.id.cart),"You have sufficient credits to pay", Snackbar.LENGTH_LONG)
//                                    .show();
                        }
                        else {
                            if(cartData!=null){
                                Snackbar.make(findViewById(R.id.cart),"You don't have sufficient credits to pay", Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }
                    }
                    else {
                        Snackbar.make(findViewById(R.id.cart),"This facility is not available currently", Snackbar.LENGTH_LONG)
                                .show();
                    }
                }
                //startActivity(new Intent(CartActivity.this, CheckoutActivity.class));
            }
        });
    }

    private void setupCartRecycler() {
//        cartRecycler.setAdapter(new CartAdapter(this));
    }

    private void initialisecontrols() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cartRecycler = findViewById(R.id.cartRecycler);
        cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        cartCount = findViewById(R.id.cartCount);
        cartCount.setBackground(null);
        checkOut = findViewById(R.id.checkout);
        subTotal = findViewById(R.id.subTotal);
        subTotal.setText(getString(R.string.thb)+" 0");
        serviceFee = findViewById(R.id.serviceFee);
        serviceFee.setText(getString(R.string.thb)+" 0");
        total = findViewById(R.id.total);
        total.setText("Pay "+getString(R.string.thb)+" 0");
        creditLayout = findViewById(R.id.creditLayout);
        creditAgent = findViewById(R.id.agentCredits);
        sessionManager = new SessionManager(getApplicationContext());
        userId = sessionManager.getKeyUserId();
        userType = sessionManager.getKeyUserType();
        llQR = findViewById(R.id.llQR);
        ivQR = findViewById(R.id.ivQR);
        tvAmount = findViewById(R.id.tvAmount);
        tvAddress = findViewById(R.id.tvAddress);
        if(userType.equals("1")||userType.equals("2")){
            userCredits = Double.parseDouble(sessionManager.getKeyUserCredits());
            //Log.d("userCredits",String.valueOf(userCredits));
        }
        if(userType.equals("1")||userType.equals("2")){
            creditLayout.setVisibility(View.VISIBLE);
            creditAgent.setText(getString(R.string.thb)+" "+String.valueOf(userCredits));
        }
        else {
            creditLayout.setVisibility(View.GONE);
        }
        df = new DecimalFormat("0.0000");
    }

    private void getAMZERate() {
        boolean isConnected = ConnectivityReceiver.isConnected();

        if (!isConnected) {
            Snackbar.make(findViewById(R.id.cart), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        } else {
            ApiInterface apiInterface = ApiClient.getClientExchange().create(ApiInterface.class);

            final ProgressDialog dialog1 = Common.showProgressDialog(CartActivity.this);
            dialog1.show();

            Call<AMZERateResponse> call = apiInterface.getAMZERate();
//            Log.d("url", "" + call.request().url().toString());

            call.enqueue(new Callback<AMZERateResponse>() {
                @Override
                public void onResponse(Call<AMZERateResponse> call, Response<AMZERateResponse> response) {
                    try {
//                        Log.d("Response", String.valueOf(response.code()));
                        if (response.isSuccessful()) {
//                            Log.d("Status", String.valueOf(response.body().getStatusCode()));

                            if (response.body().getAMZE() != null) {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog1.dismiss();
                                    }
                                }, 1000);

                                rateAMZE = response.body().getAMZE().getThb();
                                Double totalAMZE = totalAmount / rateAMZE;
                                Bitmap QR = CreateCode(df.format(totalAMZE), BarcodeFormat.QR_CODE, 250, 250);
                                ivQR.setImageBitmap(QR);
                                tvAmount.setText("Amount : " + df.format(totalAMZE) + " AMZE");
                                llQR.setVisibility(View.VISIBLE);
                                Log.d("AMZE Rate", String.valueOf(totalAMZE));
                            } else {
                                dialog1.dismiss();
                                Snackbar.make(findViewById(R.id.frag_add_cart), "Error..!!", Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        } else {
                            dialog1.dismiss();
                            Snackbar.make(findViewById(R.id.frag_add_cart), response.message(), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    } catch (Exception e) {
                        dialog1.dismiss();
                        Log.d("AMZE rate",e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AMZERateResponse> call, Throwable t) {
                    dialog1.dismiss();
                    Log.d("AMZE rate", t.getStackTrace().toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getAMZERate();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }
    }

    public Bitmap CreateCode(String str, BarcodeFormat type, int bmpWidth, int bmpHeight) throws WriterException {
        Log.d("AMZE rate",str);
        str = "ethereum:0x4EF519EC0108A7fa29C96860c076667A7Fe7C8D4@56/transfer?address=0xf21BD49D2A6255409d67C8eDbE67cEDcbF319175&uint256=" + str + "e18";
        tvAddress.setText("0xf21BD49D2A6255409d67C8eDbE67cEDcbF319175");
        BitMatrix matrix = new MultiFormatWriter().encode(str, type, bmpWidth, bmpHeight);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = 0xfff9f9f9;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

//    private void getAMZERate() {
//        boolean isConnected = ConnectivityReceiver.isConnected();
//
//        if (!isConnected) {
//            Snackbar.make(findViewById(R.id.cart), R.string.no_internet, Snackbar.LENGTH_LONG)
//                    .show();
//        } else {
//            ApiInterface apiInterface = ApiClient.getClientExchange().create(ApiInterface.class);
//
//            final ProgressDialog dialog1 = Common.showProgressDialog(CartActivity.this);
//            dialog1.show();
//
//            Call<AMZEExchnageRateResponse> call = apiInterface.getAMZERate();
////            Log.d("url", "" + call.request().url().toString());
//
//            call.enqueue(new Callback<AMZEExchnageRateResponse>() {
//                @Override
//                public void onResponse(Call<AMZEExchnageRateResponse> call, Response<AMZEExchnageRateResponse> response) {
//                    try {
////                        Log.d("Response", String.valueOf(response.code()));
//                        if (response.isSuccessful()) {
////                            Log.d("Status", String.valueOf(response.body().getStatusCode()));
//
//                            if (response.body().getMessage().equals("OK")) {
//
//                                Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    public void run() {
//                                        dialog1.dismiss();
//                                    }
//                                }, 1000);
//
//                                rateAMZE = Double.valueOf(response.body().getData().getTickers().get(0).getLastPrice());
//                                Log.d("AMZE rate", String.valueOf(rateAMZE));
//                            } else {
//                                dialog1.dismiss();
//                                Snackbar.make(findViewById(R.id.frag_add_cart), response.body().getMessage(), Snackbar.LENGTH_LONG)
//                                        .show();
//                            }
//                        } else {
//                            dialog1.dismiss();
//                            Snackbar.make(findViewById(R.id.frag_add_cart), response.message(), Snackbar.LENGTH_LONG)
//                                    .show();
//                        }
//                    } catch (Exception e) {
//                        dialog1.dismiss();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<AMZEExchnageRateResponse> call, Throwable t) {
//                    dialog1.dismiss();
////                    Log.d("AMZE rate", t.getStackTrace().toString());
//                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
//                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
//                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
//                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            getAMZERate();
//                        }
//                    });
//                    builder.setCancelable(false);
//                    builder.show();
//                }
//            });
//        }
//    }
//
//    private void getTHD_USDRate() {
//        boolean isConnected = ConnectivityReceiver.isConnected();
//
//        if (!isConnected) {
//            Snackbar.make(findViewById(R.id.cart), R.string.no_internet, Snackbar.LENGTH_LONG)
//                    .show();
//        } else {
//            ApiInterface apiInterface = ApiClient.getClientCurrency().create(ApiInterface.class);
//
//            final ProgressDialog dialog2 = Common.showProgressDialog(CartActivity.this);
//            dialog2.show();
//
//            Call<THBToUSDRateResponse> call = apiInterface.getTHB_USDRate();
////            Log.d("url", "" + call.request().url().toString());
//
//            call.enqueue(new Callback<THBToUSDRateResponse>() {
//                @Override
//                public void onResponse(Call<THBToUSDRateResponse> call, Response<THBToUSDRateResponse> response) {
//                    try {
////                        Log.d("Response", String.valueOf(response.code()));
//                        if (response.isSuccessful()) {
////                            Log.d("Status", String.valueOf(response.body().getStatusCode()));
//
//                            if (response.body().getBase().equals("THB")) {
//
//                                Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    public void run() {
//                                        dialog2.dismiss();
//                                    }
//                                }, 1000);
//
//                                rateUSD = response.body().getExchangeRates().getUsd();
//                                Log.d("USD rate", String.valueOf(rateUSD));
//                            } else {
//                                dialog2.dismiss();
//                                Snackbar.make(findViewById(R.id.frag_add_cart), "Error", Snackbar.LENGTH_LONG)
//                                        .show();
//                            }
//                        } else {
//                            dialog2.dismiss();
//                            Snackbar.make(findViewById(R.id.frag_add_cart), response.message(), Snackbar.LENGTH_LONG)
//                                    .show();
//                        }
//                    } catch (Exception e) {
//                        dialog2.dismiss();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<THBToUSDRateResponse> call, Throwable t) {
//                    dialog2.dismiss();
////                    Log.d("AMZE rate", t.getStackTrace().toString());
//                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
//                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
//                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
//                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            getTHD_USDRate();
//                        }
//                    });
//                    builder.setCancelable(false);
//                    builder.show();
//                }
//            });
//        }
//    }

    private void getCartData() {
        boolean isInternetConnected = ConnectivityReceiver.isConnected();

        if (!isInternetConnected) {
            Snackbar.make(findViewById(R.id.cart), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        } else {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            dialog = Common.showProgressDialog(CartActivity.this);
            dialog.show();

            Call<AddCartResponse> call = apiInterface.getCart(new AddCartRequest(flagID, userId));

            call.enqueue(new Callback<AddCartResponse>() {
                @Override
                public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getStatusCode() == 1) {
                                //Log.d("status", String.valueOf(response.body().getStatusCode()));
                                closeProgressDialog();
                                findViewById(R.id.cart).setBackgroundColor(Color.TRANSPARENT);
                                findViewById(R.id.emptyLayout).setVisibility(View.GONE);
                                cartData = response.body().getAddToCartData();
                                //Log.d("cart count", String.valueOf(cartData.size()));

                                cartCount.setBackgroundResource(R.drawable.bg_primary_rounded_corner_8dp);
                                cartCount.setText(String.valueOf(cartData.size()));
                                //refreshCart();
                                cartAdapter = new CartAdapter(CartActivity.this, cartData);
                                cartRecycler.setAdapter(cartAdapter);
                                cartAdapter.notifyDataSetChanged();

                                for (int i=0;i<cartData.size();i++){
                                    subTotalAmount += cartData.get(i).getTotalAmount();
                                }

                                cartAdapter.SetOnItemClickListener(new CartAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, final int position) {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
                                        alertDialog.setTitle("Confirm Delete").setMessage("Are you sure you want to delete?");
                                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                boolean isConnected = ConnectivityReceiver.isConnected();
                                                if (!isConnected) {
                                                    Snackbar.make(findViewById(R.id.cart), R.string.no_internet, Snackbar.LENGTH_LONG)
                                                            .show();
                                                } else {
                                                    int cartItemId = cartData.get(position).getSrNo();
                                                    cartData.remove(position);
                                                    //refreshCart();
                                                    if(cartData.size()==0){
                                                        findViewById(R.id.cart).setBackgroundColor(Color.WHITE);
                                                        findViewById(R.id.emptyLayout).setVisibility(View.VISIBLE);
                                                    } else {
                                                        findViewById(R.id.cart).setBackgroundColor(Color.TRANSPARENT);
                                                        findViewById(R.id.emptyLayout).setVisibility(View.GONE);
                                                    }
                                                    subTotalAmount=0;
                                                    cartAdapter.notifyDataSetChanged();
                                                    for (int j=0;j<cartData.size();j++){
                                                        subTotalAmount += cartData.get(j).getTotalAmount();
                                                    }
                                                    subTotal.setText(getString(R.string.thb)+" "+String.valueOf(subTotalAmount));
                                                    serviceCharge = (subTotalAmount*serviceChargePercent)/100;
                                                    serviceFee.setText(getString(R.string.thb)+" "+String.valueOf(serviceCharge));
                                                    totalAmount = subTotalAmount+serviceCharge;
                                                    total.setText("Pay "+getString(R.string.thb)+" "+String.valueOf(totalAmount));
                                                    if(cartData.size()!=0)
                                                    {
                                                        cartCount.setText(String.valueOf(cartData.size()));
                                                    }
                                                    else {
                                                        cartCount.setBackgroundResource(R.color.transparent);
                                                        cartCount.setText("");
                                                    }
                                                    int flagId = 3;
                                                    deleteItem(flagId,cartItemId,userId);
                                                }
//                            Snackbar.make(((Activity) context).findViewById(android.R.id.content),"Item Deleted",Snackbar.LENGTH_SHORT).show();
                                            }
                                        });

                                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                        AlertDialog alert = alertDialog.create();
                                        alert.show();
                                    }
                                });

                                subTotal.setText(getString(R.string.thb)+" "+String.valueOf(subTotalAmount));
                                serviceCharge = (subTotalAmount*serviceChargePercent)/100;
                                serviceFee.setText(getString(R.string.thb)+" "+String.valueOf(serviceCharge));
                                totalAmount = subTotalAmount+serviceCharge;
                                total.setText("Pay "+getString(R.string.thb)+" "+String.valueOf(totalAmount));
                            } else {
                                if (dialog.isShowing()) {
                                    closeProgressDialog();
                                    //Log.d("response", response.body().getMessage());
                                    findViewById(R.id.cart).setBackgroundColor(Color.WHITE);
                                    findViewById(R.id.emptyLayout).setVisibility(View.VISIBLE);
//                                    Snackbar.make(findViewById(R.id.cart), response.body().getMessage(), Snackbar.LENGTH_LONG)
//                                            .show();
                                }

                            }
                        } else {
                            if (dialog.isShowing()) {
                                closeProgressDialog();
                                //Log.d("response", response.message());
                                Snackbar.make(findViewById(R.id.cart), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }
                    } catch (Exception e) {
                        if(dialog.isShowing()){
                            closeProgressDialog();
                        }
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<AddCartResponse> call, Throwable t) {
                    closeProgressDialog();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getCartData();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }
    }

    private void deleteItem(final int flagId, final int cartItemId,final  int userId){
        boolean isConnected = ConnectivityReceiver.isConnected();

        if (!isConnected) {
            Snackbar.make(findViewById(R.id.cart), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        } else {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//            dialog = Common.showProgressDialog((Activity)context);
//            dialog.show();

            Call<AddCartResponse> call = apiInterface.deleteCart(new AddCartRequest(flagId,cartItemId,userId));

            call.enqueue(new Callback<AddCartResponse>() {
                @Override
                public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                    try{
                        if(response.isSuccessful()){
                            if(response.body().getStatusCode()==0){
                                //Log.d("status", String.valueOf(response.code()));
                                //Log.d("message",String.valueOf(response.body().getMessage()));
                                Snackbar.make(findViewById(R.id.cart), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                        .show();
                                //closeProgressDialog();
                            }else {
                                if (dialog.isShowing()) {
                                    //closeProgressDialog();
                                    //Log.d("response", response.body().getMessage());
                                    Snackbar.make(findViewById(R.id.cart), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            }
                        }else {
                            if (dialog.isShowing()) {
                                //closeProgressDialog();
                                //Log.d("response", response.message());
                                Snackbar.make(findViewById(R.id.cart), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }
                    }catch (Exception e){
                        if(dialog.isShowing()){
                            //closeProgressDialog();
                        }
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<AddCartResponse> call, Throwable t) {
                    //closeProgressDialog();
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle(R.string.NetworkErrorTitle);
                    builder.setMessage(R.string.NetworkErrorMsg);
                    builder.setPositiveButton(R.string.NetworkErrorBtnTxt, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteItem(flagId, cartItemId, userId);
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });

        }
    }

    private void placeOrder(){
        boolean isConnected = ConnectivityReceiver.isConnected();

        if (!isConnected) {
            Snackbar.make(findViewById(R.id.cart), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        } else {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            dialog = Common.showProgressDialog(this);
            dialog.show();

            List<OrderRequest> orderRequests = new ArrayList<>();

            
            for(int i=0;i<cartData.size();i++){
                flagID = 1;
                //Log.d("csr",String.valueOf(cartData.get(i).getSrNo()));
                double packageTotal = cartData.get(i).getTotalAmount();
                double serviceRate = (packageTotal * serviceChargePercent) / 100;
                double packageTotalwithSC = packageTotal + ((packageTotal * serviceChargePercent) / 100);
                //Log.d("Package Total:",String.valueOf(packageTotal));
                //Log.d("Package SC:",String.valueOf(serviceRate));
                //Log.d("Package Total with SC:",String.valueOf(packageTotalwithSC));
                //Log.d("FghtNo",cartData.get(i).getFlightNo());
                //Log.d("FghtArrive",cartData.get(i).getFlightArrivalTime());
                OrderRequest or = new OrderRequest(flagID,
                        cartData.get(i).getSrNo(),
                        cartData.get(i).getCartPackageID(),
                        cartData.get(i).getCartUserID(),
                        cartData.get(i).getNoOfChild(),
                        cartData.get(i).getNoOfAdults(),
                        cartData.get(i).getNoOfInFant(),
                        cartData.get(i).getTravelDate(),
                        packageTotalwithSC,
                        serviceRate,
                        cartData.get(i).getCustName(),
                        cartData.get(i).getCustEmail(),
                        cartData.get(i).getCustPhone(),
                        cartData.get(i).getHotelName(),
                        cartData.get(i).getHotelShortAddress(),
                        cartData.get(i).getFlightNo(),
                        cartData.get(i).getApproxDepartureTime(),
                        cartData.get(i).getFlightArrivalTime(),
                        cartData.get(i).getVehicleType(),
                        String.valueOf(cartData.get(i).getNoOfVehicle()),
                        cartData.get(i).getCustPickupTime()
                        );
                orderRequests.add(or);
            }

            //Log.d("or", new JSONArray(orderRequests).toString());

            Call<OrderResponse> call = apiInterface.addOrder(orderRequests);

            call.enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    try {

                        if (response.isSuccessful()) {
                            if (response.body().getStatusCode() == 1) {
                                //Log.d("status", String.valueOf(response.body().getStatusCode()));
                                closeProgressDialog();
                                //Log.d("message", String.valueOf(response.body().getMessage()));
                                userCredits = userCredits - totalAmount;
                                DecimalFormat df = new DecimalFormat(".##");
                                sessionManager.setKeyUserCredits(String.valueOf(df.format(userCredits)));
                                creditAgent.setText(getString(R.string.thb)+" "+String.valueOf(df.format(userCredits)));

                                startActivity(new Intent(CartActivity.this, CartActivity.class));
                                finish();

                            } else {
                                if (dialog.isShowing()) {
                                    closeProgressDialog();
                                    //Log.d("response1", response.body().toString());
                                    Snackbar.make(findViewById(R.id.cart), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            }
                        } else {
                            if (dialog.isShowing()) {
                                closeProgressDialog();
                                //Log.d("response2", response.errorBody().string());
                                Snackbar.make(findViewById(R.id.cart), response.message(), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }
                    } catch (Exception e) {
                        if (dialog.isShowing()) {
                            closeProgressDialog();
                        }
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {
                    closeProgressDialog();
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            placeOrder();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
//            userCredits = userCredits - totalAmount;
//            DecimalFormat df = new DecimalFormat(".##");
//            creditAgent.setText(getString(R.string.thb)+" "+String.valueOf(df.format(userCredits)));
        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            Snackbar.make(findViewById(R.id.cart), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    private void closeProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
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
