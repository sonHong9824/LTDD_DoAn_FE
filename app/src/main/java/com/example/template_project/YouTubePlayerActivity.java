package com.example.template_project;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

public class YouTubePlayerActivity extends AppCompatActivity {

    private WebView youtubeWebView;
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bật chế độ xoay ngang tự động
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        hideSystemUI(); // Ẩn thanh trạng thái & điều hướng
        setContentView(R.layout.activity_youtube_player);

        youtubeWebView = findViewById(R.id.youtubeWebView);
        

        // Nhận VIDEO_ID từ Intent
        videoId = getIntent().getStringExtra("VIDEO_ID");

        // Cấu hình WebView
        WebSettings webSettings = youtubeWebView.getSettings();
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        youtubeWebView.setWebViewClient(new WebViewClient());

        youtubeWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        youtubeWebView.getSettings().setAllowFileAccessFromFileURLs(true);

        // Load video từ YouTube
        String videoUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=1&fs=1";
        youtubeWebView.loadUrl(videoUrl);

    }

    // Ẩn thanh trạng thái và thanh điều hướng
    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }
}
