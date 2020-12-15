package com.example.threadexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {

    private MyHandler myHandler;
    private boolean isRunning = true;
    private long currentTimeInMillis;
    private long remaining = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (savedInstanceState != null){
            remaining = savedInstanceState.getLong("remaining_delay",5000);
        }
        currentTimeInMillis = System.currentTimeMillis();
        myHandler = new MyHandler(new WeakReference<>(this));
        Message message = myHandler.obtainMessage();
        myHandler.sendMessageDelayed(message , remaining);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        remaining -=(System.currentTimeMillis() - currentTimeInMillis);
        outState.putLong("remaining_delay", remaining);
    }

    static class MyHandler extends Handler{
        private final WeakReference<SplashActivity> splashActivityWeakReference;

        MyHandler(WeakReference<SplashActivity> splashActivityWeakReference) {
            super();
            this.splashActivityWeakReference = splashActivityWeakReference;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            SplashActivity splashActivity = splashActivityWeakReference.get();
            if (splashActivity != null){
                splashActivity.startActivity(new Intent(splashActivity , MessageHandlersActivity.class));
                splashActivity.finish();
            }
        }
    }
}