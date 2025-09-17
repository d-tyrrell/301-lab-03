package com.example.listycitylab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Array;
import java.util.ArrayList;

public class CityArrayAdapter extends ArrayAdapter<City> {
    public CityArrayAdapter(Context context, ArrayList<City> cities){
        super(context, 0, cities);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // return a view that represents an element at a position

        View view;
        if (convertView == null) {
            // create a new view:
            view = LayoutInflater.from(getContext()).inflate(R.layout.content,
                    parent, false);
        } else {
            // if not null, just use that as our view
            view = convertView;
        }

        City city = getItem(position);
        TextView cityName = view.findViewById(R.id.city_text);
        TextView provinceName = view.findViewById(R.id.province_text);

        // set the names given the data from our objects:
        cityName.setText(city.getName());
        provinceName.setText(city.getProvince());
        return view;
    }


}
