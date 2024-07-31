package com.cspl.tourtravelapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.model.CountryDatum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 08/02/2018.
 */

public class CountryAdapter extends BaseAdapter {

    Context context;
    List<CountryDatum> countryList = new ArrayList<>();
    private TextView tvCountry;

    public CountryAdapter(Context context, List<CountryDatum> countryList) {
        this.context = context;
        this.countryList = countryList;
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Object getItem(int i) {
        return countryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return countryList.get(i).getCountryID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_country,viewGroup,false);
        tvCountry = (TextView) v.findViewById(R.id.tvCountry);
        tvCountry.setText(countryList.get(i).getCountryName());
        return v;
    }
}
