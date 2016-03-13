package com.example.praneethkollareddy.restaurantreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.praneethkollareddy.restaurantreservation.ActOrder;
import com.example.praneethkollareddy.restaurantreservation.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActResDetails extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Button btn_makeReservation, btn_orderFood;
    String res_name, res_dollar_range, res_waittime, res_rating, res_cuisine;
    GoogleMap resmap;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_res_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .enableAutoManage(this, this)
                    .build();
        }
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.resmap);
        mapFragment.getMapAsync((this));
        resmap = mapFragment.getMap();

        resmap.getUiSettings().setScrollGesturesEnabled(false);
        resmap.getUiSettings().setZoomControlsEnabled(false);
        resmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent in = new Intent(getApplicationContext(),AddRes.class);
                startActivity(in);
            }
        });


        Intent in = getIntent();
        res_name = in.getStringExtra("res_name");
        res_cuisine = in.getStringExtra("res_cuisine");
        res_dollar_range = in.getStringExtra("res_dollar_range");
        res_rating = in.getStringExtra("res_rating");
        res_waittime = in.getStringExtra("res_waittime");


        setTitle(res_name);


        btn_makeReservation = (Button) findViewById(R.id.btn_makeReservation);
        btn_makeReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(), MakeReservation.class);
                in.putExtra("res_name", res_name);
                startActivity(in);

            }
        });

        btn_orderFood = (Button) findViewById(R.id.btn_orderFood);
        btn_orderFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),ActOrder.class);
                in.putExtra("res_name",res_name);
                startActivity(in);

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        resmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.3, -121.9), 13));
        resmap.addMarker(new MarkerOptions().position(new LatLng(37.3, -121.9)).title("Restaurant"));




    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
