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
                        DialogFragment cancelFrag = new FragmentCancellationPolicy();
                        cancelFrag.show(fm2, "cancel");

                        break;
                    case 2:
                        AssetManager assetManager = getActivity().getAssets();

                        InputStream in = null;
                        OutputStream out = null;
                        File file = new File(getActivity().getFilesDir(), "invoice.pdf");
                        try {
                            in = assetManager.open("invoice.pdf");
                            out = getActivity().openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

                            copyFile(in, out);
                            in.close();
                            in = null;
                            out.flush();
                            out.close();
                            out = null;
                        } catch (Exception e) {
                            Log.e("tag", e.getMessage());
                        }

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(
                                Uri.parse("file://" + getActivity().getFilesDir() + "/invoice.pdf"),
                                "application/pdf");

                        getActivity().startActivity(intent);

                        break;

                    case 3:
                        FragmentManager fm3 = getFragmentManager();
                        DialogFragment newFragment = new FragmentWriteReview();
                        newFragment.show(fm3, "review");
                        break;

                    case 4:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Text");
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