package com.cspl.tourtravelapps.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cspl.tourtravelapps.MyApplication;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.RetrofitAPI.ApiClient;
import com.cspl.tourtravelapps.RetrofitAPI.ApiInterface;
import com.cspl.tourtravelapps.activity.CartActivity;
import com.cspl.tourtravelapps.activity.RefineActivity;
import com.cspl.tourtravelapps.adapter.FoodCategoryAdapter;
import com.cspl.tourtravelapps.adapter.PackagesAdapter;
import com.cspl.tourtravelapps.helper.SessionManager;
import com.cspl.tourtravelapps.model.PackageDatum;
import com.cspl.tourtravelapps.network.PackageRequest;
import com.cspl.tourtravelapps.network.PackageResponse;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener{
    private RecyclerView recyclerFood, recyclerPackages;
    private TextView cartNotificationCount;
    private static ProgressDialog pDialog;
    private SessionManager sessionManager;
    private List<PackageDatum> packageData;
    private PackagesAdapter packageAdapter;
    private EditText searchPackages;
    private Button transfer;
    private Button normal;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        View cartActionView = menu.findItem(R.id.action_cart).getActionView();
        cartNotificationCount = ((TextView) cartActionView.findViewById(R.id.notification_count));
        cartActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInternetConnected = ConnectivityReceiver.isConnected();

                if (!isInternetConnected) {
                    Snackbar.make(getActivity().findViewById(R.id.frag_home), R.string.no_internet, Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    startActivity(new Intent(getContext(), CartActivity.class));
                }
            }
        });
        setCartCount();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setCartCount() {
        int NOTIFICATION_COUNT = 0;
        if (cartNotificationCount != null) {
            if (NOTIFICATION_COUNT <= 0) {
                cartNotificationCount.setVisibility(View.GONE);
            } else {
                cartNotificationCount.setVisibility(View.VISIBLE);
                cartNotificationCount.setText(String.valueOf(NOTIFICATION_COUNT));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sessionManager = new SessionManager(getContext());
        pDialog = new ProgressDialog(getContext(),R.style.DialogBox);
        pDialog.setCancelable(false);
        recyclerFood = view.findViewById(R.id.recyclerFood);
        recyclerFood.setVisibility(View.GONE);
        recyclerPackages = view.findViewById(R.id.recyclerPackages);
        searchPackages = view.findViewById(R.id.search_Package);
        transfer = view.findViewById(R.id.transfer);
        normal = view.findViewById(R.id.noraml);

        searchPackages.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pkg = searchPackages.getText().toString().toLowerCase();
                packageAdapter.filter(pkg);
            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packageAdapter.filterType(transfer.getText().toString());
                packageAdapter.notifyDataSetChanged();
            }
        });

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packageAdapter.filterType(normal.getText().toString());
                packageAdapter.notifyDataSetChanged();
            }
        });

        view.findViewById(R.id.refine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RefineActivity.class));
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerFood();
        setupRecyclerPackages();

    }

    private void setupRecyclerPackages() {
        recyclerPackages.setNestedScrollingEnabled(false);
        recyclerPackages.setLayoutManager(new LinearLayoutManager(getContext()));
        checkConnection();
        getPackages();
//        recyclerPackages.setAdapter(new PackagesAdapter(getContext()));
    }

    private void setupRecyclerFood() {
        recyclerFood.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerFood.setAdapter(new FoodCategoryAdapter(getContext()));
    }

    private void getPackages(){
        boolean isInternetConnected = ConnectivityReceiver.isConnected();

        if (!isInternetConnected) {
            Snackbar.make(getActivity().findViewById(R.id.frag_home), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        } else{
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            pDialog.setMessage("Getting packages...");
            showDialog();
            //Log.d("cityid",String.valueOf(sessionManager.getKeyCityId()));
            Call<PackageResponse> call = apiInterface.getPackageDetails(new PackageRequest(sessionManager.getKeyCityId(),Integer.parseInt(sessionManager.getKeyUserType())));

            call.enqueue(new Callback<PackageResponse>() {
                @Override
                public void onResponse(Call<PackageResponse> call, Response<PackageResponse> response) {
                    try {
                        //Log.d("Response",String.valueOf(response.code()));

                        if(response.isSuccessful()){
                            //Log.d("Status",String.valueOf(response.body().getStatusCode()));

                            if(response.body().getStatusCode()==1){
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        hideDialog();
                                    }
                                }, 1000);

                                packageData = response.body().getPackageData();
                                //Log.d("package count",String.valueOf(packageData.size()));

                                packageAdapter = new PackagesAdapter(getContext(),packageData);
                                recyclerPackages.setAdapter(packageAdapter);
                                packageAdapter.notifyDataSetChanged();
                            }
                            else {
                                if (pDialog.isShowing())
                                    hideDialog();
                                Snackbar.make(getActivity().findViewById(R.id.frag_home), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }
                        else {
                            if (pDialog.isShowing())
                                hideDialog();
                            Snackbar.make(getActivity().findViewById(R.id.frag_home), response.message(), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                    catch (Exception e){
                        if (pDialog.isShowing())
                            hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<PackageResponse> call, Throwable t) {
                    if (pDialog.isShowing())
                        hideDialog();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getPackages();
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
            Snackbar.make(getActivity().findViewById(R.id.frag_home), R.string.no_internet, Snackbar.LENGTH_LONG)
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

    private static void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sessionManager.removeCity();
    }
}
