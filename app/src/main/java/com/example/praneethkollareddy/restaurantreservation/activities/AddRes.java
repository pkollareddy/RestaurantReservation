package com.example.praneethkollareddy.restaurantreservation.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.praneethkollareddy.restaurantreservation.GeocodingLocation;
import com.example.praneethkollareddy.restaurantreservation.R;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AddRes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int SELECT_PICTURE = 1;

    Firebase myFirebaseRef;
    EditText name, waittime, cuisine, street, city, state, zipcode;
    RadioGroup rg_rating, rg_dollar_range;
    String radio_rating, radio_dollar_range;
    TextView mLongitude, mLatitude, filename;
    Button btn_getLocation, btn_upload;
    public String Latitude, Longitude;
    String image;

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
            btn_upload = (Button) findViewById(R.id.btn_upload);
            filename = (TextView) findViewById(R.id.textview_filename);


//initiate firebase url
            myFirebaseRef = new Firebase("https://resplendent-heat-2353.firebaseio.com/Restaurants");

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    radio_rating = ((RadioButton) findViewById(rg_rating.getCheckedRadioButtonId())).getText().toString();
                    radio_dollar_range = ((RadioButton) findViewById(rg_dollar_range.getCheckedRadioButtonId())).getText().toString();

                    Map<String, String> post1 = new HashMap<String, String>();
                    post1.put("name", name.getText().toString());
                    post1.put("street", street.getText().toString());
                    post1.put("city", city.getText().toString());
                    post1.put("state", state.getText().toString());
                    post1.put("zipcode", zipcode.getText().toString());
                    post1.put("cuisine", cuisine.getText().toString());
                    post1.put("dollar_range", radio_dollar_range);
                    post1.put("waittime", waittime.getText().toString());
                    post1.put("rating", radio_rating);
                    post1.put("latitude", Latitude);
                    post1.put("longitude", Longitude);
                    post1.put("image", image);

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


            btn_upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                }
            });
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);


        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        } else if (id == R.id.nav_writeReview) {
            Intent in = new Intent(getApplicationContext(), ActMyReservations.class);
            startActivity(in);

        } else if (id == R.id.nav_myReservations) {
            Intent in = new Intent(getApplicationContext(), ActMyReservations.class);
            startActivity(in);

        } else if (id == R.id.nav_myAccount) {
            Intent in = new Intent(getApplicationContext(), ActAccount.class);
            startActivity(in);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        image = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        File f = new File(filePath);
                        filename.setText(f.getName());


                    }
                    cursor.close();
                }
                break;
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
                    Longitude = null;
            }
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
