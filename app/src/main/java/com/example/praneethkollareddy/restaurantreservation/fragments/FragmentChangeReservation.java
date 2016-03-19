package com.example.praneethkollareddy.restaurantreservation.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.praneethkollareddy.restaurantreservation.R;

/**
 * Created by INSPIRON on 15-Mar-16.
 */
public class FragmentChangeReservation extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // Use the Builder class for convenient dialog construction
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        builder.setView(inflater.inflate(R.layout.frag_change_rsv, null));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.frag_change_rsv, null);
        builder.setView(v);


        final NumberPicker day = (NumberPicker) v.findViewById(R.id.picker_day);
        final NumberPicker month = (NumberPicker) v.findViewById(R.id.picker_month);
        final NumberPicker year = (NumberPicker) v.findViewById(R.id.picker_year);
        final NumberPicker hour = (NumberPicker) v.findViewById(R.id.picker_hour);
        final NumberPicker min = (NumberPicker) v.findViewById(R.id.picker_min);
        final NumberPicker party = (NumberPicker) v.findViewById(R.id.picker_party);


        day.setValue(17);
        day.setSelected(true);
        day.setMinValue(1);
        day.setMaxValue(31);
        day.setWrapSelectorWheel(true);

        month.setValue(3);
        month.setSelected(true);
        month.setMinValue(1);
        month.setMaxValue(12);
        month.setWrapSelectorWheel(true);

        year.setValue(2016);
        year.setSelected(true);
        year.setMinValue(2016);
        year.setMaxValue(2017);
        year.setWrapSelectorWheel(true);

        hour.setValue(16);
        hour.setSelected(true);
        hour.setMinValue(01);
        hour.setMaxValue(24);
        hour.setWrapSelectorWheel(true);

        min.setValue(16);
        min.setSelected(true);
        min.setMinValue(1);
        min.setMaxValue(59);
        min.setWrapSelectorWheel(true);

        party.setValue(2);
        party.setSelected(true);
        party.setMinValue(1);
        party.setMaxValue(10);
        party.setWrapSelectorWheel(true);


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Toast.makeText(getActivity(), "Change confirmed", Toast.LENGTH_SHORT).show();
                    }
                })
                .setTitle("Change reservation");


        // Create the AlertDialog object and return it
        return builder.create();
    }
}

