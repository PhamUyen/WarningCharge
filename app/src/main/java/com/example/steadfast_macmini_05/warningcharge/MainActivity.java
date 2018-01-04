package com.example.steadfast_macmini_05.warningcharge;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1){
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.FLASHLIGHT)
                    != PackageManager.PERMISSION_GRANTED) {

                // Ask for the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.FLASHLIGHT},
                        0);

            } else {
                // Start creating the user interface
                registerReceiver();
            }
        }
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(new ChargingReceiver(), filter);
    }
}
