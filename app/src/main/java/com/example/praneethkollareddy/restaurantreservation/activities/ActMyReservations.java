package com.example.praneethkollareddy.restaurantreservation.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.praneethkollareddy.restaurantreservation.R;
import com.example.praneethkollareddy.restaurantreservation.Reservation;
import com.example.praneethkollareddy.restaurantreservation.adapters.ReservationListAdapter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActMyReservations extends AppCompatActivity {
    Firebase myFirebaseRef;
    List<Reservation> reservationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_my_reservations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView displayList = (ListView) findViewById(R.id.reservation_list);
        final ReservationListAdapter adapter = new ReservationListAdapter(this, R.layout.reservation_list, reservationList);
        displayList.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reservationList = queryReservations();
                adapter.notifyDataSetChanged();
            }
        });
    }

    protected List<Reservation> queryReservations() {
        myFirebaseRef = new Firebase("https://resplendent-heat-2353.firebaseio.com");
        Firebase refReservations = myFirebaseRef.child("Reservations");
        reservationList = new ArrayList<>();
        EditText phoneInput = (EditText) findViewById(R.id.phoneSearchEntry);
        final long phone;
        if (phoneInput.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter a number.", Toast.LENGTH_SHORT).show();
            return reservationList;
        } else {
            phone = Long.parseLong(phoneInput.getText().toString());
        }
        refReservations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " reservations");
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Reservation reservation = postSnapshot.getValue(Reservation.class);
                    if (!reservation.getPhone().isEmpty()) {
                        if (Long.parseLong(reservation.getPhone()) == phone) {
                            System.out.println(postSnapshot.getKey());
                            reservationList.add(reservation);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        return reservationList;
    }

}
