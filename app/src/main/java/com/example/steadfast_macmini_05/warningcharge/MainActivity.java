package com.example.steadfast_macmini_05.warningcharge;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static int MY_PERMISSIONS_REQUEST_FLASH_LIGHT = 0;
    ComponentName component;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        component= new ComponentName(this, ChargingReceiver.class);
        PermissionUtil.requestPermissions(this,
                new String[]{Manifest.permission.FLASHLIGHT},
                MY_PERMISSIONS_REQUEST_FLASH_LIGHT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(MY_PERMISSIONS_REQUEST_FLASH_LIGHT == requestCode){
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do nothing
            } else {
                disableReceiver();
            }
        }
    }

    private void disableReceiver(){
        int status = getPackageManager().getComponentEnabledSetting(component);
        if(status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    , PackageManager.DONT_KILL_APP);
        }
    }
}
