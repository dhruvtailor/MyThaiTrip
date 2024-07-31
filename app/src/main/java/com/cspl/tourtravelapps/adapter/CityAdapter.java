package com.cspl.tourtravelapps.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.model.CityDatum;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 08/01/2018.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityHolder> {

    private List<CityDatum> cityData;
    private List<CityDatum> mFilterList;

    OnItemClickListener mItemClickListener;

    public CityAdapter(List<CityDatum> cityData) {
        this.cityData = cityData;
        this.mFilterList = new ArrayList<CityDatum>();
        this.mFilterList.addAll(cityData);
    }

    @Override
    public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_city,parent,false));
    }

    @Override
    public void onBindViewHolder(CityHolder holder, int position) {
        if(position!=0){
            holder.tvCity.setText(cityData.get(position).getCityName());
        }
        else {
            holder.tvCity.setPadding(0,0,0,0);
            holder.tvCity.setVisibility(View.GONE);
            holder.tvCity.setHeight(0);
        }
    }

    @Override
    public int getItemCount() {
        return cityData.size();
    }


    public class CityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvCity;

        public CityHolder(View itemView) {
            super(itemView);

            tvCity = (TextView) itemView.findViewById(R.id.tvCity);
            tvCity.setOnClickListener(this);


        }
        @Override
        public void onClick(View view) {
            if (mItemClickListener!=null)
            {

                mItemClickListener.onItemClick(view,getLayoutPosition());
                /*if (view.get()==getLayoutPosition())
                {
                    tvVehicleName.setTextColor(Color.WHITE);
                }
                else
                {
                    tvVehicleName.setTextColor(Color.BLACK);
                }*/
                //tvVehicleName.setTextColor(Color.WHITE);
            }
        }

    }

    public void filter(String charText) {
        charText = charText.toLowerCase();
        cityData.clear();
        if (charText.length() == 0) {
            cityData.addAll(mFilterList);
        }
        else
        {
            CityDatum c = new CityDatum();
            c.setCityID(0);
            c.setCityName("Select City");
            cityData.add(c);
            for (CityDatum city : mFilterList)
            {
                if (city.getCityName().toLowerCase().contains(charText))
                {
                    cityData.add(city);
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
