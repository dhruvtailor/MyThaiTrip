package com.cspl.tourtravelapps.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.model.OrderDatum;

import java.text.ParseException;
import java.util.List;

import static com.cspl.tourtravelapps.helper.Common.IMG_PATH;
import static com.cspl.tourtravelapps.helper.Common.convertDateFormat;
import static com.cspl.tourtravelapps.helper.Common.getDateMonthName;

/**
 * Created by a_man on 24-01-2018.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {
    private Context context;
    private List<OrderDatum> orderList;

    public OrdersAdapter(Context context,List<OrderDatum> orderList) {
        this.context = context;
        this.orderList = orderList;
//        this.dataList = new ArrayList<>();
//        this.dataList.add(new Order("06 Jul", "Belgium Tower", "Ring Road, Surat", 1225, 0));
//        this.dataList.add(new Order("07 Jun", "Sugar and spice chef", "Amboli, Surat", 980, 1));
//        this.dataList.add(new Order("05 May", "Old spice chef", "Yogi chowk, Surat", 1258, 1));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(orderList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View orderPreparingContainer;
        private TextView orderDate, orderPlaceName,orderCustName, orderCustEmail,orderHotelName,
                orderNoOfAdult,orderNoOfChild,orderNoOfInfant,orderTotal, orderTotalAmount, orderStatus;
        private ImageView orderImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            orderPreparingContainer = itemView.findViewById(R.id.orderPreparingContainer);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderPlaceName = itemView.findViewById(R.id.orderPlaceName);
            orderCustName = itemView.findViewById(R.id.orderCustName);
            orderCustEmail = itemView.findViewById(R.id.orderCustEmail);
            orderHotelName = itemView.findViewById(R.id.orderHotelName);
            orderImage = itemView.findViewById(R.id.orderImage);
            orderNoOfAdult = itemView.findViewById(R.id.noOfAdultOrder);
            orderNoOfChild = itemView.findViewById(R.id.noOfChildOrder);
            orderNoOfInfant = itemView.findViewById(R.id.noOfInfantOrder);
            //orderPlaceAddress = itemView.findViewById(R.id.orderPlaceAddress);
            orderTotal = itemView.findViewById(R.id.orderTotal);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderTotalAmount = itemView.findViewById(R.id.orderTotalAmount);
        }

        public void setData(OrderDatum orderDatum) {
            orderPreparingContainer.setVisibility(orderDatum.getConfirmed() == true ? View.VISIBLE : View.GONE);
            try {
                orderDatum.setTravelDate(getDateMonthName(convertDateFormat(orderDatum.getTravelDate(),"yyyy-MM-dd'T'HH:mm:ss","dd-MM-yyyy")).replace(" ", "\n"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            orderDate.setText(orderDatum.getTravelDate());
            orderPlaceName.setText(orderDatum.getCategoryName());
            orderTotal.setText(context.getString(R.string.thb)+" "+String.valueOf(orderDatum.getTotalAmount()));
            orderCustName.setText(orderDatum.getCustName());
            orderCustEmail.setText(orderDatum.getCustEmail());
            orderHotelName.setText(orderDatum.getHotelName());
            Glide.with(context).load(IMG_PATH+orderDatum.getPackageImg()).into(orderImage);
            orderNoOfAdult.setText(String.valueOf(orderDatum.getNoOfAdult()));
            orderNoOfChild.setText(String.valueOf(orderDatum.getNoOfChild()));
            orderNoOfInfant.setText(String.valueOf(orderDatum.getNoOfInFant()));
            orderTotalAmount.setText(context.getString(R.string.thb)+" "+String.valueOf(orderDatum.getTotalAmount()));
            orderStatus.setText(orderDatum.getConfirmed() == true ? "Confirmed" : "Waiting");
            orderStatus.setBackground(ContextCompat.getDrawable(context, orderDatum.getConfirmed() == true ? R.drawable.round_circle_accent : R.drawable.round_primary));
        }
    }
}
