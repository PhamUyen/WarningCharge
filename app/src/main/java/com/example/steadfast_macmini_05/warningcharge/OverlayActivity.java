package com.example.steadfast_macmini_05.warningcharge;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.steadfast_macmini_05.warningcharge.utils.NotificationUtil;

/**
 * Created by steadfast-macmini-05 on 1/12/18.
 */

public class OverlayActivity extends Activity {
    public static  boolean isWarning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warning_screen);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Button close =findViewById(R.id.buttonOff);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWarning = false;
                System.exit(0);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Toast.makeText(this, ""+isWarning, Toast.LENGTH_SHORT).show();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                return isWarning;
            case KeyEvent.KEYCODE_VOLUME_UP:
                return isWarning;
            default:
                return true;
        }
    }
}
