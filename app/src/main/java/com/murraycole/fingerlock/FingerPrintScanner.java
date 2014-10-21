package com.murraycole.fingerlock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cole on 10/15/2014.
 */
public class FingerPrintScanner {
    public static final String LOG_TAG = FingerPrintScanner.class.getSimpleName();
    private SpassFingerprint mSpassFingerprint;
    private Spass mSpass;
    private static Context mContext;
    private ListView mListView;
    private List<String> mItemArray = new ArrayList<String>();
    private ArrayAdapter<String> mListAdapter;
    private boolean onReadyIdentify = false;
    private boolean onReadyEnroll = false;
    boolean isFeatureEnabled = true;


    private SpassFingerprint.IdentifyListener listener = new SpassFingerprint.IdentifyListener() {
        public void onFinished(int eventStatus) {

            //  log("identify finished : reason=" + getEventStatusName(eventStatus));
            onReadyIdentify = false;
            int FingerprintIndex = 0;
            try {
                FingerprintIndex = mSpassFingerprint.getIdentifiedFingerprintIndex();
                Log.d(LOG_TAG, "Fingerprint is" + FingerprintIndex);
            } catch (IllegalStateException ise) {
                //log(ise.getMessage());
            }
            if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS) {
                Log.d(LOG_TAG, "SUCCESS");
                //      log("onFinished() : Identify authentification Success with FingerprintIndex " + FingerprintIndex);


                //Thumb
                if (FingerprintIndex == 1) {
                    //go to homescreen
                    ((Activity) mContext).finish();


                } else if (FingerprintIndex == 2) {
                    //  Intent view = new Intent (Intent.ACTION_DIAL);
                    // intent for all apps

                    Log.d(LOG_TAG, ((Activity) mContext).getLocalClassName());
                    //   mContext.startActivity(view);
                    ((Activity) mContext).finish();
                } else {
                    if (FingerprintIndex == 3) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"));

                        mContext.startActivity(browserIntent);
                        ((Activity) mContext).finish();
                    }


                }

            } else if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS) {
                //  log("onFinished() : Password authentification Success");
            } else {
                Log.d(LOG_TAG, "Authentification failed");
                //  log("onFinished() : Authentification Fail for identify");
            }
        }

        @Override
        public void onReady() {
            Log.d(LOG_TAG, "Ready for fingerprint");
            // log("identify state is ready");
        }

        @Override
        public void onStarted() {
            Log.d(LOG_TAG, "Finger has touched fingerprint sensor!");
            // log("User touched fingerprint sensor!");
        }
    };


    public FingerPrintScanner(Context context) {
        mContext = context;
    }

    public void startIdentify() {
        mSpassFingerprint = new SpassFingerprint(mContext);
        isFeatureEnabled = mSpassFingerprint.hasRegisteredFinger();
        if (isFeatureEnabled) {
            if (onReadyIdentify) {
                mSpassFingerprint.startIdentifyWithDialog(mContext, listener, false);
            }
        }
    }


}
