package com.example.moises.proyectofinal;
import com.example.moises.proyectofinal.MainActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.moises.proyectofinal.BaseDatos.DatosOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Description extends AppCompatActivity {
    private TextView task,date,description,importance;
    private ListView lstDatos1;
    private ArrayAdapter<String> adaptador;
    public ArrayList<String> tareas;

    private SQLiteDatabase conexion;
    private DatosOpenHelper datosOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        lstDatos1 = (ListView) findViewById(R.id.lstDatos);
        tareas = new ArrayList<String>();
        TextView noTasks = (TextView) findViewById(R.id.textView);

        try {
            datosOpenHelper = new DatosOpenHelper(this);
            conexion = datosOpenHelper.getReadableDatabase();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM TAREA ");
            String sTarea;
            String sDescripcion;

            Cursor resultado = conexion.rawQuery(sql.toString(), null);

            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
                do {
                    sTarea = resultado.getString(resultado.getColumnIndex("TAREA"));
                    sDescripcion = resultado.getString(resultado.getColumnIndex("DESCRIPCION"));
                    tareas.add(sTarea + ": " + sDescripcion);
                }
                while (resultado.moveToNext());
                noTasks.setVisibility(TextView.GONE);
            }
            else
            {
                noTasks.setVisibility(TextView.VISIBLE);
            }

            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tareas);
            lstDatos1.setAdapter(adaptador);
        }
        catch (Exception ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

    }

}