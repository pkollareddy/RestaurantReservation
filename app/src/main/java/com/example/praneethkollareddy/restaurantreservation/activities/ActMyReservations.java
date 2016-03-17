package com.example.praneethkollareddy.restaurantreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praneethkollareddy.restaurantreservation.R;
import com.example.praneethkollareddy.restaurantreservation.databeans.Reservation;
import com.example.praneethkollareddy.restaurantreservation.adapters.ReservationListAdapter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActMyReservations extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Firebase myFirebaseRef;
    List<Reservation> reservationList = new ArrayList<>();
    ReservationListAdapter adapter;
    TextView viewInvoice,cancellationPolicy,changeRsv, cancelRsv,writeReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_my_reservations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);

        //map elements
        final ListView displayList = (ListView) findViewById(R.id.reservation_list);

        reservationList = queryReservations();
        System.out.println(reservationList.size());
        adapter = new ReservationListAdapter(ActMyReservations.this, R.layout.reservation_list, reservationList);
        displayList.setAdapter(adapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    protected List<Reservation> queryReservations() {
        myFirebaseRef = new Firebase("https://resplendent-heat-2353.firebaseio.com");
        Firebase refReservations = myFirebaseRef.child("Reservations");
        reservationList = new ArrayList<>();
        final long phone;
//        if (phoneInput.getText().toString().isEmpty()) {
//            Toast.makeText(getApplicationContext(), "Please enter a number.", Toast.LENGTH_SHORT).show();
//            return reservationList;
//        } else {
//            phone = Long.parseLong(phoneInput.getText().toString());
//        }

        phone = Long.parseLong(Main_Activity.phone);
        refReservations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " reservations");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        if (id == R.id.add_res) {
            Intent in = new Intent(getApplicationContext(), AddRes.class);
            startActivity(in);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reserveTable) {
            Intent in = new Intent(getApplicationContext(), Main_Activity.class);
            startActivity(in);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_reservations) {
            Intent in = new Intent(getApplicationContext(), ActMyReservations.class);
            startActivity(in);

        } else if (id == R.id.nav_settings) {


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







}
