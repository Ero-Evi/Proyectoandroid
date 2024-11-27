package com.evi.proyectoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class PantallaMain extends AppCompatActivity {
    private ListView listView;
    private TextView mensajeVacio;
    private BD database;
    private ArrayAdapter<String> adapter;
    private MqttHandler mqttHandler;

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
        // Inicializar el manejador de MQTT
        mqttHandler = new MqttHandler();
        mqttHandler.connect("tcp://broker.emqx.io:1883", "AndroidClient");


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

        // Selecciona un local al azar
        String localAleatorio = locales[new Random().nextInt(locales.length)];

        // Generar aleatoriamente el valor total
        int totalAleatorio = 6000 + new Random().nextInt(44001);

        // Fecha en formato "dd/MM/yyyy"
        String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        // Insertar la transacción en la base de datos SQLite
        boolean insertado = database.insertTransaccion(localAleatorio, fechaActual, totalAleatorio);

        if (insertado) {
            // Actualizar la lista de transacciones en el ListView
            mostrarHistorial();

            // Crear un objeto de datos para Firebase y MQTT
            Map<String, Object> transaccion = new HashMap<>();
            transaccion.put("Local", localAleatorio);
            transaccion.put("Fecha", fechaActual);
            transaccion.put("Total", totalAleatorio);

            // Enviar los datos al servidor MQTT
            String mensajeMQTT = String.format(
                    "{\"Local\": \"%s\", \"Fecha\": \"%s\", \"Total\": %.2f}",
                    localAleatorio, fechaActual, (double) totalAleatorio
            );

            if (mqttHandler != null) { // Asegúrate de que mqttHandler esté inicializado
                mqttHandler.publish("transacciones/nuevas", mensajeMQTT);
            } else {
                Log.e("onRoundButtonClick", "El manejador MQTT no está inicializado.");
            }

            // Enviar los datos a Firebase Realtime Database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Transacciones");
            databaseReference.push().setValue(transaccion)
                    .addOnSuccessListener(aVoid -> Log.i("onRoundButtonClick", "Transacción guardada en Firebase Realtime Database."))
                    .addOnFailureListener(e -> Log.e("onRoundButtonClick", "Error al guardar en Firebase Realtime Database: " + e.getMessage()));

            // Enviar los datos a Firebase Firestore (opcional)
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            firestore.collection("Transacciones")
                    .add(transaccion)
                    .addOnSuccessListener(documentReference -> Log.i("onRoundButtonClick", "Transacción guardada en Firestore con ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.e("onRoundButtonClick", "Error al guardar en Firestore: " + e.getMessage()));

        } else {
            // Manejo de error en caso de que no se pueda insertar en la base de datos SQLite
            Log.e("onRoundButtonClick", "Error al insertar la transacción en SQLite.");
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
