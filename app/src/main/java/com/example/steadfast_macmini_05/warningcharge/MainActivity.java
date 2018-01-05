package com.example.steadfast_macmini_05.warningcharge;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.steadfast_macmini_05.warningcharge.service.ChargingReceiver;
import com.example.steadfast_macmini_05.warningcharge.utils.FlashUtil;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity {
    public static int MY_PERMISSIONS_REQUEST_FLASH_LIGHT = 1000;
    ComponentName component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        component = new ComponentName(this, ChargingReceiver.class);
        if (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH)) {
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
        if (MY_PERMISSIONS_REQUEST_FLASH_LIGHT == requestCode) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // do nothing
            } else {
                Toast.makeText(this, "finish", Toast.LENGTH_SHORT).show();
                disableReceiver();
                finish();
            }
        }
    }

    private void disableReceiver() {
        Toast.makeText(this, "disable reicerver!!!", Toast.LENGTH_SHORT).show();
        getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                , PackageManager.DONT_KILL_APP);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.FLASHLIGHT,
                        CAMERA
                }, MY_PERMISSIONS_REQUEST_FLASH_LIGHT);

    }

    private boolean checkPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.FLASHLIGHT);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
    }
}
