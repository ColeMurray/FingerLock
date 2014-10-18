package com.murraycole.fingerlock;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.murraycole.fingerlock.service.LockScreenService;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pass.Spass;


public class LockScreenActivity extends Activity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disableForTrueHomeScreen(this);
    }
    private static void disableForTrueHomeScreen(Context mContext){
        PackageManager pm = mContext.getPackageManager();
        ComponentName componentName = new ComponentName(mContext,LockScreenActivity.class);
        pm.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this,LockScreenService.class));


        setContentView(R.layout.activity_lock_screen);

        TextView unlockTextView = (TextView) findViewById(R.id.button);
        unlockTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                unlockScreen(view);
                return true;
            }
        });
       /* if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        } */
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
    public void onBackPressed(){
        return;
    }
    public void unlockScreen(View view){
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
