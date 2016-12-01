package com.example.benvc.films;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class TrailerActivity extends AppCompatActivity {
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        final String url = getIntent().getStringExtra("VIDEO_URL");
        // Find your VideoView in your video_main.xml layout
        videoView = (VideoView) findViewById(R.id.videoView);
        String path1="";

        Uri uri=Uri.parse(url);

        VideoView video=(VideoView)findViewById(R.id.videoView);
        video.setVideoURI(uri);
        video.start();
//        try {
//            // Start the MediaController
//            MediaController mediacontroller = new MediaController(
//                    TrailerActivity.this);
//            mediacontroller.setAnchorView(videoView);
//            // Get the URL from String VideoURL
//            Uri video = Uri.parse(url);
//            videoView.setMediaController(mediacontroller);
//            videoView.setVideoURI(video);
//
//        } catch (Exception e) {
//            Log.e("Error", e.getMessage());
//            e.printStackTrace();
//        }
//
//        videoView.requestFocus();
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            // Close the progress bar and play the video
//            public void onPrepared(MediaPlayer mp) {
//                //pDialog.dismiss();
//                videoView.start();
//            }
//        });
    }
}
