package com.example.steadfast_macmini_05.warningcharge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class ChargingReceiver extends BroadcastReceiver {
    public static String KEY_ACTION = "action";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
        Intent intentService = new Intent(context, ChargingService.class);
        intentService.putExtra(KEY_ACTION, intent.getAction());
        context.startService(intentService);
    }
}
