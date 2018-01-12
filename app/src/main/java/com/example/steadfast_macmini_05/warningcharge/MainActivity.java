package com.example.steadfast_macmini_05.warningcharge;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.steadfast_macmini_05.warningcharge.service.ChargingReceiver;
import com.example.steadfast_macmini_05.warningcharge.utils.FlashUtil;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity {
    public static final int CAMERA_PERMISSIONS_REQ_CODE = 1000;
    private ComponentName component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        component = new ComponentName(this, ChargingReceiver.class);
        //check available flash on device
        if (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH)) {
            //check version of device and request permission if needed
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 && !checkPermission()) {
                requestPermission();
            }
        } else {
            FlashUtil.showAlertConfirmFlash(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //if user allow permission: enable receiver
        // disable receiver and finish app if not allow
        if (CAMERA_PERMISSIONS_REQ_CODE == requestCode) {
            if (grantResults.length > 0
                    && (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                enableReceiver();
            } else {
                disableReceiver();
                finish();
            }
        }
    }

    //disable broadcast receiver
    private void disableReceiver() {
        getPackageManager().setComponentEnabledSetting(component, PackageManager
                        .COMPONENT_ENABLED_STATE_DISABLED
                , PackageManager.DONT_KILL_APP);
    }

    //enable broadcast receiver
    private void enableReceiver() {
        Toast.makeText(this, "enable reicerver!!!", Toast.LENGTH_SHORT).show();
        getPackageManager().setComponentEnabledSetting(component, PackageManager
                        .COMPONENT_ENABLED_STATE_ENABLED
                , PackageManager.DONT_KILL_APP);
    }

    //send request permission for camera(flash)
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.FLASHLIGHT,
                        CAMERA
                }, CAMERA_PERMISSIONS_REQ_CODE);

    }

    private boolean checkPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.FLASHLIGHT);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(),
                CAMERA);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
    }
}
