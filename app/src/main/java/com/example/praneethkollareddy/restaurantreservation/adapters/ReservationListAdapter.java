package com.example.praneethkollareddy.restaurantreservation.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.praneethkollareddy.restaurantreservation.R;
import com.example.praneethkollareddy.restaurantreservation.Reservation;

import java.util.List;

public class ReservationListAdapter extends ArrayAdapter<Reservation> {
    private final List<Reservation> reservationList;

    public static class ViewHolder {
        public TextView textView;
    }

    public ReservationListAdapter(Context context, int resource, List<Reservation> noteList) {
        super(context, resource, noteList);
        this.reservationList = noteList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Reservation reservation = reservationList.get(position);

        View row;
        ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.reservation_list, null);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) row.findViewById(R.id.reservation_text);
            row.setTag(viewHolder);
        } else {
            row = convertView;
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.textView.setText(reservation.getName() + " has a reservation at " + reservation.getHour() + ":" + reservation.getMinute() + " on " + reservation.getMonth() + "/" + reservation.getDay() + "/" + reservation.getYear() + " for " + reservation.getParty() + " people.");
        return row;
    }
}
