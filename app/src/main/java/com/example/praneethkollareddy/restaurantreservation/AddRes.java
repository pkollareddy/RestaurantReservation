package com.example.praneethkollareddy.restaurantreservation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.firebase.client.Firebase;

public class AddRes extends AppCompatActivity {

    Firebase myFirebaseRef;
    EditText name;
    EditText address;
    EditText waittime;
    EditText cuisine;
    RadioGroup rg_rating;
    RadioGroup rg_dollar_range;
    String radio_rating;
    String radio_dollar_range;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_res);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);

        name = (EditText)findViewById(R.id.editText_name);
        address = (EditText)findViewById(R.id.editText_add);
        rg_rating = (RadioGroup)findViewById(R.id.rg_rating);
        rg_dollar_range = (RadioGroup)findViewById(R.id.rg_dollar_range);
        cuisine = (EditText)findViewById(R.id.editText_cuisine);
        waittime = (EditText)findViewById(R.id.editText_waittime);
        Button btn_add = (Button) findViewById(R.id.button_add);


        myFirebaseRef = new Firebase("https://resplendent-heat-2353.firebaseio.com/Restaurants");
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_rating = ((RadioButton) findViewById(rg_rating.getCheckedRadioButtonId())).getText().toString();
                radio_dollar_range = ((RadioButton) findViewById(rg_dollar_range.getCheckedRadioButtonId())).getText().toString();

                myFirebaseRef.child(name.getText().toString()).setValue(name.getText().toString());
                myFirebaseRef.child(name.getText().toString() + "/address").setValue(address.getText().toString());
                myFirebaseRef.child(name.getText().toString() + "/waitime").setValue(waittime.getText().toString());
                myFirebaseRef.child(name.getText().toString() + "/rating").setValue(radio_rating);
                myFirebaseRef.child(name.getText().toString() + "/cuisine").setValue(cuisine.getText().toString());
                myFirebaseRef.child(name.getText().toString() + "/dollar_range").setValue(radio_dollar_range);



                // myFirebaseRef.child("Restaurans/name").setValue(name.getText());



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

}
