package app.test.lm.lemmatestapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import lemma.lemmavideosdk.common.AppLog;
import lemma.lemmavideosdk.interstitial.LMInterstitial;

public class InterstitialActivity extends Activity {
    private static final String TAG = "InterstitialActivity";

    private LMInterstitial interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interstitial_activity);

        findViewById(R.id.load_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.show_ad).setEnabled(false);
                loadAd();

            }
        });

        findViewById(R.id.show_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != interstitial) {
                    interstitial.show();
                } else {
                    Toast.makeText(InterstitialActivity.this, "Please load Ad before showing it", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initAd() {
        if (null != interstitial) {
            interstitial.destroy();
            interstitial = null;
        }

        interstitial = new LMInterstitial(this, "169", "14688","http://ads.lemmatechnologies.com/lemma/servad");
        interstitial.setListener(new InterstitialListener());
    }

    private void loadAd() {
        initAd();
        interstitial.ShowAdCloseButton=true;
        interstitial.loadAd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != interstitial) {
            interstitial.destroy();
        }

    }

    class InterstitialListener implements LMInterstitial.LMInterstitialListener {

        @Override
        public void onAdReceived(LMInterstitial ad) {
            AppLog.d(TAG,"Ad Received Successfully");
            findViewById(R.id.show_ad).setEnabled(true);
        }

        @Override
        public void onAdFailed(LMInterstitial ad, Error error) {
            AppLog.e(TAG, "Ad failed with error - " + error.toString());
        }

        @Override
        public void onAdOpened(LMInterstitial ad) {
            AppLog.d(TAG, "Ad Opened");
        }

        @Override
        public void onAdClosed(LMInterstitial ad) {
            AppLog.d(TAG, "Ad Closed");
            interstitial = null;
        }

    }
}
