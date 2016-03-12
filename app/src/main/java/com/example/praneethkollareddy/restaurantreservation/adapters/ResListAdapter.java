package com.example.praneethkollareddy.restaurantreservation.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.praneethkollareddy.restaurantreservation.R;
import com.example.praneethkollareddy.restaurantreservation.ResData;
import com.example.praneethkollareddy.restaurantreservation.activities.Main_Activity;
import com.firebase.client.Query;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Time;
import java.util.Date;


/**
 * Created by INSPIRON on 16-Feb-16.
 */
public class ResListAdapter extends ResListFirebase<ResData> {

    String res_key;
    Location myLocation= new Location(LocationManager.NETWORK_PROVIDER), resLocation= new Location(LocationManager.NETWORK_PROVIDER);

    public ResListAdapter(Query ref, Activity activity, int layout) {
        super(ref, ResData.class, layout, activity);
        // TODO Auto-generated constructor stub


    }

    @Override
    protected void populateView(View v, ResData resData) {

        String myLat = Main_Activity.Latitude;
        String myLong = Main_Activity.Longitude;
        myLocation = Main_Activity.myLoc;
        float distance;
        int time;
        String restime1,restime2,restime3;
        Date now = new Date();
        now.getTime();
        int hrNow = now.getHours();
        int minNow = now.getMinutes();
        int hr1,hr2,hr3,min1,min2,min3,min;
        hr1=hr2=hr3=min1=min2=min3=0;
        min = minNow/15;


        if ((hrNow>21)|| (hrNow<12))
            hrNow=13;

        switch (min){
            case 0:
                hr1 = hrNow;
                hr2 = hrNow;
                hr3 = hrNow+1;
                min1 = 30;
                min2= 45;
                min3 =00;
            case 1:
                hr1 = hrNow;
                hr2 = hrNow+1;
                hr3 = hrNow+1;
                min1 = 45;
                min2= 00;
                min3 =15;
            case 2:
                hr1 = hrNow+1;
                hr2 = hrNow+1;
                hr3 = hrNow+1;
                min1 = 00;
                min2= 15;
                min3 =30;
            case 3:
                hr1 = hrNow+1;
                hr2 = hrNow+1;
                hr3 = hrNow+1;
                min1 = 15;
                min2= 30;
                min3 =45;
        }


        restime1 = hr1 + ":" + min1;
        restime2 = hr2 + ":" + min2;
        restime3 = hr3 + ":" + min3;


        Double resLat = Double.valueOf(resData.getLatitude());
        Double resLng = Double.valueOf((resData.getLongitude()));

        resLocation.setLatitude(resLat);
        resLocation.setLongitude(resLng);
        distance = myLocation.distanceTo(resLocation);
        distance=distance/1000;

        distance = (float) (distance/ 1.6);
        distance= (float) ((double)Math.round(distance * 10d) / 10d);

        time = (int) (distance*1.2);

        // Map a Chat object to an entry in our listview
        String waittime = resData.getWaittime();
        String dollarrange = resData.getDollar_range();
        String rating = resData.getRating();
        String cuisine = resData.getCuisine();
        String name = resData.getName();
        String image = resData.getImage();

        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        TextView res_dis = (TextView) v.findViewById(R.id.textview_distance);
        TextView res_name = (TextView) v.findViewById(R.id.text_res_name);
        TextView res_cuisine = (TextView) v.findViewById(R.id.text_res_cuisine);
        TextView res_dollarrange = (TextView) v.findViewById(R.id.text_dollar_range);
        Button btn_waittime = (Button) v.findViewById(R.id.btn_wait_time);
        RatingBar res_rating = (RatingBar)v.findViewById(R.id.res_rating);
        ImageView img_res = (ImageView) v.findViewById(R.id.img_res);
        Button btn_restime1 = (Button) v.findViewById(R.id.res_time1);
        Button btn_restime2 = (Button) v.findViewById(R.id.res_time2);
        Button btn_restime3 = (Button) v.findViewById(R.id.res_time3);



        res_name.setText(name);
        res_cuisine.setText(cuisine);
        res_dollarrange.setText(dollarrange);
        btn_waittime.setText(waittime);
        res_rating.setRating(Float.parseFloat(rating));
        res_dis.setText(String.valueOf(distance) + " mi, " + time + " mins");
        btn_restime1.setText(restime1);
        btn_restime2.setText(restime2);
        btn_restime3.setText(restime3);




        //mark tags of each restaurant in the listview
        Main_Activity.googleMap.addMarker(new MarkerOptions().position(new LatLng(resLat, resLng)).title(name));


        int wt =  Integer.parseInt(waittime);
        if(wt<=15)btn_waittime.setBackgroundResource(R.drawable.wait_green);
        if(wt>15 && wt<=30)btn_waittime.setBackgroundResource(R.drawable.wait_yellow);
        if(wt>30 && wt<=45)btn_waittime.setBackgroundResource(R.drawable.wait_orange);
        if(wt>45)btn_waittime.setBackgroundResource(R.drawable.wait_red);

        img_res.setImageBitmap(decodedByte);


    }
    }



