package com.example.praneethkollareddy.restaurantreservation.adapters;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import com.example.praneethkollareddy.restaurantreservation.databeans.Reservation;
import com.example.praneethkollareddy.restaurantreservation.activities.ActOrder;
import com.example.praneethkollareddy.restaurantreservation.fragments.FragmentCancelReservation;
import com.example.praneethkollareddy.restaurantreservation.fragments.FragmentCancellationPolicy;
import com.example.praneethkollareddy.restaurantreservation.fragments.FragmentChangeReservation;
import com.example.praneethkollareddy.restaurantreservation.fragments.FragmentWriteReview;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservationListAdapter extends ArrayAdapter<Reservation> {
    private final List<Reservation> reservationList;
    String rMonth,rTime, rName, rDate, rResName, rParty, rGenTime;
    public static String rShare;

    public static class ViewHolder {
        public TextView rsvMonth,partySize, genTime, resName, rsvName, rsvDate, rsvTime, viewInvoice, cancellationPolicy, changeRsv, cancelRsv, orderFood, writeReview, share;
        public LinearLayout changeReservation, viewReservation, rsv_layout;
    }

    public ReservationListAdapter(Context context, int resource, List<Reservation> noteList) {
        super(context, resource, noteList);
        this.reservationList = noteList;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        Reservation reservation = reservationList.get(position);

        String hr, min;
        if (reservation.getHour() < 10) hr = "0" + String.valueOf(reservation.getHour());
        else hr = String.valueOf(reservation.getHour());

        if (reservation.getMinute() < 10) min = "0" + String.valueOf(reservation.getMinute());
        else min = String.valueOf(reservation.getMinute());


        rTime = hr + ":" + min;
        rDate = String.valueOf(reservation.getDay());
        rMonth = new SimpleDateFormat("MMM").format(new Date());
        //new DateFormatSymbols().getMonths()[reservation.getMonth()];
        rName = reservation.getName();
        rParty = String.valueOf(reservation.getParty());
        rResName = reservation.getrName();
        rGenTime = reservation.getGenTime();

        StringBuilder sb = new StringBuilder();
        sb.append("Reservation confirmed: ");
        sb.append(rResName);
        sb.append("on ");
        sb.append(rDate);
        sb.append(" at ");
        sb.append(rTime);
        sb.append(" for party of ");
        sb.append(rParty);
        sb.append(" Address: 637 El Camino Real, Santa Clara, CA, 95050 Tel:654-854-9854");

        rShare = sb.toString();

        final View row;
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.reservation_list, null);

            viewHolder = new ViewHolder();
            viewHolder.partySize = (TextView) row.findViewById(R.id.text_rsvParty);
            viewHolder.genTime = (TextView) row.findViewById(R.id.text_rsvGenTime);
            viewHolder.resName = (TextView) row.findViewById(R.id.text_rsvRname);
            viewHolder.rsvName = (TextView) row.findViewById(R.id.text_rsvName);
            viewHolder.rsvDate = (TextView) row.findViewById(R.id.text_rsvDay);
            viewHolder.rsvMonth = (TextView) row.findViewById(R.id.text_rsvMonth);

            viewHolder.rsvTime = (TextView) row.findViewById(R.id.text_rsvTime);
           // viewHolder.changeReservation = (LinearLayout) row.findViewById(R.id.layout_changeReservation);
            //viewHolder.viewReservation = (LinearLayout) row.findViewById(R.id.layout_viewReservation);
            //viewHolder.viewInvoice = (TextView) row.findViewById(R.id.text_viewInvoice);
            //viewHolder.cancellationPolicy = (TextView) row.findViewById(R.id.text_cancellationPolicy);
            //viewHolder.changeRsv = (TextView) row.findViewById(R.id.text_changeReservation);
            //viewHolder.cancelRsv = (TextView) row.findViewById(R.id.text_cancelReservation);
            //viewHolder.orderFood = (TextView) row.findViewById(R.id.text_orderFood);
            //viewHolder.writeReview = (TextView) row.findViewById(R.id.text_writeReview);
            //viewHolder.share = (TextView) row.findViewById(R.id.text_share);
            //viewHolder.rsv_layout = (LinearLayout)row.findViewById(R.id.layout_rsv);

            row.setTag(viewHolder);
        } else {
            row = convertView;
            viewHolder = (ViewHolder) row.getTag();
        }


//        viewHolder.share.setTag(position);
//        viewHolder.share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, rShare);
//                sendIntent.setType("text/plain");
//                row.getContext().startActivity(sendIntent);
//
//            }
//        });
//
//
//        viewHolder.viewInvoice.setTag(position);
//
//        viewHolder.viewInvoice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                AssetManager assetManager = row.getContext().getAssets();
//
//                InputStream in = null;
//                OutputStream out = null;
//                File file = new File(row.getContext().getFilesDir(), "invoice.pdf");
//                try {
//                    in = assetManager.open("invoice.pdf");
//                    out = row.getContext().openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
//
//                    copyFile(in, out);
//                    in.close();
//                    in = null;
//                    out.flush();
//                    out.close();
//                    out = null;
//                } catch (Exception e) {
//                    Log.e("tag", e.getMessage());
//                }
//
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(
//                        Uri.parse("file://" + row.getContext().getFilesDir() + "/invoice.pdf"),
//                        "application/pdf");
//
//                row.getContext().startActivity(intent);
//
//
//            }
//
//            private void copyFile(InputStream in, OutputStream out) throws IOException {
//                byte[] buffer = new byte[1024];
//                int read;
//                while ((read = in.read(buffer)) != -1) {
//                    out.write(buffer, 0, read);
//                }
//            }
//
//
//        });
//
//
//        viewHolder.cancellationPolicy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Context context = row.getContext();
//                FragmentManager fm = ((Activity) context).getFragmentManager();
//                DialogFragment newFragment = new FragmentCancellationPolicy();
//                newFragment.show(fm, "offer1");
//                //Toast.makeText(row.getContext(),"policy",Toast.LENGTH_SHORT).show();
//            }
//        });
//        viewHolder.changeRsv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Context context = row.getContext();
//                FragmentManager fm = ((Activity) context).getFragmentManager();
//                DialogFragment newFragment = new FragmentChangeReservation();
//                newFragment.show(fm, "offer1");
//            }
//        });
//        viewHolder.cancelRsv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//               final Context context = row.getContext();
//                FragmentManager fm = ((Activity) context).getFragmentManager();
//                DialogFragment newFragment = new FragmentCancelReservation();
//                newFragment.show(fm, "review");
//            }
//        });
//        viewHolder.orderFood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent in = new Intent(row.getContext(), ActOrder.class);
//                row.getContext().startActivity(in);
//                Toast.makeText(row.getContext(), "Order food for your reservation", Toast.LENGTH_SHORT).show();
//            }
//        });
//        viewHolder.writeReview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Context context = row.getContext();
//                FragmentManager fm = ((Activity) context).getFragmentManager();
//                DialogFragment newFragment = new FragmentWriteReview();
//                newFragment.show(fm, "review");
//            }
//        });


//        Calendar c = Calendar.getInstance();
//
//        int daynow = c.get(Calendar.DAY_OF_MONTH);
//        if (daynow > reservation.getDay()) {
//            viewHolder.changeReservation.setVisibility(View.GONE);
//            viewHolder.viewReservation.setVisibility(View.VISIBLE);
//            viewHolder.rsv_layout.setBackgroundColor(Color.parseColor("#b90000"));
//
//        } else {
//            viewHolder.changeReservation.setVisibility(View.VISIBLE);
//            viewHolder.viewReservation.setVisibility(View.GONE);
//            viewHolder.rsv_layout.setBackgroundColor(Color.parseColor("#00585F"));
//
//        }

        viewHolder.rsvName.setText(rName);
        viewHolder.rsvDate.setText(rDate);
        viewHolder.rsvTime.setText(rTime);
        viewHolder.partySize.setText(rParty);
        viewHolder.rsvMonth.setText(rMonth);

        viewHolder.resName.setText(rResName);
        viewHolder.genTime.setText(rGenTime);
        return row;
    }


}
