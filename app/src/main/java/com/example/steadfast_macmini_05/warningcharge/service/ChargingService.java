package com.example.steadfast_macmini_05.warningcharge.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.steadfast_macmini_05.warningcharge.R;
import com.example.steadfast_macmini_05.warningcharge.utils.FlashUtil;
import com.example.steadfast_macmini_05.warningcharge.utils.NotificationUtil;

import static com.example.steadfast_macmini_05.warningcharge.service.ChargingReceiver.KEY_ACTION;

public class ChargingService extends Service {
    String action = "";
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getStringExtra(KEY_ACTION) != null) {
            action = intent.getStringExtra(KEY_ACTION);
            if (Intent.ACTION_POWER_DISCONNECTED.equals(action)) {
                FlashUtil.flickerFlash();
                NotificationUtil.showNotification(this);
                playSong();
                showScreenOVerlay(this);
            } else {
                stopSelf();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        FlashUtil.stopFlickerFlash();
        stopSong();
        System.exit(0);
        super.onDestroy();
    }

    //show screen overlay
    private void showScreenOVerlay(Context context) {
        WindowManager mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View mView = mInflater.inflate(R.layout.warning_screen, null);

        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.RGBA_8888);
        mView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        mView.findViewById(R.id.buttonOff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.setVisibility(View.GONE);
                FlashUtil.stopFlickerFlash();
                stopSong();
            }
        });
        mView.setVisibility(View.VISIBLE);
        mWindowManager.addView(mView, mLayoutParams);
    }

    private void playSong() {
        int resID = getResources().getIdentifier("maps", "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this, resID);
        mediaPlayer.start();
    }

    private void stopSong() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
