package com.example.steadfast_macmini_05.warningcharge.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


public class FlashUtil {
    private static Camera cam;
    private static boolean isBlink;
    private static Handler mHandler = new Handler();
    private static Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                if (isBlink) {
                    flashOff();
                } else {
                    flashOn();
                }

            } finally {
                int blinkDelay = 30;
                mHandler.postDelayed(mStatusChecker, blinkDelay);
            }
        }
    };

    private static void flashOn() {
        isBlink = true;
        Camera.Parameters paramOn = cam.getParameters();
        paramOn.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(paramOn);
        cam.startPreview();
    }

    private static void flashOff() {
        isBlink = false;
        Camera.Parameters paramOff = cam.getParameters();
        paramOff.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        cam.setParameters(paramOff);
        cam.startPreview();
    }

    public static void showAlertConfirmFlash(Context context) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Your device not support flash!!!!");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                System.exit(0);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void flickerFlash() {
        cam = Camera.open();
        mStatusChecker.run();
    }

    public static void stopFlickerFlash() {
        mHandler.removeCallbacks(mStatusChecker);
        if (cam != null) {
            cam.stopPreview();
            cam.release();
        }
    }
}
