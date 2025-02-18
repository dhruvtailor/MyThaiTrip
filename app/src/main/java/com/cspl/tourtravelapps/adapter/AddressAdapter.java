package com.cspl.tourtravelapps.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cspl.tourtravelapps.model.Address;
import com.cspl.tourtravelapps.R;

import java.util.ArrayList;

/**
 * Created by a_man on 24-01-2018.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Address> dataList;
    private int lastSelectedPos;

    public AddressAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.dataList.add(new Address("Home", "150,Vadi faliyu, Opp. Lucy shop,Ring Road Surat - 395 102"));
        this.dataList.add(new Address("Office", "340,Cursor Soft Pvt.Ltd, Belgium Tower, Opp. Liner Bus Stand,Ring Road, Surat - 395 003"));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_address, parent, false));
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
        private TextView title, addressText;
        private RadioButton selectedRadio;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.addressTitle);
            addressText = itemView.findViewById(R.id.addressText);
            selectedRadio = itemView.findViewById(R.id.addressSelected);

            selectedRadio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedRadio.isChecked()) {
                        lastSelectedPos = getAdapterPosition();
                        notifyDataSetChanged();
                    }
                }
            });
        }

        public void setData(Address address) {
            title.setText(address.getTitle());
            addressText.setText(address.getAddress());

            address.setSelected(getAdapterPosition() == lastSelectedPos);
            selectedRadio.setChecked(address.isSelected());
        }
    }
}
