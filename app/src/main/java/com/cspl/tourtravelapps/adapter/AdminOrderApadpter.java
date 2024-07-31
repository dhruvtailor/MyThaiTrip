package com.cspl.tourtravelapps.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.helper.Common;
import com.cspl.tourtravelapps.model.AdminOrder;

import java.util.ArrayList;

/**
 * Created by HP on 08/18/2018.
 */

public class AdminOrderApadpter extends RecyclerView.Adapter<AdminOrderApadpter.MyViewHolder> {
    private Context context;
    private ArrayList<AdminOrder> dataList;
    OnItemClickListener mItemClickListener;

    public AdminOrderApadpter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.dataList.add(new AdminOrder("22-08-2018", "Safari World ticket only",1717.0,2,0,0,1));
        this.dataList.add(new AdminOrder("24-08-2018", "Coral Island with lunch and transfer ", 1111.00,1,1,0,0));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_admin_order, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView travelDate, pkgOrderName, orderNoOfAdults, orderNoOfChilds, orderNoOfInfants, orderPkgTotal,orderPkgStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            travelDate = itemView.findViewById(R.id.pkgOrderDate);
            pkgOrderName = itemView.findViewById(R.id.orderPkgName);
            orderNoOfAdults = itemView.findViewById(R.id.orderNoOfAdults);
            orderNoOfChilds = itemView.findViewById(R.id.orderNoOfChilds);
            orderNoOfInfants = itemView.findViewById(R.id.orderNoOfInfants);
            orderPkgTotal = itemView.findViewById(R.id.orderPkgTotal);
            orderPkgStatus = itemView.findViewById(R.id.orderPkgStatus);

            itemView.setOnClickListener(this);
        }

        public void setData(AdminOrder adminOrder) {
            travelDate.setText(Common.getDateMonthName(adminOrder.getTravelDate()).replace(" ","\n"));
            pkgOrderName.setText(adminOrder.getPackageName());
            orderNoOfAdults.setText("Adults:  "+adminOrder.getNoOfAdult());
            orderNoOfChilds.setText("Childs:  "+adminOrder.getNoOfChild());
            Log.d("DateMonth", Common.getDateMonthName(adminOrder.getTravelDate()));
            orderNoOfInfants.setText("Infants: "+adminOrder.getNoOfInfant());
            orderPkgTotal.setText(context.getString(R.string.thb)+" "+ String.valueOf(adminOrder.getOrderTotal()));
            orderPkgStatus.setText(adminOrder.getIsDriverAssigned() == 0 ? "Driver not assigned":"Driver Assigned");
            orderPkgStatus.setBackground(ContextCompat.getDrawable(context, adminOrder.getIsDriverAssigned() == 0 ? R.drawable.round_circle_accent : R.drawable.round_primary));
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener!=null)
            {
                mItemClickListener.onItemClick(view,getLayoutPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
