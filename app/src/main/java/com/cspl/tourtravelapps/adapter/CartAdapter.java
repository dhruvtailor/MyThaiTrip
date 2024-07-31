package com.cspl.tourtravelapps.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.cspl.tourtravelapps.RetrofitAPI.ApiClient;
import com.cspl.tourtravelapps.RetrofitAPI.ApiInterface;
import com.cspl.tourtravelapps.helper.SessionManager;
import com.cspl.tourtravelapps.model.AddToCartDatum;
import com.cspl.tourtravelapps.network.AddCartRequest;
import com.cspl.tourtravelapps.network.AddCartResponse;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cspl.tourtravelapps.helper.Common.IMG_PATH;
import static com.cspl.tourtravelapps.helper.Common.convertDateFormat;

/**
 * Created by a_man on 24-01-2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> implements ConnectivityReceiver.ConnectivityReceiverListener  {
    private int userId;
    private Context context;
    private List<AddToCartDatum> cartData;
    private ProgressDialog dialog;
    private SessionManager sessionManager;
    private int flagId = 3;

    OnItemClickListener mItemClickListener;

    public CartAdapter(Context context, List<AddToCartDatum> cartData) {
        this.context = context;
        this.cartData = cartData;
        /*this.dataList = new ArrayList<>();
        this.dataList.add(new CartItem("Safari world", 1, 300, R.drawable.rest_res_1));
        this.dataList.add(new CartItem("Pataya", 1, 300, R.drawable.rest_res_2));*/
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(cartData.get(position));
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, priceTotal,adults,childs,infants,travelDate;
        private ImageView itemImage,editItem,deleteItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            priceTotal = itemView.findViewById(R.id.itemPriceTotal);
            adults = itemView.findViewById(R.id.itemAdults);
            childs = itemView.findViewById(R.id.itemChilds);
            infants = itemView.findViewById(R.id.itemInfants);
            travelDate = itemView.findViewById(R.id.itemTravelDate);
            itemImage = itemView.findViewById(R.id.itemImage);
            editItem = itemView.findViewById(R.id.editItem);
            deleteItem = itemView.findViewById(R.id.deleteItem);
            sessionManager = new SessionManager(context);

            userId = sessionManager.getKeyUserId();

            editItem.setEnabled(false);
            editItem.setVisibility(View.GONE);
            deleteItem.setEnabled(true);
            deleteItem.setOnClickListener(this);
            /*editItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("Confirm Delete").setMessage("Are you sure you want to delete?");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int pos = getAdapterPosition();
                            int cartItemId = cartData.get(pos).getSrNo();
                            cartData.remove(pos);
                            notifyItemChanged(pos);
                            deleteItem(flagId,cartItemId,userId);
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
            });*/
           /* itemView.findViewById(R.id.itemQuantityMinus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (dataList.get(pos).getQuantity() > 1) {
                        dataList.get(pos).setQuantity(dataList.get(pos).getQuantity() - 1);
                        notifyItemChanged(pos);
                    }
                }
            });
            itemView.findViewById(R.id.itemQuantityPlus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    dataList.get(pos).setQuantity(dataList.get(pos).getQuantity() + 1);
                    notifyItemChanged(pos);
                }
            });*/
        }

        public void setData(AddToCartDatum cartItem) {
            name.setText(cartItem.getCategoryName());
            priceTotal.setText(context.getString(R.string.thb) + " " + cartItem.getTotalAmount());
            adults.setText("Adults:  "+String.valueOf(cartItem.getNoOfAdults()));
            childs.setText("Childs:  "+String.valueOf(cartItem.getNoOfChild()));
            infants.setText("Infants: "+String.valueOf(cartItem.getNoOfInFant()));
            try {
                travelDate.setText("Date: "+convertDateFormat(cartItem.getTravelDate(),"yyyy-MM-dd","dd-MM-yyyy"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            if(cartItem.getCategoryName().toLowerCase().contains("safari")){
//                itemImage.setImageResource(R.drawable.rest_res_1);
//            }else if(cartItem.getCategoryName().toLowerCase().contains("coral")){
//                itemImage.setImageResource(R.drawable.rest_res_2);
//            }
//            else if(cartItem.getCategoryName().toLowerCase().contains("chao")){
//                itemImage.setImageResource(R.drawable.charo_phraya);
//            }
//            else if(cartItem.getCategoryName().toLowerCase().contains("old city")){
//                itemImage.setImageResource(R.drawable.bangkok_city_tour);
//            }
//            else if(cartItem.getCategoryName().toLowerCase().contains("alcazar")){
//                itemImage.setImageResource(R.drawable.alcazar);
//            }
//            else if(cartItem.getCategoryName().toLowerCase().contains("sriracha")){
//                itemImage.setImageResource(R.drawable.sriracha);
//            }
//            else if(cartItem.getCategoryName().toLowerCase().contains("phi")){
//                itemImage.setImageResource(R.drawable.phi_phi);
//            }
//            quantity.setText(String.valueOf(cartItem.getQuantity()));
            Glide.with(context).load(IMG_PATH+cartItem.getPackageImg()).into(itemImage);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener!=null)
            {
                mItemClickListener.onItemClick(view,getLayoutPosition());
            }
        }
    }


    private void deleteItem(final int flagId, final int cartItemId,final  int userId){
        boolean isConnected = ConnectivityReceiver.isConnected();

        if (!isConnected) {
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), R.string.no_internet, Snackbar.LENGTH_LONG)
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
                                //closeProgressDialog();
                            }else {
                                if (dialog.isShowing()) {
                                    //closeProgressDialog();
                                    //Log.d("response", response.body().getMessage());
                                    Snackbar.make(((Activity) context).findViewById(android.R.id.content), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            }
                        }else {
                            if (dialog.isShowing()) {
                                //closeProgressDialog();
                                //Log.d("response", response.body().getMessage());
                                Snackbar.make(((Activity) context).findViewById(android.R.id.content), response.body().getMessage(), Snackbar.LENGTH_LONG)
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    private void closeProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        MyApplication.getInstance().setConnectivityListener(this);
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final CartAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkConnection();
    }
}
