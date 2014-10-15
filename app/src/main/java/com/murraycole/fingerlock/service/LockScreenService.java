package com.murraycole.fingerlock.service;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.murraycole.fingerlock.receiver.LockStateReceiver;


public class LockScreenService extends Service {

    BroadcastReceiver mReceiver;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    @SuppressWarnings("deprecation")
    public void onCreate() {
        KeyguardManager.KeyguardLock key;
        KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        key = km.newKeyguardLock("IN");
        key.disableKeyguard();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);

        mReceiver = new LockStateReceiver();
        registerReceiver(mReceiver,filter);
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

}
