package app.test.lm.lemmatestapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import lemma.lemmavideosdk.bannervideo.LMInBannerVideo;

public class InBannerVideoActivity extends Activity {

    private static final String TAG = "InVideoBannerActivity";
    LMInBannerVideo inBannerVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_banner_video);

        inBannerVideo = new LMInBannerVideo(this,"169","14687",new LMInBannerVideo.LMAdSize(300,250),"http://ads.lemmatechnologies.com/lemma/servad");
        inBannerVideo.setListener(new InBannerVideoListener());

        FrameLayout mLinerAdContainer = findViewById(R.id.ad_linear_container);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        mLinerAdContainer.addView(inBannerVideo, params);
        Button playBtn = findViewById(R.id.start);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inBannerVideo.loadAd();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != inBannerVideo) {
            inBannerVideo.destroy();
            inBannerVideo = null;
        }

    }

    class InBannerVideoListener implements LMInBannerVideo.InBannerVideoListener{

        @Override
        public void onAdReceived(LMInBannerVideo ad) {
            Log.e(TAG, "Ad Received Successfully :- ");
        }

        @Override
        public void onAdFailed(LMInBannerVideo ad, Error error) {
            Log.e(TAG, "Ad failed with error - " + error.toString());

        }

        @Override
        public void onAdOpened(LMInBannerVideo ad) {
            Log.d(TAG, "Ad Opened");

        }

        @Override
        public void onAdClosed(LMInBannerVideo ad) {
            Log.d(TAG, "Ad Closed");
            inBannerVideo.destroy();
            inBannerVideo = null;

        }
    }
}