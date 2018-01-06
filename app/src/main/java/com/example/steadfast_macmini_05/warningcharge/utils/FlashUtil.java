package com.example.steadfast_macmini_05.warningcharge.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v7.app.AlertDialog;

import com.example.steadfast_macmini_05.warningcharge.R;

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

    //turn on flash
    private static void flashOn() {
        isBlink = true;
        Camera.Parameters paramOn = cam.getParameters();
        paramOn.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(paramOn);
        cam.startPreview();
    }

    //turn off flash
    private static void flashOff() {
        isBlink = false;
        Camera.Parameters paramOff = cam.getParameters();
        paramOff.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        cam.setParameters(paramOff);
        cam.startPreview();
    }

    // show dialog when device not support flash
    public static void showAlertConfirmFlash(Context context) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(context.getString(R.string.content_alert));
        alertDialogBuilder.setPositiveButton(context.getString(R.string.txt_ok), new
                DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                System.exit(0);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //make flash flicker on/off
    public static void flickerFlash() {
        cam = Camera.open();
        mStatusChecker.run();
    }

    //stop flicker flash
    public static void stopFlickerFlash() {
        mHandler.removeCallbacks(mStatusChecker);
        if (cam != null) {
            cam.stopPreview();
            cam.release();
            cam = null;
        }
    }
}
