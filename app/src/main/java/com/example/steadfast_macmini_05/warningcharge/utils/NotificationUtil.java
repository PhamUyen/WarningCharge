package com.example.steadfast_macmini_05.warningcharge.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.steadfast_macmini_05.warningcharge.R;


public class NotificationUtil {
    private static int ID_NOTIFICATION = 0;
    private static NotificationManager notificationmanager;
    public static void showNotification(Context context) {
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);

        //Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                // Set Icon
                .setSmallIcon(R.mipmap.ic_launcher)
                // Set Ticker Message
                .setContentTitle("Warning charge!")
                // Set Text
                .setContentText("You have just unplug charge.")
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Dismiss Notification
                .setAutoCancel(true);

        // Create Notification Manager
        notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationmanager.notify(ID_NOTIFICATION, builder.build());
    }
    public static void clearNotification(){
        notificationmanager.cancel(ID_NOTIFICATION);
    }
}
