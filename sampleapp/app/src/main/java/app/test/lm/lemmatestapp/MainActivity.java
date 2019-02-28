package app.test.lm.lemmatestapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import lemma.lemmavideosdk.common.AppLog;
import lemma.lemmavideosdk.common.LemmaSDK;
import lemma.lemmavideosdk.vast.VastBuilder.Vast;
import lemma.lemmavideosdk.vast.listeners.AdManagerCallback;
import lemma.lemmavideosdk.vast.manager.VAdManager;


public class MainActivity extends AppCompatActivity implements AdManagerCallback {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    String TAG = "MainActivity";
    private VAdManager mVAdManager = null;
    private FrameLayout mLinerAdContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLinerAdContainer = findViewById(R.id.ad_container);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAd(<LEMMA_AD_UNIT_URL>);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void loadAd(final String adUnitId) {

        String listPermissionsNeeded[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this, listPermissionsNeeded[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, listPermissionsNeeded[1]) == PackageManager.PERMISSION_GRANTED) {
            loadAd();
        } else {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded, REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    private void loadAd() {

        LemmaSDK.init(MainActivity.this);
        mVAdManager = new VAdManager(MainActivity.this,
                MainActivity.this,
                <LEMMA_AD_UNIT_URL>);
        mVAdManager.init(mLinerAdContainer);
        mVAdManager.destroyAd();

    }

    @Override
    public void onAdError(VAdManager adManager, int errorType) {
        // TODO Auto-generated method stub
        AppLog.e(TAG, "Failed to start ad playing");
    }

    @Override
    public void adReceived(VAdManager vAdManager, Vast vast, boolean b) {

    }

    @Override
    public boolean shouldFireImpressions() {
        return true;
    }

    @Override
    public void onAdEvent(AdManagerCallback.AD_EVENT event) {
        // TODO Auto-generated method stub
        switch (event) {
            case AD_LOADED:
                mVAdManager.startAd();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        loadAd();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
