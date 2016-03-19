package com.example.praneethkollareddy.restaurantreservation.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.praneethkollareddy.restaurantreservation.R;
import com.example.praneethkollareddy.restaurantreservation.databeans.Reservation;
import com.example.praneethkollareddy.restaurantreservation.fragments.ReservationDatePickerFragment;
import com.example.praneethkollareddy.restaurantreservation.fragments.ReservationTimePickerFragment;
import com.example.praneethkollareddy.restaurantreservation.receivers.DelayedNotif;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookDialog;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.firebase.client.Firebase;

import java.util.Calendar;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;



public class MakeReservation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    TextView rdate,rtime,wait_time, rname;
    Firebase myFirebaseRef;
    String name,email,phone,pushID,rName,genTime;
    int party,year, month,day,hour,minute,code;
    static final int REQUEST_SEND_SMS = 1;
    private CountDownTimer countDownTimer;
    LinearLayout layout_timer;
    Button share;
    ShareDialog shareDialog;
    Switch facebook;
    int timer=0;
    Calendar cl;




    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.SEND_SMS
    };

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_SEND_SMS
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null, String.valueOf(code), null, null);
                } else {
                    System.out.println("Texts can't be sent.");
                }
                return;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Make Reservation");
        FacebookSdk.sdkInitialize(getApplicationContext());

        shareDialog = new ShareDialog(this);
        CallbackManager callbackManager = CallbackManager.Factory.create();
        shareDialog.registerCallback(callbackManager, new

                FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                    }
                });


        String time = new SimpleDateFormat("MMM-dd-yyyy HH:mm").format(new Date());
        genTime = String.valueOf(time);

        //map elements
        facebook = (Switch) findViewById((R.id.switch_facebook));
        rdate = (TextView) findViewById(R.id.date);
        rtime = (TextView) findViewById(R.id.time);
        wait_time = (TextView) findViewById(R.id.text_timer);
        rname = (TextView) findViewById(R.id.text_rname);

        Intent in = getIntent();
        final String res_name =  in.getStringExtra("res_name");
        final String res_time = in.getStringExtra("res_time");
        final String waittime = in.getStringExtra("waittime");


        DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        String now = df.format(new Date());

        rname.setText(res_name);

        cl = Calendar.getInstance();


        cl.setTime(new Date());


        day = cl.get(Calendar.DAY_OF_MONTH);
        month = cl.get(Calendar.MONTH)+1;
        year = cl.get(Calendar.YEAR);


        rName = rname.getText().toString();
        if(res_time!=null){
            rdate.setText(now);
            rtime.setText(res_time);

        }

        if(waittime!=null) {
            rdate.setText(now);
            timer = Integer.parseInt(waittime);
            cl.set(Calendar.MINUTE, cl.get(Calendar.MINUTE) + timer);

        }

        hour = cl.get(Calendar.HOUR_OF_DAY);
        minute = cl.get(Calendar.MINUTE);
        rtime.setText(hour + ":" + minute);
        rdate.setText(month + "-" + day + "-" + year);



        layout_timer = (LinearLayout) findViewById(R.id.layout_timer);
        layout_timer.setVisibility(View.GONE);

        if(timer!=0) {

            layout_timer.setVisibility(View.VISIBLE);
            long startTime = timer * 60 * 1000;
            countDownTimer = new CountDownTimer(startTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    wait_time.setText("" + String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }

                @Override
                public void onFinish() {

                    wait_time.setText("Book now!!");
                }
            }.start();
        }


        Button pickTime = (Button) findViewById(R.id.pickTime);
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        Button pickDate = (Button) findViewById(R.id.pickDate);
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        Button sendCode = (Button) findViewById(R.id.sendCode);
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameField = (EditText) findViewById(R.id.enterName);
                name = nameField.getText().toString();

                EditText emailField = (EditText) findViewById(R.id.enterEmail);
                email = emailField.getText().toString();

                EditText phoneField = (EditText) findViewById(R.id.enterPhone);
                phone = phoneField.getText().toString();

                EditText partyField = (EditText) findViewById(R.id.enterParty);
                party = Integer.parseInt(partyField.getText().toString());

                if (name != null && email != null && phone != null && party != 0) {
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(getApplicationContext(), "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                    } else if (!android.util.Patterns.PHONE.matcher(phone).matches()) {
                        Toast.makeText(getApplicationContext(), "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                    } else if (party > 10) {
                        Toast.makeText(getApplicationContext(), "We can not take reservations for over 10 people. Please call the restaurant.", Toast.LENGTH_LONG).show();
                    } else {
                        verifyStoragePermissions(MakeReservation.this);
                        SmsManager smsManager = SmsManager.getDefault();
                        code = (int) (Math.random() * 100001) + 1;
                        System.out.println("This is the verification code: " + code);
                        smsManager.sendTextMessage(phone, null, String.valueOf(code), null, null);
                        Toast.makeText(getApplicationContext(), "Verification code has been sent.", Toast.LENGTH_SHORT).show();
                        nameField.setKeyListener(null);
                        emailField.setKeyListener(null);
                        phoneField.setKeyListener(null);
                        partyField.setKeyListener(null);
                        Button saveButton = (Button) findViewById(R.id.saveButton);
                        saveButton.setEnabled(true);
                        EditText enterCode = (EditText) findViewById(R.id.enterCode);
                        enterCode.setVisibility(View.VISIBLE);
                        enterCode.setEnabled(true);
                    }
                }
            }
        });

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText enterCode = (EditText) findViewById(R.id.enterCode);
                if (Integer.parseInt(enterCode.getText().toString()) == code) {
                    Firebase.setAndroidContext(MakeReservation.this);
                    myFirebaseRef = new Firebase("https://resplendent-heat-2353.firebaseio.com/Reservations");
                    Firebase resRef = myFirebaseRef.push();
                    Reservation myRes = new Reservation(name, email, phone, party, year, month, day, hour, minute, rName, genTime);
                    resRef.setValue(myRes);
                    pushID = resRef.getKey();
                    Toast.makeText(getApplicationContext(), "Your reservation has been confirmed.", Toast.LENGTH_SHORT).show();
                }

                int id = 12345;

                Notification.Builder notification = new Notification.Builder(MakeReservation.this)
                        .setContentTitle("ReRe")
                        .setContentText("Reservation at " + res_name + " confirmed")
                        .setSmallIcon(R.drawable.logo);

                Intent notificationIntent = new Intent(getApplicationContext(), ActMyReservations.class);

                PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                notification.setContentIntent(contentIntent);
                notification.setAutoCancel(true);
                notification.setLights(Color.BLUE, 500, 500);
                long[] pattern = {500,500,500,500,500,500,500,500,500};
                notification.setVibrate(pattern);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notification.setSound(alarmSound);

                NotificationManager notificationManager = (NotificationManager) getSystemService(
                        Context.NOTIFICATION_SERVICE);
                notificationManager.notify(id, notification.build());

                Intent myIntent = new Intent(MakeReservation.this, DelayedNotif.class);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MakeReservation.this, 0, myIntent, 0);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month-1);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.HOUR_OF_DAY, hour - 2);
                calendar.set(Calendar.MINUTE, minute);

                System.out.println(calendar.getTime());

                System.out.println("sound the alarm");
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                System.out.println("alarm sounded");


                if(facebook.isChecked())
                {
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle("Yay I got my table at " +res_name)
                                .setContentDescription("Yolo people get your table reserved from this amazing new app")

                                .setContentUrl(Uri.parse("http://www.yelp.com/san-jose"))
                                .build();

                        shareDialog.show(linkContent);
                    }

                }
                finish();



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


    public void showTimePickerDialog(View v) {
        FragmentManager fm = this.getFragmentManager();
        DialogFragment newFragment = new ReservationTimePickerFragment();
        newFragment.show(fm, "timePicker");
    }

    public void showDatePickerDialog(View v) {
        FragmentManager fm = this.getFragmentManager();
        DialogFragment newFragment = new ReservationDatePickerFragment();
        newFragment.show(fm, "datePicker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minutes) {
        this.hour = hour;
        this.minute = minutes;
        String hr,min;
        if(hour<10)hr = "0" + String.valueOf(hour);
        else hr = String.valueOf(hour);

        if(minutes<10)min = "0" + String.valueOf(minutes);
        else min = String.valueOf(minutes);

        rtime.setText(hr + ":" + min);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        rdate.setText(month+1 + "-" + day + "-" + year);
    }


}
