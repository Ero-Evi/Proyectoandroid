package com.evi.proyectoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Random random = new Random();
        int delay = (random.nextInt(5) + 1) * 1000;

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(splashScreen.this, PantallaMain.class);
            startActivity(intent);
            finish();
        }, delay);
    }
}