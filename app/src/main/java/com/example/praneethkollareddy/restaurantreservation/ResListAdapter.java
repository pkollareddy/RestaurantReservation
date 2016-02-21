package com.example.praneethkollareddy.restaurantreservation;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import org.w3c.dom.Text;


/**
 * Created by INSPIRON on 16-Feb-16.
 */
public class ResListAdapter extends ResListFirebase<ResData> {

    String res_key;

    public ResListAdapter(Query ref, Activity activity, int layout) {
        super(ref, ResData.class, layout, activity);
        // TODO Auto-generated constructor stub


    }

    @Override
    protected void populateView(View v, ResData resData) {

        // Map a Chat object to an entry in our listview
        String waittime = resData.getWaittime();
        String dollarrange = resData.getDollar_range();
        String rating = resData.getRating();
        String cuisine = resData.getCuisine();
        String name = resData.getName();


        TextView res_name = (TextView) v.findViewById(R.id.text_res_name);
        TextView res_cuisine = (TextView) v.findViewById(R.id.text_res_cuisine);
        TextView res_dollarrange = (TextView) v.findViewById(R.id.text_dollar_range);
        Button btn_waittime = (Button) v.findViewById(R.id.btn_wait_time);
        RatingBar res_rating = (RatingBar)v.findViewById(R.id.res_rating);
        ImageView img_res = (ImageView) v.findViewById(R.id.img_res);

        res_name.setText(name);
        res_cuisine.setText(cuisine);
        res_dollarrange.setText(dollarrange);
        btn_waittime.setText(waittime);
        res_rating.setRating(Float.parseFloat(rating));

        int wt =  Integer.parseInt(waittime);
        if(wt<=15)btn_waittime.setBackgroundResource(R.drawable.wait_green);
        if(wt>15 && wt<=30)btn_waittime.setBackgroundResource(R.drawable.wait_yellow);
        if(wt>30 && wt<=45)btn_waittime.setBackgroundResource(R.drawable.wait_orange);
        if(wt>45)btn_waittime.setBackgroundResource(R.drawable.wait_red);

        img_res.setBackgroundResource(R.drawable.res_icon);


    }
    }



