package com.example.praneethkollareddy.restaurantreservation.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

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


        final NumberPicker day= (NumberPicker) v.findViewById(R.id.picker_day);
        final NumberPicker month= (NumberPicker) v.findViewById(R.id.picker_month);
        final NumberPicker year= (NumberPicker) v.findViewById(R.id.picker_year);
        final NumberPicker hour= (NumberPicker) v.findViewById(R.id.picker_hour);
        final NumberPicker min= (NumberPicker) v.findViewById(R.id.picker_min);

        day.setMinValue(1);
        day.setMaxValue(31);
        day.setWrapSelectorWheel(false);

        month.setMinValue(1);
        month.setMaxValue(12);
        month.setWrapSelectorWheel(false);

        year.setMinValue(2016);
        year.setMaxValue(2017);
        year.setWrapSelectorWheel(false);

        hour.setMinValue(01);
        hour.setMaxValue(24);
        hour.setWrapSelectorWheel(false);

        min.setMinValue(1);
        min.setMaxValue(59);
        min.setWrapSelectorWheel(false);




        // Create the AlertDialog object and return it
        return builder.create();
    }
}

