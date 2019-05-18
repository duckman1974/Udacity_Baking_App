package com.example.bakingapp.mediaplayer;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bakingapp.NetworkConnectivity;
import com.example.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BakingVideo extends AppCompatActivity {

    private static final String TAG = BakingVideo.class.getSimpleName();

    Context context;
    String video;
    String description;
    private SimpleExoPlayer player;
    long playbackPosition;
    int currentWindow;
    @BindView(R.id.player_view) SimpleExoPlayerView playerView;
    @BindView(R.id.description_video) TextView descripView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exoplayer_layout);
        ButterKnife.bind(this);

        context = getApplicationContext();

        Log.d(TAG, "MainActivity: Checking network connection");
        NetworkConnectivity net = new NetworkConnectivity(context);
        net.onNetworkActive();

        getIncomingIntent();
        initializePlayer();

    }

    private void initializePlayer() {

        int ort = getResources().getConfiguration().orientation;
        Log.d(TAG, "initializePlayer: orientation: " + ort);


        if (player == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            playerView.setPlayer(player);

            String userAgent = Util.getUserAgent(context, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource (Uri.parse(video), new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
             player.prepare(mediaSource, true, false);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    private void getIncomingIntent() {
        Log.d(TAG, "Getting Intent in BakingVideo");
        if (getIntent().hasExtra("videoUrl")) {
            video = getIntent().getStringExtra("videoUrl");
            description = getIntent().getStringExtra("description");
            descripView.setText(description);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Killing video");
        player.release();
        releasePlayer();
    }
}
