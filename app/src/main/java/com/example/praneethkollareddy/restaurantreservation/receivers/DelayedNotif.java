package com.example.praneethkollareddy.restaurantreservation.receivers;


import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.praneethkollareddy.restaurantreservation.R;

public class DelayedNotif extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        System.out.println("in da receiver");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.logo)
                .setContentTitle("Restaurant Reservation")
                .setContentText("Don't forget that you have a reservation today!");

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, mBuilder.build());
    }
}
