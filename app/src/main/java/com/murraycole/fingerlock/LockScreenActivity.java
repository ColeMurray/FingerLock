package com.murraycole.fingerlock;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

import com.murraycole.fingerlock.service.LockScreenService;

import org.w3c.dom.Text;


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
        fingerPrintScanner.startIdentify();

        //this.finish();
    }


}
