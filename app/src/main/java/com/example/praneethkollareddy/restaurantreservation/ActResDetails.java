package com.example.praneethkollareddy.restaurantreservation;

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

public class ActResDetails extends AppCompatActivity {

    Button btn_reserve;
    String res_name, res_dollar_range, res_waittime, res_rating, res_cuisine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_res_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent in = getIntent();
        res_name = in.getStringExtra("res_name");
        res_cuisine = in.getStringExtra("res_cuisine");
        res_dollar_range = in.getStringExtra("res_dollar_range");
        res_rating = in.getStringExtra("res_rating");
        res_waittime = in.getStringExtra("res_waittime");


        setTitle(res_name);

        TextView text_resName = (TextView) findViewById(R.id.text_RDaddress);
        TextView text_resCuisine = (TextView) findViewById(R.id.text_RDcuisine);
        TextView text_resDollarRange = (TextView) findViewById(R.id.text_RDdollarrange);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingbar_RD);
        Button btn_waittime = (Button) findViewById(R.id.btn_RDwaittime);

        text_resName.setText(res_name);
        text_resCuisine.setText(res_cuisine);
        text_resDollarRange.setText(res_dollar_range);
        ratingBar.setRating(Float.parseFloat(res_rating));
        btn_waittime.setText(res_waittime);


        btn_reserve = (Button) findViewById(R.id.btn_RDreserve);
        btn_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),MakeReservation.class);
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
}
