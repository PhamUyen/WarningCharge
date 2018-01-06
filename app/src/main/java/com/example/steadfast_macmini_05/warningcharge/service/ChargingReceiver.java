package com.example.steadfast_macmini_05.warningcharge.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class ChargingReceiver extends BroadcastReceiver {
    public static String KEY_ACTION = "action";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, ChargingService.class);
        intentService.putExtra(KEY_ACTION, intent.getAction());
        context.startService(intentService);
    }
}
