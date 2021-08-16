package app.test.lm.lemmatestapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import lemma.lemmavideosdk.banner.LMBannerView;

public class BannerActivity extends Activity {

    LMBannerView bannerView;
    private static final String TAG = "BannerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.banner_activity);

        bannerView = new LMBannerView(this, "169", "14688", new LMBannerView.LMAdSize(320,50),"http://ads.lemmatechnologies.com/lemma/servad");

        FrameLayout mLinerAdContainer = findViewById(R.id.ad_linear_container);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        mLinerAdContainer.addView(bannerView, params);


        bannerView.setBannerViewListener(new LMBannerView.BannerViewListener() {
            @Override
            public void onAdReceived() {

            }

            @Override
            public void onAdError(Error error) {
                Log.e(TAG, "Ad failed with error - " + error.toString());
            }
        });

        Button playBtn = findViewById(R.id.start);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bannerView.loadAd();
            }
        });

    }

}
