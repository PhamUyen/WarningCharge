package com.example.steadfast_macmini_05.warningcharge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class ChargingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, ChargingService.class);
        intentService.putExtra("action", intent.getAction());
        context.startService(intentService);
    }
}
