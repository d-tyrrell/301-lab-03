package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);

        void onCitySaved(City existingCity);
//        void onCitySaved(City city);
    }
    private AddCityDialogListener listener;
    private static final String ARG_CITY = "arg_city";  // key for bundle

    // bundle the city data in a fragment, so android studio can recreate it when re-creating the fragment
    static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("arg_city", city);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);



        City existing_city;
        Bundle arguments = getArguments();

        // unpack bundle, if args != null, we are editing a city
        if (arguments != null)  {
            existing_city = (City) arguments.getSerializable(ARG_CITY);
        } else {
            existing_city = null;
        }

        // fill edit fields:
        if (existing_city != null) {
            editCityName.setText(existing_city.getName());
            editProvinceName.setText(existing_city.getProvince());
        }

        // set string title and positive button:
        String title = (existing_city == null) ? "Add a city" : "Edit city";
        String positive = (existing_city == null) ? "Add" : "Save";

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(positive, (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    // if not in edit mode(no city was provided from the bundle)
                    if (existing_city == null) {
                        // add mode, create a new city
                        listener.addCity(new City(cityName, provinceName));
                    }
                    else {
                        // edit mode, edit existing city object
                        existing_city.setName(cityName);
                        existing_city.setProvince(provinceName);
                        listener.onCitySaved(existing_city);
                    }

                })
                .create();
    }
}
