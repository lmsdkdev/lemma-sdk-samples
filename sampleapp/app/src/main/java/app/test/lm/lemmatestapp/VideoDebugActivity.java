package app.test.lm.lemmatestapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import lemma.lemmavideosdk.common.AppLog;
import lemma.lemmavideosdk.vast.listeners.AdManagerCallback;
import lemma.lemmavideosdk.vast.manager.LMAdRequest;
import lemma.lemmavideosdk.vast.manager.LMVideoAdManager;

public class VideoDebugActivity extends Activity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    String TAG = "VideoActivity";
    private LMVideoAdManager mVAdManager = null;
    private FrameLayout mLinerAdContainer;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.debug_activity);
        mLinerAdContainer = findViewById(R.id.ad_linear_container);
        textView = findViewById(R.id.tv);
        textView.setMovementMethod(new ScrollingMovementMethod());

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        Button playBtn = findViewById(R.id.start);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVAdManager == null) {
                    checkPermissionAndLoadAd();
                }
            }
        });

        Button stopBtn = findViewById(R.id.stop);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVAdManager != null) {
                    mVAdManager.destroy();
                    mVAdManager = null;
                } else {
                    Toast.makeText(VideoDebugActivity.this, "Ad loading is not started yet, Please press START AD", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button pause = findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVAdManager != null) {
                    mVAdManager.pauseLoop();
                } else {
                    Toast.makeText(VideoDebugActivity.this, "Ad loading is not started yet, Please press START AD", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button resume = findViewById(R.id.resume);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVAdManager != null) {
                    mVAdManager.resumeLoop();
                } else {
                    Toast.makeText(VideoDebugActivity.this, "Ad loading is not started yet, Please press START AD", Toast.LENGTH_LONG).show();
                }
            }
        });

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
                Toast.makeText(VideoDebugActivity.this, "Error " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdEvent(AD_EVENT event) {

                AppLog.i(TAG, "" + mVAdManager.getCurrentAdLoopStat());
                log(event.name());
                log("" + mVAdManager.getCurrentAdLoopStat());

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

    private void log(String log) {
        textView.append(log + "\n");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        loadAd();
    }

}
