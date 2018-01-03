package com.example.steadfast_macmini_05.warningcharge;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Camera cam;
    int blinkDelayOn = 20; //Delay in ms
    int blinkDelayOff = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cam = Camera.open();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera.Parameters paramOn = cam.getParameters();
                paramOn.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                Camera.Parameters paramOff = cam.getParameters();
                paramOff.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                while(true) {
                    try {
                        if (blinkDelayOn > 0) {
                            cam.setParameters(paramOn);
                            cam.startPreview();
                            Thread.sleep(Math.round(blinkDelayOn));
                        }

                        if (blinkDelayOff > 0) {
                            cam.setParameters(paramOff);
                            cam.startPreview();
                            Thread.sleep(Math.round(blinkDelayOff));
                        }
                    }
                    catch(InterruptedException ex)
                    {
                        ex.printStackTrace();
                    }
                    catch(RuntimeException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });

        findViewById(R.id.buttonOff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cam.stopPreview();
                cam.release();
            }
        });
    }
}
