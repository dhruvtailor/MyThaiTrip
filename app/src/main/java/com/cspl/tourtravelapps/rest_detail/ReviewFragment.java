package com.cspl.tourtravelapps.rest_detail;


import android.app.AlertDialog;
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
import android.widget.TextView;

import com.cspl.tourtravelapps.MyApplication;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.RetrofitAPI.ApiClient;
import com.cspl.tourtravelapps.RetrofitAPI.ApiInterface;
import com.cspl.tourtravelapps.adapter.ReviewsAdapter;
import com.cspl.tourtravelapps.model.PackageReview;
import com.cspl.tourtravelapps.network.ReviewRequest;
import com.cspl.tourtravelapps.network.ReviewResponse;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFragment extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener {
    private RecyclerView recyclerReviews;
    private static String EXTRA_PACKAGE_ID = "package_id";
    private int pkgId=0;
    private int flagID=0;
    private List<PackageReview> reviewData;
    private ReviewsAdapter reviewAdapter;
    private TextView noReview;

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        initializeViews(view);

        return view;
    }

    private void initializeViews(View view){
        recyclerReviews = view.findViewById(R.id.recyclerReviews);
        noReview = view.findViewById(R.id.noReview);
        pkgId = getActivity().getIntent().getIntExtra(EXTRA_PACKAGE_ID, 0);
        getFeedback();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupReviewsRecycler();
    }

    private void setupReviewsRecycler() {
        recyclerReviews.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerReviews.setAdapter(new ReviewsAdapter(getContext()));
    }

    private void getFeedback(){
        boolean isConnected = ConnectivityReceiver.isConnected();

        if (!isConnected) {
            Snackbar.make(getActivity().findViewById(R.id.frag_review), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        }
        else {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<ReviewResponse> call = apiInterface.getReviews(new ReviewRequest(flagID,pkgId));

            call.enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                    try {
                        if (response.isSuccessful()){
                            if (response.body().getStatusCode()==1){
//                                Log.d("Response", String.valueOf(response.body().getMessage()));
                                noReview.setVisibility(View.GONE);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {

                                    }
                                }, 1000);

                                reviewData = response.body().getPackageReview();
                                //if(reviewData.size()==0)
//                                Log.d("Review Count", String.valueOf(reviewData.size()));

                                reviewAdapter = new ReviewsAdapter(getContext(),reviewData);

                                recyclerReviews.setAdapter(reviewAdapter);
                                reviewAdapter.notifyDataSetChanged();
                            } else {
//                                Snackbar.make(getActivity().findViewById(R.id.frag_review), response.body().getMessage(), Snackbar.LENGTH_LONG)
//                                        .show();
                                noReview.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Snackbar.make(getActivity().findViewById(R.id.frag_review), response.message(), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ReviewResponse> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getFeedback();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    private void checkConnection() {
        boolean isInternetConnected = ConnectivityReceiver.isConnected();
        showView(isInternetConnected);
    }

    private void showView(boolean isInternetConnected) {
        if (!isInternetConnected) {
            Snackbar.make(getActivity().findViewById(R.id.frag_review), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkConnection();
    }
}
