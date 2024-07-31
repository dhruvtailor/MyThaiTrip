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
import com.cspl.tourtravelapps.model.RestaurantMenu;

import java.util.ArrayList;

/**
 * Created by a_man on 24-01-2018.
 */

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<RestaurantMenu> dataList;

    public RestaurantMenuAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.dataList.add(new RestaurantMenu("Safari World Ticket Only", "Safari World only ticket", 700, R.drawable.rest_res_1));
        this.dataList.add(new RestaurantMenu("Safari World,Marine Park and Lunch", "Safari World ticket with Marine Park and Lunch", 850, R.drawable.rest_res_1));
        this.dataList.add(new RestaurantMenu("Safari World,Marine Park,Lunch(Join in Transfer)", "Safari World ticket with Marine Park,Lunch and Transfer", 900, R.drawable.rest_res_1));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName, itemDescription, itemPrice;
        private ImageView itemImage, menuItemAction;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.menuItemName);
            itemDescription = itemView.findViewById(R.id.menuItemDescription);
            itemPrice = itemView.findViewById(R.id.menuItemPrice);
            itemImage = itemView.findViewById(R.id.menuItemImage);
            menuItemAction = itemView.findViewById(R.id.menuItemAction);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // context.startActivity(FoodDetailActivity.newIntent(context, dataList.get(getAdapterPosition())));
                }
            });

            menuItemAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    dataList.get(pos).setAdded(!dataList.get(pos).isAdded());
                    notifyItemChanged(pos);
                }
            });
        }

        public void setData(RestaurantMenu restaurantMenu) {
            Glide.with(context).load(restaurantMenu.getImageRes()).into(itemImage);
            itemPrice.setText(String.valueOf(restaurantMenu.getPrice())+"\u0E3F");
            itemDescription.setText(restaurantMenu.getDescription());
            itemName.setText(restaurantMenu.getName());
            menuItemAction.setImageDrawable(ContextCompat.getDrawable(context, restaurantMenu.isAdded() ? R.drawable.ic_done_primary_24dp : R.drawable.ic_add_circle_primary_24dp));
        }
    }
}
