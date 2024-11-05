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

import com.github.pdfviewer.PDFView;

public class Menu extends AppCompatActivity {
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        configurarBotonesInferiores();

        PDFView pdfView = findViewById(R.id.pdfView);

        // Cargar PDF desde assets
        pdfView.fromAsset("menup.pdf")
                .enableSwipe(true) // Permite el deslizamiento entre páginas
                .swipeHorizontal(false) // Vertical o horizontal
                .enableDoubletap(true) // Habilitar doble toque para zoom
                .load();
    }
    private void configurarBotonesInferiores() {
        ImageView PagarImageView = findViewById(R.id.btn_pagar);
        PagarImageView.setOnClickListener(v -> startActivity(new Intent(Menu.this, PantallaMain.class)));

        ImageView MenuImageView = findViewById(R.id.btn_menu);
        MenuImageView.setOnClickListener(v -> startActivity(new Intent(Menu.this, Menu.class)));

        ImageView ComercioImageView = findViewById(R.id.btn_comercios);
        ComercioImageView.setOnClickListener(v -> startActivity(new Intent(Menu.this, MapsActivity.class)));

        ImageView ChatImageView = findViewById(R.id.btn_chat);
        ChatImageView.setOnClickListener(v -> startActivity(new Intent(Menu.this, Chat.class)));

        ImageView MasImageView = findViewById(R.id.btn_mas);
        MasImageView.setOnClickListener(v -> startActivity(new Intent(Menu.this, Mas.class)));
    }
}