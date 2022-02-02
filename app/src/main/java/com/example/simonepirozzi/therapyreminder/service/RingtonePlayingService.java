package com.example.simonepirozzi.therapyreminder.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.simonepirozzi.therapyreminder.AggiungiTerapieFragment;
import com.example.simonepirozzi.therapyreminder.R;

import java.util.Calendar;
import java.util.Random;

public class RingtonePlayingService extends Service {

    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startId;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyActivity", "In the Richard service");
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), AggiungiTerapieFragment.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "some_channel_id";
        CharSequence channelName = "Some Channel";
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(notificationChannel);

        String nomeAtt = intent.getExtras().getString("nomeAtt1");
        String orario = intent.getExtras().getString("orario1");
        String dose = intent.getExtras().getString("dose1");
        String dose1="";
        if(dose==null)  dose1=" ";
        else dose1="Dosaggio: "+dose;


        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle(nomeAtt+" ")
                .setContentText(dose1+" ")
                .setSubText(orario+" ")
                .setChannelId(channelId)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();


        String state = intent.getExtras().getString("extra");

        Log.e("what is going on here  ", state);

        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }


        if(!this.isRunning && startId == 1) {

                mMediaPlayer = MediaPlayer.create(this, R.raw.suoneria);

            mMediaPlayer.start();


            mNM.notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;

        }
        else if (!this.isRunning && startId == 0){
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.startId = 0;

        }

        else if (this.isRunning && startId == 1){
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startId = 0;

        }
        else {
            Log.e("if there is sound ", " and you want end");

            mMediaPlayer.stop();
            mMediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }


        Log.e("MyActivity", "In the service");

        return START_NOT_STICKY;

    }


    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }







}