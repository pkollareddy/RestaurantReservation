package com.example.praneethkollareddy.restaurantreservation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.praneethkollareddy.restaurantreservation.R;

public class MyCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        int [][] cartItems;
        Bundle myBundle = getIntent().getExtras();
        cartItems = (int[][]) myBundle.getSerializable("myCart");
        if (cartItems != null) {
            String nonVegItems = "You ordered these items: ";
            for (int i = 0; i < 6; i++) {
                if (cartItems[0][i] == 1) {
                    nonVegItems = nonVegItems + (i+1) + " ";
                }
            }
            TextView nonVeg = (TextView) findViewById(R.id.nonVegItems);
            nonVeg.setText(nonVegItems);

            String vegItems = "You ordered these items: ";
            for (int i = 0; i < 6; i++) {
                if (cartItems[1][i] == 1) {
                    vegItems = vegItems + (i+1) + " ";
                }
            }
            TextView Veg = (TextView) findViewById(R.id.vegItems);
            Veg.setText(vegItems);

            String drinks = "You ordered these items: ";
            for (int i = 0; i < 6; i++) {
                if (cartItems[2][i] == 1) {
                    drinks = drinks + (i+1) + " ";
                }
            }
            TextView drinksItems = (TextView) findViewById(R.id.drinksItems);
            drinksItems.setText(drinks);
        }
    }
}
