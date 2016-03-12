package com.example.praneethkollareddy.restaurantreservation.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praneethkollareddy.restaurantreservation.GeocodingLocation;
import com.example.praneethkollareddy.restaurantreservation.R;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;
import java.util.Map;

public class AddRes extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Firebase myFirebaseRef;
    EditText name, waittime, cuisine, street, city, state, zipcode;
    RadioGroup rg_rating, rg_dollar_range;
    String radio_rating, radio_dollar_range;
    TextView mLongitude, mLatitude;
    Button btn_getLocation;
    public String Latitude, Longitude;

    private static final int LOCATION_PERMISSION = 1;
    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_res);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

//map elements with xml content
            name = (EditText) findViewById(R.id.editText_name);
            //address = (EditText) findViewById(R.id.editText_add);
            rg_rating = (RadioGroup) findViewById(R.id.rg_rating);
            rg_dollar_range = (RadioGroup) findViewById(R.id.rg_dollar_range);
            cuisine = (EditText) findViewById(R.id.editText_cuisine);
            waittime = (EditText) findViewById(R.id.editText_waittime);
            Button btn_add = (Button) findViewById(R.id.button_add);
            mLongitude = (TextView) findViewById(R.id.textview_longitude);
            mLatitude = (TextView) findViewById(R.id.textview_latitude);
            btn_getLocation = (Button) findViewById(R.id.btn_getLocation);
            street = (EditText) findViewById(R.id.editText_street);
            city = (EditText) findViewById(R.id.editText_city);
            state = (EditText) findViewById(R.id.editText_state);
            zipcode = (EditText) findViewById(R.id.editText_zipcode);


            mLatitude.setText("lat");
            mLongitude.setText("long");


//initiate firebase url
            myFirebaseRef = new Firebase("https://resplendent-heat-2353.firebaseio.com/Restaurants");

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    radio_rating = ((RadioButton) findViewById(rg_rating.getCheckedRadioButtonId())).getText().toString();
                    radio_dollar_range = ((RadioButton) findViewById(rg_dollar_range.getCheckedRadioButtonId())).getText().toString();

                    StringBuilder sb = new StringBuilder();
                    sb.append(street.getText());
                    sb.append(" ");
                    sb.append(city.getText());
                    sb.append(" ");
                    sb.append(state.getText());
                    sb.append(" ");
                    sb.append(zipcode.getText());
                    String address = sb.toString();

                    GeocodingLocation locationAddress = new GeocodingLocation();
                    locationAddress.getAddressFromLocation(address,
                            getApplicationContext(), new GeocoderHandler());


                    Latitude = mLatitude.getText().toString();
                    Longitude = mLongitude.getText().toString();

                    Map<String, String> post1 = new HashMap<String, String>();
                    post1.put("name", name.getText().toString());
                    post1.put("street",street.getText().toString());
                    post1.put("city",city.getText().toString());
                    post1.put("state",state.getText().toString());
                    post1.put("zipcode",zipcode.getText().toString());
                    post1.put("cuisine", cuisine.getText().toString());
                    post1.put("dollar_range", radio_dollar_range);
                    post1.put("waittime", waittime.getText().toString());
                    post1.put("rating", radio_rating);
                    post1.put("latitude",Latitude);
                    post1.put("longitude",Longitude);

                    myFirebaseRef.push().setValue(post1);
                    finish();

                }
            });

            btn_getLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    StringBuilder sb = new StringBuilder();
                    sb.append(street.getText());
                    sb.append(" ");
                    sb.append(city.getText());
                    sb.append(" ");
                    sb.append(state.getText());
                    sb.append(" ");
                    sb.append(zipcode.getText());
                    String address = sb.toString();

                    GeocodingLocation locationAddress = new GeocodingLocation();
                    locationAddress.getAddressFromLocation(address,
                            getApplicationContext(), new GeocoderHandler());


                }



            });

//            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//            });
        }
    }



    public class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String Lat, Long;

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    Latitude = bundle.getString("latitude");
                    Longitude = bundle.getString("longitude");

                    break;
                default:
                    Latitude = null;
                    Longitude = null;          }
            mLatitude.setText(Latitude);
            mLongitude.setText(Longitude);

        }
    }






    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
           // mLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
           // mLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
