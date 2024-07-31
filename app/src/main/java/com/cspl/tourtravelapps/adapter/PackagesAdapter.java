package com.cspl.tourtravelapps.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cspl.tourtravelapps.MyApplication;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.model.PackageDatum;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;
import com.cspl.tourtravelapps.rest_detail.PackageDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.cspl.tourtravelapps.helper.Common.IMG_PATH;

/**
 * Created by a_man on 22-01-2018.
 */

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.MyViewHolder> implements ConnectivityReceiver.ConnectivityReceiverListener{

    private Context context;
    private List<PackageDatum> packagesData;
    private List<PackageDatum> mFilterList;
    private ArrayList subpackages= new ArrayList<>();

    public PackagesAdapter(Context context, List<PackageDatum> packagesData) {
        this.context = context;
        this.packagesData = packagesData;
        this.mFilterList = new ArrayList<PackageDatum>();
        this.mFilterList.addAll(packagesData);
    }

//    public PackagesAdapter(Context context) {
//        this.context = context;
//        this.dataList = new ArrayList<>();
//        this.dataList.add(new Restaurant("Safari world", " ", R.drawable.rest_res_1));
//        this.dataList.add(new Restaurant("Pataya", " ", R.drawable.rest_res_2));
//        this.dataList.add(new Restaurant("City tour", " ", R.drawable.rest_res_3));
//        this.dataList.add(new Restaurant("Bangkok", " ", R.drawable.rest_res_4));
//        this.dataList.add(new Restaurant("Safari world", " ", R.drawable.rest_res_1));
//        this.dataList.add(new Restaurant("Pataya", " ", R.drawable.rest_res_2));
//        this.dataList.add(new Restaurant("City tour", " ", R.drawable.rest_res_3));
//        this.dataList.add(new Restaurant("Bangkok", " ", R.drawable.rest_res_4));
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(packagesData.get(position));
    }

    @Override
    public int getItemCount() {
        return packagesData.size();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkConnection();
    }

    private void checkConnection() {
        boolean isInternetConnected = ConnectivityReceiver.isConnected();
        if (!isInternetConnected) {
            Snackbar.make(((Activity) context).findViewById(R.id.frag_home), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView paymentMode;
        private final TextView avgTime;
        private TextView name, restMinOrderPrice;
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.restName);
            avgTime = itemView.findViewById(R.id.avgTime);
            restMinOrderPrice = itemView.findViewById(R.id.restMinOrderPrice);
            paymentMode = itemView.findViewById(R.id.paymentMode);
            imageView = itemView.findViewById(R.id.restRes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isInternetConnected = ConnectivityReceiver.isConnected();
                    if (!isInternetConnected) {
                        Snackbar.make(((Activity) context).findViewById(R.id.frag_home), R.string.no_internet, Snackbar.LENGTH_LONG)
                                .show();
                    }
                    else {
                        context.startActivity(PackageDetailActivity.newIntent(context, packagesData.get(getAdapterPosition())));
                    }

                }
            });
        }

        public void setData(PackageDatum packageDatum) {
            name.setText(packageDatum.getPackageName());
            avgTime.setText(packageDatum.getAverageTime());
            restMinOrderPrice.setText("\u0E3F"+" "+String.valueOf(packageDatum.getPackageRate()));
            paymentMode.setText(packageDatum.getPaymentMode());
            Glide.with(context).load(IMG_PATH+packageDatum.getPackageImg()).into(imageView);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase();
        packagesData.clear();
        if (charText.length() == 0) {
            packagesData.addAll(mFilterList);
        }
        else
        {
            for (PackageDatum packages : mFilterList)
            {
                if (packages.getPackageName().toLowerCase().contains(charText))
                {
                    packagesData.add(packages);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterType(String charText) {
        charText = charText.toLowerCase();
        packagesData.clear();
        if (charText.length() == 0) {
            packagesData.addAll(mFilterList);
        }
        else
        {
            if(charText.equalsIgnoreCase("Normal")){
                for (PackageDatum pod : mFilterList)
                {
                    if (pod.getPackageTypeName().equalsIgnoreCase("N"))
                    {
                        packagesData.add(pod);
                    }
                }
            }
            else if(charText.equalsIgnoreCase("Transfer")){
                for (PackageDatum pod : mFilterList)
                {
                    if (!pod.getPackageTypeName().equalsIgnoreCase("N"))
                    {
                        packagesData.add(pod);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        MyApplication.getInstance().setConnectivityListener(this);
    }
}
