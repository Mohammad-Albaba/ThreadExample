package com.example.threadexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView imageView;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
    }

    public void loadImage(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.a15);
                for (int i=0 ; i<=100 ; i++) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int finalI = i;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(finalI);
                        }
                    });
                }
                    handler.post(new Runnable() {
                        @Override
                       public void run() {
                       imageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();

    }

    public void showMessage(View view) {
        Toast.makeText(this, "Button Clicked!", Toast.LENGTH_SHORT).show();
    }
}