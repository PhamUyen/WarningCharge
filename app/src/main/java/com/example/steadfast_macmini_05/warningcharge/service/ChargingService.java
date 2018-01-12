package com.example.steadfast_macmini_05.warningcharge.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.steadfast_macmini_05.warningcharge.OverlayActivity;
import com.example.steadfast_macmini_05.warningcharge.utils.FlashUtil;
import com.example.steadfast_macmini_05.warningcharge.utils.NotificationUtil;

import static com.example.steadfast_macmini_05.warningcharge.OverlayActivity.isWarning;
import static com.example.steadfast_macmini_05.warningcharge.service.ChargingReceiver.KEY_ACTION;

public class ChargingService extends Service{
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
                showOverlayActivity(this);
                isWarning = true;
            } else {
                stopSelf();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        isWarning = false;
        FlashUtil.stopFlickerFlash();
        stopSong();
        NotificationUtil.clearNotification();
        System.exit(0);
        super.onDestroy();
    }

    //play song from raw folder
    private void playSong() {
        int resID = getResources().getIdentifier("maps", "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this, resID);
        mediaPlayer.start();
    }

    //stop song
    private void stopSong() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer= null;
        }
    }
    private void showOverlayActivity(Context context) {
        Intent intent = new Intent(context, OverlayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
