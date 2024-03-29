package app.test.lm.lemmatestapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


import lemma.lemmavideosdk.common.AppLog;
import lemma.lemmavideosdk.vast.listeners.AdManagerCallback;
import lemma.lemmavideosdk.vast.manager.LMAdRequest;
import lemma.lemmavideosdk.vast.manager.LMVideoAdManager;


public class VideoActivity extends Activity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    String TAG = "VideoActivity";
    private LMVideoAdManager mVAdManager = null;
    private FrameLayout mLinerAdContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mLinerAdContainer = findViewById(R.id.ad_linear_container);

        Button play = findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVAdManager == null) {
                    checkPermissionAndLoadAd();
                }
            }
        });


        Button pause = findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVAdManager.pauseLoop();
            }
        });


        Button resume = findViewById(R.id.resume);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVAdManager.resumeLoop();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void checkPermissionAndLoadAd() {

        String[] listPermissionsNeeded = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this, listPermissionsNeeded[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, listPermissionsNeeded[1]) == PackageManager.PERMISSION_GRANTED) {
            loadAd();
        } else {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded, REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    private void loadAd() {

        LMAdRequest adRequest = new LMAdRequest("76", "1526");
        mVAdManager = new LMVideoAdManager(this, adRequest, new AdManagerCallback() {
            @Override
            public void onAdError(LMVideoAdManager adManager, Error error) {

            }

            @Override
            public void onAdEvent(AD_EVENT event) {

                AppLog.i(TAG, event.name());
                AppLog.i(TAG, "" + mVAdManager.getCurrentAdLoopStat());

                switch (event) {
                    case AD_LOADED:
                        mVAdManager.startAd();
                        break;
                    default:
                        break;
                }
            }
        });
        mVAdManager.init(mLinerAdContainer);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        loadAd();
    }
}
