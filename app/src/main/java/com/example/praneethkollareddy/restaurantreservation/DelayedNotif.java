package com.example.praneethkollareddy.restaurantreservation;


import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.praneethkollareddy.restaurantreservation.activities.ActMyReservations;
import com.example.praneethkollareddy.restaurantreservation.activities.Main_Activity;
import com.example.praneethkollareddy.restaurantreservation.activities.MapsActivity;

public class DelayedNotif extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        System.out.println("in da receiver");


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.logo)
                .setContentTitle("ReRe")
                .setContentText("Don't forget that you have a reservation today!");

        Intent notificationIntent = new Intent(context.getApplicationContext(), MapsActivity.class);
        notificationIntent.putExtra("latitude", Main_Activity.Latitude);
        notificationIntent.putExtra("longitude", Main_Activity.Longitude);


        PendingIntent contentIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);
        mBuilder.setLights(Color.BLUE, 500, 500);
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        mBuilder.setVibrate(pattern);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, mBuilder.build());
    }
}
