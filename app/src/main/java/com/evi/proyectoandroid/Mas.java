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
        configurarBotonesInferiores();
    }
    private void configurarBotonesInferiores() {
        ImageView PagarImageView = findViewById(R.id.btn_pagar);
        PagarImageView.setOnClickListener(v -> startActivity(new Intent(Mas.this, PantallaMain.class)));

        ImageView MenuImageView = findViewById(R.id.btn_menu);
        MenuImageView.setOnClickListener(v -> startActivity(new Intent(Mas.this, Menu.class)));

        ImageView ComercioImageView = findViewById(R.id.btn_comercios);
        ComercioImageView.setOnClickListener(v -> startActivity(new Intent(Mas.this, MapsActivity.class)));

        ImageView ChatImageView = findViewById(R.id.btn_chat);
        ChatImageView.setOnClickListener(v -> startActivity(new Intent(Mas.this, Chat.class)));

        ImageView MasImageView = findViewById(R.id.btn_mas);
        MasImageView.setOnClickListener(v -> startActivity(new Intent(Mas.this, Mas.class)));
    }
}