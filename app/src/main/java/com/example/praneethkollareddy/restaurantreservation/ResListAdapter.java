package com.example.praneethkollareddy.restaurantreservation;

import android.app.Activity;
import android.content.Context;
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
public class ResListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String[] waittime;
    private final String[] dollorrange;
    private final String[] rating;

    public ResListAdapter(Activity context, String[] itemname, Integer[] imgid, String[] waittime, String[] dollor_range, String[] res_rating) {
        super(context, R.layout.res_list, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.waittime = waittime;
        this.dollorrange = dollor_range;
        this.rating = res_rating;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.res_list, null, true);

        //Get values
        TextView txtTitle = (TextView) rowView.findViewById(R.id.text_res_name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img_res);
        TextView extratxt = (TextView) rowView.findViewById(R.id.text_res_cuisine);
        Button btn_waittime = (Button) rowView.findViewById(R.id.btn_wait_time);
        TextView dollor_range = (TextView) rowView.findViewById((R.id.text_dollar_range));
        RatingBar resRating= (RatingBar) rowView.findViewById(R.id.res_rating);

        //Set values
        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        extratxt.setText(itemname[position] + "'s cuisine.");
        btn_waittime.setText(waittime[position]);
        dollor_range.setText(dollorrange[position]);
        resRating.setRating(Float.parseFloat(rating[position]));

        int wt =  Integer.parseInt(waittime[position]);
        if(wt<=15)btn_waittime.setBackgroundResource(R.drawable.wait_green);
        if(wt>15 && wt<=30)btn_waittime.setBackgroundResource(R.drawable.wait_yellow);
        if(wt>30 && wt<=45)btn_waittime.setBackgroundResource(R.drawable.wait_orange);
        if(wt>45)btn_waittime.setBackgroundResource(R.drawable.wait_red);






        return rowView;

    };
}
