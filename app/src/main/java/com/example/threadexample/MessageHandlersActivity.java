package com.example.threadexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MessageHandlersActivity extends AppCompatActivity {
    private static final int SET_PROGRESS = 100;
    private static final int SHOW_IMAGE = 101;
    private ProgressBar progressBar;
    private ImageView imageView;
    private MyHandler myHandler;

    static class MyHandler extends Handler {
        private final WeakReference<MessageHandlersActivity> messageHandlersActivityWeakReference;

        public MyHandler(WeakReference<MessageHandlersActivity> messageHandlersActivityWeakReference) {
            super();
            this.messageHandlersActivityWeakReference = messageHandlersActivityWeakReference;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            MessageHandlersActivity messageHandlersActivity = messageHandlersActivityWeakReference.get();
            if (messageHandlersActivity != null) {
                switch (msg.what) {
                    case SET_PROGRESS:
                        messageHandlersActivity.progressBar.setProgress(msg.arg1);
                        break;
                    case SHOW_IMAGE:
                        messageHandlersActivity.imageView.setImageBitmap((Bitmap) msg.obj);
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_handlers);

        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);

        myHandler = new MyHandler(new WeakReference<>(this));
    }

    public void loadImage(View view) {

//      لو كان عندي عدة ثريد بنفذها من خلال ثريد بول كما هو موضح هنا
//        Executor executor = Executors.newFixedThreadPool(3);
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                //TODO:
//            }
//        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a15);
                for (int i = 0; i <= 100; i++) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message progressMessage = myHandler.obtainMessage(SET_PROGRESS, i, -1);
                    myHandler.sendMessage(progressMessage);
                }
                Message showMessage = myHandler.obtainMessage(SHOW_IMAGE, bitmap);
                myHandler.sendMessage(showMessage);
            }
        }).start();

    }

    public void showMessage(View view) {
        Toast.makeText(this, "Button Clicked!", Toast.LENGTH_SHORT).show();
    }
}