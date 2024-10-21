package com.evi.proyectoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Mas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mas);

        ImageView PagarImageView = findViewById(R.id.btn_pagar);
        PagarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mas.this, PantallaMain.class);
                startActivity(intent);
            }
        });

        ImageView MenuImageView = findViewById(R.id.btn_menu);
        MenuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mas.this, Menu.class);
                startActivity(intent);
            }
        });

        ImageView ComercioImageView = findViewById(R.id.btn_comercios);
        ComercioImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mas.this, Mapa.class);
                startActivity(intent);
            }
        });

        ImageView ChatImageView = findViewById(R.id.btn_chat);
        ChatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mas.this, Chat.class);
                startActivity(intent);
            }
        });
        ImageView MasImageView = findViewById(R.id.btn_mas);
        MasImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mas.this, Mas.class);
                startActivity(intent);
            }
        });
    }
}