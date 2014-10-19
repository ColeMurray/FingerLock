package com.murraycole.fingerlock;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.murraycole.fingerlock.service.LockScreenService;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pass.Spass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class LockScreenActivity extends Activity {
    public static String LOG_TAG = LockScreenActivity.class.getSimpleName();
    Timer timer = new Timer();
    TextView clock;
    TextView dateTime;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Time time = new Time();
            time.setToNow();
            Log.d(LOG_TAG,time.toString());

            String timeString = time.toString();
            String changeTime = new Parser(timeString).parseTime();

            String currentTime = time.format("%I:%M");
            String currentDate = time.format("%a, %h %e");

            Log.d(LOG_TAG,currentTime);
            clock.setText(currentTime);
            dateTime.setText(currentDate);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disableForTrueHomeScreen(this);
    }

    private static void disableForTrueHomeScreen(Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        ComponentName componentName = new ComponentName(mContext, LockScreenActivity.class);
        pm.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, LockScreenService.class));


        setContentView(R.layout.activity_lock_screen);
        clock = (TextView) findViewById(R.id.clockTextView);
        dateTime = (TextView) findViewById(R.id.dateTextView);

        TextView unlockTextView = (TextView) findViewById(R.id.button);
        unlockTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                unlockScreen(view);
                return true;
            }
        });







        int initialDelay = 1000; //first update in miliseconds
        int period = 5000;      //nexts updates in miliseconds

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Message msg = new Message();
                mHandler.sendMessage(msg);
            }
        };
        timer.scheduleAtFixedRate(task, initialDelay, period);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lock_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public void unlockScreen(View view) {
        FingerPrintScanner fingerPrintScanner = new FingerPrintScanner(this);

        Spass mSpass = new Spass();
        try {
            mSpass.initialize(this);
            fingerPrintScanner.startIdentify();
        } catch (SsdkUnsupportedException e) {
            // Error
            Log.d("DA", "FingerPrint not supported");
            this.finish();
        }


        //this.finish();
    }








}
