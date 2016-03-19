package com.example.praneethkollareddy.restaurantreservation.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.praneethkollareddy.restaurantreservation.R;

/**
 * Created by INSPIRON on 16-Mar-16.
 */
public class FragmentCancelReservation extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle("Confirm cancellation")
                .setMessage("Are you sure you want to cancel this reservation?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FragmentManager fm2 = getFragmentManager();
                        DialogFragment cancelFrag = new FragmentCancellationPolicy();
                        cancelFrag.show(fm2, "cancellation policy");

                        Toast.makeText(getActivity(), "I don't think so", Toast.LENGTH_SHORT).show();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
