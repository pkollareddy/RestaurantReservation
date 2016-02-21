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

import java.util.HashMap;
import java.util.Map;

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

        name = (EditText) findViewById(R.id.editText_name);
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

                Map<String, String> post1 = new HashMap<String, String>();
                post1.put("name", name.getText().toString());
                post1.put("address", address.getText().toString());
                post1.put("cuisine", cuisine.getText().toString());
                post1.put("dollar_range", radio_dollar_range);
                post1.put("waittime", waittime.getText().toString());
                post1.put("rating", radio_rating);

                myFirebaseRef.push().setValue(post1);
                finish();


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
