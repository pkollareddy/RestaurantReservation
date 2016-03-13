package com.example.praneethkollareddy.restaurantreservation.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.praneethkollareddy.restaurantreservation.R;
import com.example.praneethkollareddy.restaurantreservation.Reservation;
import com.example.praneethkollareddy.restaurantreservation.ReservationDatePickerFragment;
import com.example.praneethkollareddy.restaurantreservation.ReservationTimePickerFragment;
import com.firebase.client.Firebase;

public class MakeReservation extends FragmentActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    TextView rdate,rtime;
    Firebase myFirebaseRef;
    String name,email,phone,pushID;
    int party,year, month,day,hour,minute,code;
    static final int REQUEST_SEND_SMS = 1;
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
        //setSupportActionBar(toolbar);


        TextView rname = (TextView) findViewById(R.id.text_rname);
        rdate = (TextView) findViewById(R.id.date);
        rtime = (TextView) findViewById(R.id.time);




        rname.setText(Main_Activity.rName);
        setTitle("Make Reservation");





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

                if(name != null && email !=null && phone != null && party != 0) {
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(getApplicationContext(), "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                    }else if(!android.util.Patterns.PHONE.matcher(phone).matches()) {
                        Toast.makeText(getApplicationContext(), "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                    }else if(party > 10) {
                        Toast.makeText(getApplicationContext(), "We can not take reservations for over 10 people. Please call the restaurant.", Toast.LENGTH_LONG).show();
                    } else {
                        verifyStoragePermissions(MakeReservation.this);
                        SmsManager smsManager = SmsManager.getDefault();
                        code = (int)(Math.random()*100001) + 1;
                        System.out.println("This is the verification code: " + code);
                        smsManager.sendTextMessage(phone, null, String.valueOf(code), null, null);
                        Toast.makeText(getApplicationContext(), "Verification code has been sent.", Toast.LENGTH_SHORT).show();
                        nameField.setKeyListener(null);
                        emailField.setKeyListener(null);
                        phoneField.setKeyListener(null);
                        partyField.setKeyListener(null);
                        Button saveButton = (Button) findViewById(R.id.saveButton);
                        saveButton.setEnabled(true);
                        TextView codePrompt = (TextView) findViewById(R.id.codePrompt);
                        codePrompt.setVisibility(View.VISIBLE);
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
                    Reservation myRes = new Reservation(name, email, phone, party, year, month, day, hour, minute);
                    resRef.setValue(myRes);
                    pushID = resRef.getKey();
                    Toast.makeText(getApplicationContext(), "Your reservation has been confirmed.", Toast.LENGTH_SHORT).show();
                }


                int id = 12345;

                Notification notification = new Notification.Builder(MakeReservation.this)
                        .setContentTitle("Restaurant Reservation")
                        .setContentText("Your reservation has been made.")
                        .setSmallIcon(android.R.drawable.ic_menu_day)
                        .build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(
                        Context.NOTIFICATION_SERVICE);
                notificationManager.notify(id, notification);
                finish();
            }
        });

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
        rtime.setText(hour +":"+ minutes);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        rdate.setText(month +"-"+ day +"-"+ year);
    }

}
