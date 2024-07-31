package com.cspl.tourtravelapps.location;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.RetrofitAPI.ApiClient;
import com.cspl.tourtravelapps.RetrofitAPI.ApiInterface;
import com.cspl.tourtravelapps.activity.MainActivity;
import com.cspl.tourtravelapps.adapter.CityAdapter;
import com.cspl.tourtravelapps.helper.SessionManager;
import com.cspl.tourtravelapps.model.CityDatum;
import com.cspl.tourtravelapps.network.CityResponse;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment implements PlaceSelectionListener {

    private ImageView close;
    private SessionManager sessionManager;
    private ImageView done;
    private RecyclerView cityRecyler;
    private static ProgressDialog pDialog;
    private List<CityDatum> cityData;
    private CityAdapter cityAdapter;
    private Integer cityID = 0;
    private String UserType;
    private int SelectedIndex=0;
    private String cityName;
    private EditText search;
    private SearchView searchView;

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        initializecontrols(view);

        checkConnection();

        getCityList();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String city = search.getText().toString().toLowerCase();
                cityAdapter.filter(city);
                cityAdapter.notifyDataSetChanged();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SelectedIndex!=0){
                    sessionManager.setKeyCityId(cityID);
                    sessionManager.setKeyCityName(cityName);
//                    Log.d("session_cityID",String.valueOf(sessionManager.getKeyCityId()));
//                    Log.d("session_cityName",sessionManager.getKeyCityName());
                }
                else{
                    sessionManager.setKeyCityId(0);
                }
                boolean isInternetConnected = ConnectivityReceiver.isConnected();

                if (!isInternetConnected) {
                    Snackbar.make(getActivity().findViewById(R.id.fragment_location), R.string.no_internet, Snackbar.LENGTH_LONG)
                            .show();
                }
                else {
                    startActivity(new Intent(getActivity(),MainActivity.class));
                }


            }
        });



//        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//        autocompleteFragment.setHint("Search other location..");
//        autocompleteFragment.setOnPlaceSelectedListener(this);

        return view;
    }

    @Override
    public void onPlaceSelected(Place place) {

    }

    @Override
    public void onError(Status status) {

    }



    private void initializecontrols(View v){
        sessionManager = new SessionManager(getContext());
        close =(ImageView) v.findViewById(R.id.close);
        done = (ImageView) v.findViewById(R.id.submit);
        cityRecyler = (RecyclerView) v.findViewById(R.id.cityRecycler);
        pDialog = new ProgressDialog(getContext(),R.style.DialogBox);
        search = (EditText) v.findViewById(R.id.search);
        pDialog.setCancelable(false);
        UserType = sessionManager.getKeyUserType();
       // searchView = (SearchView) v.findViewById(R.id.search);
        cityRecyler.setLayoutManager(new LinearLayoutManager(getContext()));
//        Log.d("user_type",UserType);
    }

    private void getCityList(){
        boolean isInternetConnected = ConnectivityReceiver.isConnected();

        if (!isInternetConnected) {
            Snackbar.make(getActivity().findViewById(R.id.fragment_location), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        } else{
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            pDialog.setMessage("Getting city list...");
            showDialog();

            Call<CityResponse> call = apiInterface.getCity();
//            Log.d("url", "" + call.request().url().toString());

            call.enqueue(new Callback<CityResponse>() {
                @Override
                public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                    try{
//                        Log.d("Response",String.valueOf(response.code()));

                        if(response.isSuccessful()){

//                            Log.d("Status",String.valueOf(response.body().getStatusCode()));

                            if(response.body().getStatusCode()==1){

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        hideDialog();
                                    }
                                }, 1000);
                                CityDatum c = new CityDatum();
                                c.setCityID(0);
                                c.setCityName("Select City");

                                cityData =response.body().getCityData();
                                cityData.add(0,c);
//                                Log.d("city count",String.valueOf(cityData.size()));
                                cityAdapter = new CityAdapter(cityData);
                                cityRecyler.setAdapter(cityAdapter);
                                cityAdapter.notifyDataSetChanged();
                                cityRecyler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

                                cityAdapter.SetOnItemClickListener(new CityAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                        if(position==SelectedIndex){
                                            cityRecyler.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                                            SelectedIndex=0;
                                        }
                                        for (int i = 1; i < cityRecyler.getChildCount(); i++) {
                                            if (position == i) {
                                                cityRecyler.getChildAt(i).setBackgroundColor(Color.parseColor("#66ccff"));
                                                cityID = cityData.get(position).getCityID();
                                                cityName = cityData.get(position).getCityName();
//                                                Log.d("cityid", cityID.toString());
//                                                Log.d("cityname", cityName.toString());
                                                SelectedIndex = i;

//                                                Log.d("SelectedIndex", String.valueOf(SelectedIndex));
                                            } else {
                                                cityRecyler.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                                            }
                                        }
                                    }
                                });
                            }else {
                                if (pDialog.isShowing())
                                    hideDialog();
                                Snackbar.make(getActivity().findViewById(R.id.fragment_location), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }
                    }catch (Exception e){
                        if (pDialog.isShowing())
                            hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<CityResponse> call, Throwable t) {
                    if (pDialog.isShowing())
                        hideDialog();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getCityList();
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


}
