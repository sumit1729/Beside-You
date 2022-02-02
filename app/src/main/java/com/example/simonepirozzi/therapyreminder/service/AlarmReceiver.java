package com.example.simonepirozzi.therapyreminder.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.simonepirozzi.therapyreminder.service.RingtonePlayingService;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getExtras().getString("extra");
        String n = intent.getExtras().getString("nomeAtt");
        String o = intent.getExtras().getString("orario");
        String d = intent.getExtras().getString("dose");



        Log.e("MyActivity", "In the receiver with " + state);

        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);
        serviceIntent.putExtra("extra", state);
        serviceIntent.putExtra("nomeAtt1", n);
        serviceIntent.putExtra("orario1", o);
        serviceIntent.putExtra("dose1", d);


        context.startService(serviceIntent);
    }
}