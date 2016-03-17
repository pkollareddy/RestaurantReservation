package com.example.praneethkollareddy.restaurantreservation.activities;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.app.DialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.praneethkollareddy.restaurantreservation.R;
import com.example.praneethkollareddy.restaurantreservation.databeans.ResData;
import com.example.praneethkollareddy.restaurantreservation.fragments.FragmentOffer2Details;
import com.example.praneethkollareddy.restaurantreservation.fragments.FragmentOfferDetails;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ActResDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Button btn_makeReservation, btn_orderFood;
    String res_name;
    GoogleMap resmap;
    TextView offer1, offer2, phone, price,cuisine,address;
    private GoogleApiClient mGoogleApiClient;
    ResData resData;
    LatLng resLoc = new LatLng(37,-121);
    ImageView image1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_res_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .enableAutoManage(this, this)
                    .build();
        }



        Intent in = getIntent();
        res_name = in.getStringExtra("res_name");

        setTitle(res_name);

        FetchResDetails(res_name);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.resmap);
        mapFragment.getMapAsync((this));
        resmap = mapFragment.getMap();

        resmap.getUiSettings().setScrollGesturesEnabled(false);
        resmap.getUiSettings().setZoomControlsEnabled(false);
        resmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent in = new Intent(getApplicationContext(), MapsActivity.class);
                in.putExtra("latitude", String.valueOf(latLng.latitude));
                in.putExtra("longitude", String.valueOf(latLng.longitude));
                in.putExtra("res_name", res_name);

                startActivity(in);
            }
        });


        //map elements
        image1 = (ImageView) findViewById(R.id.img_res);
        price = (TextView) findViewById(R.id.text_price);
        cuisine = (TextView) findViewById(R.id.text_cuisine);
        address = (TextView) findViewById(R.id.text_address);
        btn_makeReservation = (Button) findViewById(R.id.btn_makeReservation);
        btn_orderFood = (Button) findViewById(R.id.btn_orderFood);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.res_fiorillo);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

                File f = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "test.jpg");
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Uri path = Uri.fromFile(f);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(path, "image/*");
                startActivity(intent);
            }
        });

        offer1 = (TextView) findViewById(R.id.text_offer1);
        offer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOffer1Dialog();
            }
        });
        offer2 = (TextView) findViewById(R.id.text_offer2);
        offer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOffer2Dialog();
            }
        });

        phone = (TextView) findViewById(R.id.text_contact);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tel = phone.getText().toString();

                String uri = "tel:" + tel.trim();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });

        btn_makeReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(), MakeReservation.class);
                in.putExtra("res_name", res_name);
                startActivity(in);

            }
        });

        btn_orderFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ActOrder.class);
                in.putExtra("res_name", res_name);
                startActivity(in);

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
            // Handle the camera action
        } else if (id == R.id.nav_myAccount) {
            Intent in = new Intent(getApplicationContext(), ActAccount.class);
            startActivity(in);
        } else if (id == R.id.nav_myReservations) {
            Intent in = new Intent(getApplicationContext(), ActMyReservations.class);
            startActivity(in);

        } else if (id == R.id.nav_writeReview) {
            Intent in = new Intent(getApplicationContext(), ActMyReservations.class);
            startActivity(in);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void FetchResDetails(final String res_name) {
        Firebase myFirebaseRef = new Firebase("https://resplendent-heat-2353.firebaseio.com/Restaurants");


        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    resData = postSnapshot.getValue(ResData.class);
                    //Toast.makeText(getApplicationContext(), res_name, Toast.LENGTH_SHORT).show();
                    if (!resData.getName().isEmpty()) {

                        if (resData.getName().equals(res_name)) {
                            System.out.println(postSnapshot.getKey());

                            price.setText(resData.getDollar_range());
                            cuisine.setText(resData.getCuisine());

                            StringBuilder sb = new StringBuilder();
                            sb.append(resData.getStreet());
                            sb.append(", ");
                            sb.append(resData.getCity());
                            sb.append(", ");
                            sb.append(resData.getState());
                            sb.append(", ");
                            sb.append(resData.getZipcode());
                            String add =  sb.toString();

                            address.setText(add);
                            resLoc = new LatLng(Double.parseDouble(resData.getLatitude()),Double.parseDouble(resData.getLongitude()));
                        } else {

                        }
                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }



    private void showOffer1Dialog() {
        FragmentManager fm = this.getFragmentManager();

        DialogFragment newFragment = new FragmentOfferDetails();
        newFragment.show(fm, "offer1");
    }

    private void showOffer2Dialog() {
        FragmentManager fm = this.getFragmentManager();

        DialogFragment newFragment = new FragmentOffer2Details();
        newFragment.show(fm, "offer2");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        resmap.moveCamera(CameraUpdateFactory.newLatLngZoom(resLoc, 13));
        resmap.addMarker(new MarkerOptions().position(resLoc).title(res_name));

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
