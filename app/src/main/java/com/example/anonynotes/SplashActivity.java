package com.example.anonynotes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private TextView notificationBubble;
    private Handler handler = new Handler();
    private Runnable runnable;
    private final long SPLASH_DISPLAY_LENGTH = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        notificationBubble = findViewById(R.id.notification_bubble);

        // Delay check for 3 seconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkInternetConnection();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void checkInternetConnection() {
        if (isConnected()) {
            startSign_Up();
        } else {
            showNotificationBubble("Not connected to the internet");
            // Recheck internet connection every 3 seconds
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (isConnected()) {
                        hideNotificationBubble();
                        startSign_Up();
                    } else {
                        handler.postDelayed(runnable, 3000);
                    }
                }
            };
            handler.postDelayed(runnable, 3000);
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void showNotificationBubble(String message) {
        notificationBubble.setText(message);
        notificationBubble.setVisibility(View.VISIBLE);
    }

    private void hideNotificationBubble() {
        notificationBubble.setVisibility(View.GONE);
    }

    private void startSign_Up() {
        Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
