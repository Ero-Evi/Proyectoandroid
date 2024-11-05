package com.evi.proyectoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class PantallaMain extends AppCompatActivity {
    private ListView listView;
    private TextView mensajeVacio;
    private BD database;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_main);

        // se inicializan las variables y deja un mensaje en caso de que no hayan compras
        database = new BD(this);
        listView = findViewById(R.id.listView);
        mensajeVacio = findViewById(R.id.mensajeVacio);
        mostrarHistorial();
        configurarBotonesInferiores();


    }
    // historial en el array list
    private void mostrarHistorial() {
        ArrayList<HashMap<String, String>> historialTransacciones = database.getAllTransacciones();
        ArrayList<String> transaccionesTexto = new ArrayList<>();

        // Si no hay compras se muestra el mensaje "Aún no has comprado nada"
        if (historialTransacciones.isEmpty()) {
            mensajeVacio.setVisibility(View.VISIBLE); // Mostrar el mensaje
            listView.setVisibility(View.GONE); // Ocultar el ListView
        } else {
            mensajeVacio.setVisibility(View.GONE); // Ocultar el mensaje
            listView.setVisibility(View.VISIBLE); // Mostrar el ListView

            for (HashMap<String, String> transaccion : historialTransacciones) {
                String texto = "Local: " + transaccion.get("Local") +
                        ", Fecha: " + transaccion.get("Fecha") +
                        ", Total: " + transaccion.get("Total");
                transaccionesTexto.add(texto);
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, transaccionesTexto);
            listView.setAdapter(adapter);
        }
    }
    //se ordeno esto para mas comodidad  *logica botones Inferiores*
    private void configurarBotonesInferiores() {
        ImageView PagarImageView = findViewById(R.id.btn_pagar);
        PagarImageView.setOnClickListener(v -> startActivity(new Intent(PantallaMain.this, PantallaMain.class)));

        ImageView MenuImageView = findViewById(R.id.btn_menu);
        MenuImageView.setOnClickListener(v -> startActivity(new Intent(PantallaMain.this, Menu.class)));

        ImageView ComercioImageView = findViewById(R.id.btn_comercios);
        ComercioImageView.setOnClickListener(v -> startActivity(new Intent(PantallaMain.this, MapsActivity.class)));

        ImageView ChatImageView = findViewById(R.id.btn_chat);
        ChatImageView.setOnClickListener(v -> startActivity(new Intent(PantallaMain.this, Chat.class)));

        ImageView MasImageView = findViewById(R.id.btn_mas);
        MasImageView.setOnClickListener(v -> startActivity(new Intent(PantallaMain.this, Mas.class)));
    }

    // Método que se llama cuando se hace clic en el botón "Clave Dinámica"
    public void onRoundButtonClick(View view) {
        // Opciones para el campo "Local"
        String[] locales = {"Constitución", "El Roble", "20 de Agosto"};

        // Selecciona un local al azar ya que no tengo como hacer que me llegue el valor real aun. para tener mas variedad en la BD
        String localAleatorio = locales[new Random().nextInt(locales.length)];

        // me generara aleatoria mente el valor total por ahora ya que no tengo como hacer que me llegue el valor real aun.
        int totalAleatorio = 6000 + new Random().nextInt(44001);

        // Fecha en formato "dd/MM/yyyy" despues agregare la hora tamien si no se me olvida
        String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        // Insertar la transacción en la base de datos
        boolean insertado = database.insertTransaccion(localAleatorio, fechaActual, totalAleatorio);

        if (insertado) {
            // Actualizar la lista de transacciones en el ListView
            mostrarHistorial();
        }
    }
    public void onbtnReclamosClick(View view) {
        ArrayList<HashMap<String, String>> historialTransacciones = database.getAllTransacciones();

        if (historialTransacciones.isEmpty()) {
            mensajeVacio.setText("No hay transacciones para modificar o cancelar.");
            mensajeVacio.setVisibility(View.VISIBLE);
            return;
        }

        ArrayList<String> transaccionesTexto = new ArrayList<>();
        for (HashMap<String, String> transaccion : historialTransacciones) {
            String texto = "Local: " + transaccion.get("Local") +
                    ", Fecha: " + transaccion.get("Fecha") +
                    ", Total: " + transaccion.get("Total");
            transaccionesTexto.add(texto);
        }

        new AlertDialog.Builder(this)
                .setTitle("Seleccionar Transacción")
                .setSingleChoiceItems(transaccionesTexto.toArray(new String[0]), -1, (dialog, which) -> {
                    int idSeleccionado = Integer.parseInt(historialTransacciones.get(which).get("ID"));
                    new AlertDialog.Builder(this)
                            .setTitle("Seleccionar Acción")
                            .setMessage("¿Desea modificar o cancelar el pedido?")
                            .setPositiveButton("Modificar", (dialog1, which1) -> {
                                editarTransaccion(idSeleccionado);
                            })
                            .setNegativeButton("Cancelar", (dialog1, which1) -> {
                                eliminarTransaccion(idSeleccionado);
                            })
                            .show();
                    dialog.dismiss();
                })
                .setNegativeButton("Cerrar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void editarTransaccion(int id) {
        HashMap<String, String> transaccion = database.getTransaccionById(id);
        if (transaccion != null) {
            String local = transaccion.get("Local");
            String fecha = transaccion.get("Fecha");
            double total = Double.parseDouble(transaccion.get("Total"));

            // aqui se editan los datos del historial
            String nuevoLocal = obtenerNuevoValor("Ingrese el nuevo local:", local); // Aqui se cambia el local para la BD
            String nuevaFecha = obtenerNuevoValor("Ingrese la nueva fecha:", fecha); // Aqui se cambia la fecha para la BD
            String nuevoTotalStr = obtenerNuevoValor("Ingrese el nuevo total:", String.valueOf(total)); // Aqui se cambia el total para la BD

            double nuevoTotal;
            try {
                nuevoTotal = Double.parseDouble(nuevoTotalStr);
            } catch (NumberFormatException e) {
                mostrarMensaje("El total ingresado no es válido."); //  mensaje al usuario en caso de que no sea un valor valido
                return; // Salir si el valor no es válido
            }

            // Actualiza la transacción en la base de datos
            if (database.updateTransaccion(id, nuevoLocal, nuevaFecha, nuevoTotal)) {
                mostrarHistorial(); // Actualiza la vista después de la edición
            } else {
                mostrarMensaje("Error al actualizar la transacción."); // Método ficticio para mostrar un mensaje al usuario
            }
        } else {
            mostrarMensaje("Transacción no encontrada."); // Método ficticio para mostrar un mensaje al usuario
        }
    }

    // Métodos ficticios para obtener entrada del usuario y mostrar mensajes
    private String obtenerNuevoValor(String mensaje, String valorActual) {
        final String[] nuevoValor = {valorActual}; // Usamos un array para capturar el valor en un contexto final

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(mensaje);

        // Crear un campo de texto
        final EditText input = new EditText(this);
        input.setText(valorActual);
        builder.setView(input);

        // Configurar botones
        builder.setPositiveButton("OK", (dialog, which) -> nuevoValor[0] = input.getText().toString());
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();

        return nuevoValor[0];
    }


    private void mostrarMensaje(String mensaje) {
        // luego agrego si no se me olvida *se me olvido*
    }

    //se llama la variable para eliminar un dato
    private void eliminarTransaccion(int id) {
        if (database.deleteTransaccion(id)) {
            mostrarHistorial();
        }
    }

}
