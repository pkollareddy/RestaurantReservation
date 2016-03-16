package com.example.praneethkollareddy.restaurantreservation.adapters;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praneethkollareddy.restaurantreservation.R;
import com.example.praneethkollareddy.restaurantreservation.Reservation;
import com.example.praneethkollareddy.restaurantreservation.activities.ActOrder;
import com.example.praneethkollareddy.restaurantreservation.fragments.FragmentCancellationPolicy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

public class ReservationListAdapter extends ArrayAdapter<Reservation> {
    private final List<Reservation> reservationList;

    public static class ViewHolder {
        public TextView textView, rsvName, rsvDate, rsvTime,viewInvoice,cancellationPolicy,changeRsv,cancelRsv,orderFood,writeReview;
        public LinearLayout changeReservation, viewReservation;
    }

    public ReservationListAdapter(Context context, int resource, List<Reservation> noteList) {
        super(context, resource, noteList);
        this.reservationList = noteList;
    }

    @Override
    public View getView(int position, final View  convertView, ViewGroup parent) {
        Reservation reservation = reservationList.get(position);

        final View row;
        ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.reservation_list, null);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) row.findViewById(R.id.reservation_text);
            viewHolder.rsvName = (TextView) row.findViewById(R.id.text_reserveName);
            viewHolder.rsvDate = (TextView) row.findViewById(R.id.text_reserveDate);
            viewHolder.rsvTime = (TextView) row.findViewById(R.id.text_reserveTime);
            viewHolder.changeReservation =(LinearLayout) row.findViewById(R.id.layout_changeReservation);
            viewHolder.viewReservation =(LinearLayout) row.findViewById(R.id.layout_viewReservation);
            viewHolder.viewInvoice = (TextView) row.findViewById(R.id.text_viewInvoice);
            viewHolder.cancellationPolicy = (TextView) row.findViewById(R.id.text_cancellationPolicy);
            viewHolder.changeRsv = (TextView) row.findViewById(R.id.text_changeReservation);
            viewHolder.cancelRsv = (TextView) row.findViewById(R.id.text_cancelReservation);
            viewHolder.orderFood = (TextView) row.findViewById(R.id.text_orderFood);
            viewHolder.writeReview = (TextView) row.findViewById(R.id.text_writeReview);

            row.setTag(viewHolder);
        } else {
            row = convertView;
            viewHolder = (ViewHolder) row.getTag();
        }


        viewHolder.viewInvoice.setTag(position);

        viewHolder.viewInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AssetManager assetManager = row.getContext().getAssets();

                InputStream in = null;
                OutputStream out = null;
                File file = new File(row.getContext().getFilesDir(), "invoice.pdf");
                try {
                    in = assetManager.open("invoice.pdf");
                    out = row.getContext().openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

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
                        Uri.parse("file://" + row.getContext().getFilesDir() + "/invoice.pdf"),
                        "application/pdf");

                row.getContext().startActivity(intent);


            }

            private void copyFile(InputStream in, OutputStream out) throws IOException {
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
            }


        });


        viewHolder.cancellationPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                FragmentManager fm = row.getContext().getFragmentManager();
//
//                DialogFragment newFragment = new FragmentCancellationPolicy();
//                newFragment.show(fm, "offer1");
                Toast.makeText(row.getContext(),"policy",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.changeRsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                FragmentManager fm = row.getContext().getFragmentManager();
//
//                DialogFragment newFragment = new FragmentCancellationPolicy();
//                newFragment.show(fm, "offer1");
                Toast.makeText(row.getContext(),"change",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.cancelRsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                FragmentManager fm = row.getContext().getFragmentManager();
//
//                DialogFragment newFragment = new FragmentCancellationPolicy();
//                newFragment.show(fm, "offer1");
                Toast.makeText(row.getContext(),"cancel",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.orderFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(row.getContext(), ActOrder.class);
                row.getContext().startActivity(in);

//                FragmentManager fm = row.getContext().getFragmentManager();
//
//                DialogFragment newFragment = new FragmentCancellationPolicy();
//                newFragment.show(fm, "offer1");
                Toast.makeText(row.getContext(),"Order food for your reservation",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                FragmentManager fm = row.getContext().getFragmentManager();
//
//                DialogFragment newFragment = new FragmentCancellationPolicy();
//                newFragment.show(fm, "offer1");
                Toast.makeText(row.getContext(),"Review",Toast.LENGTH_SHORT).show();
            }
        });




        Calendar c = Calendar.getInstance();

        int daynow = c.get(Calendar.DAY_OF_MONTH);
        if(daynow> reservation.getDay()) {
            viewHolder.changeReservation.setVisibility(View.GONE);
            viewHolder.viewReservation.setVisibility(View.VISIBLE);
        }
        else
        {
            viewHolder.changeReservation.setVisibility(View.VISIBLE);
            viewHolder.viewReservation.setVisibility(View.GONE);
        }

        viewHolder.rsvName.setText(reservation.getName());
        viewHolder.rsvDate.setText(reservation.getMonth() + "/" + reservation.getDay() + "/" + reservation.getYear());
        viewHolder.rsvTime.setText(reservation.getHour() + ":" + reservation.getMinute());

        viewHolder.textView.setText("Restaurant name at " + reservation.getHour() + ":" + reservation.getMinute() + " on " + reservation.getMonth() + "/" + reservation.getDay() + "/" + reservation.getYear() + " for " + reservation.getParty() + " people.");
        return row;
    }



}
