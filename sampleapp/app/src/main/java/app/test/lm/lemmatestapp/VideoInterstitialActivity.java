package app.test.lm.lemmatestapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import lemma.lemmavideosdk.common.AppLog;
import lemma.lemmavideosdk.videointerstitial.LMVideoInterstitial;

public class VideoInterstitialActivity extends Activity {

    private static final String TAG = "InterstitialActivity";

    private LMVideoInterstitial interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_interstitial_activity);

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
                    Toast.makeText(VideoInterstitialActivity.this, "Please load Ad before showing it", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initAd() {
        if (null != interstitial) {
            interstitial.destroy();
            interstitial = null;
        }

        interstitial = new LMVideoInterstitial(this, "169", "14687", "http://ads.lemmatechnologies.com/lemma/servad");
        interstitial.setListener(new VideoInterstitialListener());
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
            interstitial = null;
            AppLog.d(TAG,"VideoInterstitialActivity Destroy");
        }

    }

    class VideoInterstitialListener implements LMVideoInterstitial.LMVideoInterstitialListener {

        @Override
        public void onAdReceived(LMVideoInterstitial ad) {
            AppLog.d(TAG,"Ad Received Successfully");
            findViewById(R.id.show_ad).setEnabled(true);
        }

        @Override
        public void onAdFailed(LMVideoInterstitial ad, Error error) {
            AppLog.e(TAG, "Ad failed with error - " + error.toString());
        }

        @Override
        public void onAdOpened(LMVideoInterstitial ad) {
            AppLog.d(TAG, "Ad Opened");
        }

        @Override
        public void onAdClosed(LMVideoInterstitial ad) {
            AppLog.d(TAG, "Ad Closed");
            interstitial.destroy();
            interstitial = null;
        }

        @Override
        public void onAdCompletion(LMVideoInterstitial lmVideoInterstitial) {
            AppLog.d(TAG,"Ad Completed");
        }

    }
}
