package com.example.praneethkollareddy.restaurantreservation.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.praneethkollareddy.restaurantreservation.R;
import com.example.praneethkollareddy.restaurantreservation.adapters.ReservationListAdapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by INSPIRON on 17-Mar-16.
 */
public class FragmentReservationOptions extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(R.array.options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, final int which) {
                // The 'which' argument contains the index position
                // of the selected item
                switch (which) {
                    case 0:
                        FragmentManager fm1 = getFragmentManager();
                        DialogFragment changeFrag = new FragmentChangeReservation();
                        changeFrag.show(fm1, "change");

                        break;
                    case 1:
                        FragmentManager fm2 = getFragmentManager();
                        DialogFragment cancelFrag = new FragmentCancelReservation();
                        cancelFrag.show(fm2, "cancel");

                        break;
                    case 2:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, ReservationListAdapter.rShare);
                        sendIntent.setType("text/plain");
                        getActivity().startActivity(sendIntent);
                        break;
                }


            }
        });
        return builder.create();
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}