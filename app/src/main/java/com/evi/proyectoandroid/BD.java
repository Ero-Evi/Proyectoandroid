package com.evi.proyectoandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class BD extends SQLiteOpenHelper {
    private static final String Database_Name = "registros.db";
    private static final String Table_name = "Historial_de_Transacciones";
    private static final String Col_1 = "ID";
    private static final String Col_2 = "Local";
    private static final String Col_3 = "Fecha";
    private static final String Col_4 = "Total";

    public BD(Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + Table_name + " (" +
                        Col_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Col_2 + " TEXT, " +
                        Col_3 + " TEXT, " +
                        Col_4 + " REAL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(db);
    }

    // Método para obtener una transacción por ID
    public HashMap<String, String> getTransaccionById(int id) {
        HashMap<String, String> transaccion = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;

        try {
            res = db.rawQuery("SELECT * FROM " + Table_name + " WHERE " + Col_1 + " = ?", new String[]{String.valueOf(id)});

            if (res != null && res.moveToFirst()) {
                int indexId = res.getColumnIndex(Col_1);
                int indexLocal = res.getColumnIndex(Col_2);
                int indexFecha = res.getColumnIndex(Col_3);
                int indexTotal = res.getColumnIndex(Col_4);

                transaccion = new HashMap<>();
                transaccion.put("ID", res.getString(indexId));
                transaccion.put("Local", res.getString(indexLocal));
                transaccion.put("Fecha", res.getString(indexFecha));
                transaccion.put("Total", res.getString(indexTotal));
            }
        } finally {
            if (res != null) {
                res.close();
            }
        }

        return transaccion;
    }
    // Método para obtener todos los registros de historial
    public ArrayList<HashMap<String, String>> getAllTransacciones() {
        ArrayList<HashMap<String, String>> transacciones = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;

        try {
            res = db.rawQuery("SELECT * FROM " + Table_name, null);

            if (res.moveToFirst()) {
                int indexId = res.getColumnIndex(Col_1);  // Obtén el índice del ID
                int indexLocal = res.getColumnIndex(Col_2);
                int indexFecha = res.getColumnIndex(Col_3);
                int indexTotal = res.getColumnIndex(Col_4);

                do {
                    HashMap<String, String> transaccion = new HashMap<>();
                    transaccion.put("ID", res.getString(indexId));  // Agrega el ID al HashMap
                    transaccion.put("Local", res.getString(indexLocal));
                    transaccion.put("Fecha", res.getString(indexFecha));
                    transaccion.put("Total", res.getString(indexTotal));
                    transacciones.add(transaccion);
                } while (res.moveToNext());
            }
        } finally {
            if (res != null) {
                res.close();
            }
        }

        return transacciones;
    }



    // Método para insertar automáticamente un nuevo registro en la tabla
    public boolean insertTransaccion(String local, String fecha, double total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2, local);
        contentValues.put(Col_3, fecha);
        contentValues.put(Col_4, total);
        long result = db.insert(Table_name, null, contentValues);
        return result != -1;
    }
    // Método para actualizar un registro existente
    public boolean updateTransaccion(int id, String local, String fecha, double total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2, local);
        contentValues.put(Col_3, fecha);
        contentValues.put(Col_4, total);
        int result = db.update(Table_name, contentValues, Col_1 + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Método para eliminar un registro
    public boolean deleteTransaccion(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Table_name, Col_1 + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

}