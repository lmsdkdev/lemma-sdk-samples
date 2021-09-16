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
import lemma.lemmavideosdk.common.AppLog;

public class InBannerVideoActivity extends Activity {

    private static final String TAG = "InVideoBannerActivity";
    LMInBannerVideo inBannerVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_banner_video);

        final Button playBtn, showAdBtn;

        playBtn = findViewById(R.id.start);
        showAdBtn = findViewById(R.id.showAd);

        showAdBtn.setVisibility(View.INVISIBLE);

        inBannerVideo = new LMInBannerVideo(this,"169","14687",new LMInBannerVideo.LMAdSize(300,250),"http://ads.lemmatechnologies.com/lemma/servad");
        inBannerVideo.setListener(new InBannerVideoListener());

        FrameLayout mLinerAdContainer = findViewById(R.id.ad_linear_container);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        mLinerAdContainer.addView(inBannerVideo, params);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.showAd).setVisibility(View.INVISIBLE);
                inBannerVideo.loadAd();
            }
        });

        showAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inBannerVideo != null){
                    inBannerVideo.play();
                    showAdBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != inBannerVideo) {
            inBannerVideo.destroy();
            inBannerVideo = null;
            AppLog.d(TAG,"InBannerVideoActivity Destroy");
        }

    }

    class InBannerVideoListener implements LMInBannerVideo.InBannerVideoListener{

        @Override
        public void onAdReceived(LMInBannerVideo ad) {
            AppLog.d(TAG, "Ad Received Successfully  ");
            findViewById(R.id.showAd).setVisibility(View.VISIBLE);
        }

        @Override
        public void onAdFailed(LMInBannerVideo ad, Error error) {
            AppLog.e(TAG, "Ad failed with error - " + error.toString());

        }

        @Override
        public void onAdOpened(LMInBannerVideo ad) {
            AppLog.d(TAG, "Ad Opened");

        }

        @Override
        public void onAdClosed(LMInBannerVideo ad) {
            AppLog.d(TAG, "Ad Closed");
            inBannerVideo.destroy();
            inBannerVideo = null;

        }

        @Override
        public void onAdCompletion(LMInBannerVideo lmInBannerVideo) {
            AppLog.d(TAG,"Ad Completed");
        }
    }
}