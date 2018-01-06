package com.example.steadfast_macmini_05.warningcharge.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.steadfast_macmini_05.warningcharge.R;


public class NotificationUtil {
    private static int ID_NOTIFICATION = 2000;
    private static NotificationManager notificationmanager;
    public static void showNotification(Context context) {
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
        //Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.txt_title_noti))
                .setContentText(context.getString(R.string.txt_content_noti))
                .setContentIntent(pIntent)
                .setAutoCancel(true);

        // Create Notification Manager
        notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationmanager.notify(ID_NOTIFICATION, builder.build());
    }
    public static void clearNotification(){
        notificationmanager.cancel(ID_NOTIFICATION);
    }
}
