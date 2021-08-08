package com.example.moises.proyectofinal;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.moises.proyectofinal.BaseDatos.DatosOpenHelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import android.view.View;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lstDatos;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> tareas;

    private SQLiteDatabase conexion;
    private DatosOpenHelper datosOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, ActNuevaTarea.class);
                //startActivity(it);
                startActivityForResult(it, 0);
            }
        });
        actualizar();
    }

    private void actualizar() {
        lstDatos = (ListView) findViewById(R.id.lstDatos);
        tareas = new ArrayList<String>();

        try {
            datosOpenHelper = new DatosOpenHelper(this);
            conexion = datosOpenHelper.getWritableDatabase();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM TAREA ORDER BY IMPORTANCE DESC, FECHA ASC");
            String sTarea;
            String sFecha;

            Cursor resultado = conexion.rawQuery(sql.toString(), null);

            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
                do {
                    sTarea = resultado.getString(resultado.getColumnIndex("TAREA"));
                    sFecha = resultado.getString(resultado.getColumnIndex("FECHA"));
                    tareas.add(sFecha + ": " + sTarea);
                }
                while (resultado.moveToNext());
            }

            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tareas);
            lstDatos.setAdapter(adaptador);
        }
        catch (Exception ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        actualizar();
        //super.onActivityResult(requestCode, resultCode, data); //verificar
    }
}