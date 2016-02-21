package com.example.praneethkollareddy.restaurantreservation;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.firebase.client.Firebase;

public class MakeReservation extends FragmentActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    Firebase myFirebaseRef;
    String name;
    String email;
    String phone;
    int party;
    int year;
    int month;
    int day;
    int hour;
    int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

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

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
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
                    Firebase.setAndroidContext(MakeReservation.this);
                    myFirebaseRef = new Firebase("https://resplendent-heat-2353.firebaseio.com/Reservations");
                    Firebase resRef = myFirebaseRef.child(name + "'s Reservation");
                    Reservation myRes = new Reservation(name, email, phone, party, year, month, day, hour, minute);
                    resRef.setValue(myRes);
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
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

}
