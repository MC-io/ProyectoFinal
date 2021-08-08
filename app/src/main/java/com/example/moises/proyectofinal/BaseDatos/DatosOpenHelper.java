package com.example.moises.proyectofinal.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatosOpenHelper extends SQLiteOpenHelper{

    public DatosOpenHelper(Context context) {
        super(context, "DATOS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS TAREA (");
        sql.append("TAREA VARCHAR(100), ");
        sql.append("FECHA DATE, ");
        sql.append("IMPORTANCIA INT, ");
        sql.append("DESCRIPCION VARCHAR(360))");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il) {

    }
}