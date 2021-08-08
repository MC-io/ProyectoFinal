package com.example.moises.proyectofinal;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.example.moises.proyectofinal.BaseDatos.DatosOpenHelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuInflater;

import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

public class ActNuevaTarea extends AppCompatActivity implements View.OnClickListener {
    private EditText edtTarea;
    private EditText edtFecha;
    private EditText edtImportancia;
    private EditText edtDescripcion;

    private SQLiteDatabase conexion;
    private DatosOpenHelper datosOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_nueva_tarea);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtTarea = (EditText) findViewById(R.id.edtTarea);
        edtFecha = (EditText) findViewById(R.id.edtFecha);
        edtFecha.setOnClickListener(this);
        edtImportancia = (EditText) findViewById(R.id.edtImportancia);
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcion);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_nueva_tarea, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_ok:
                if (bCamposCorrectos()) {
                    try {
                        datosOpenHelper = new DatosOpenHelper(this);
                        conexion = datosOpenHelper.getWritableDatabase();
                        StringBuilder sql = new StringBuilder();
                        sql.append("INSERT INTO TAREA (TAREA, FECHA, IMPORTANCIA, DESCRIPCION) VALUES ('");
                        sql.append(edtTarea.getText().toString().trim() + "', '");
                        sql.append(edtFecha.getText().toString().trim() + "', '");
                        sql.append(edtImportancia.getText().toString().trim() + "', '");
                        sql.append(edtDescripcion.getText().toString().trim() + "')");

                        conexion.execSQL(sql.toString());
                        conexion.close();

                        finish();
                    } catch (Exception ex) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                        dlg.setTitle("Aviso");
                        dlg.setMessage(ex.getMessage());
                        dlg.setNeutralButton("OK", null);
                        dlg.show();
                    }
                }
                else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setTitle("Aviso");
                    dlg.setMessage("Llene todos los campos");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                }
                //Toast.makeText(this, "Boton Ok seleccionado", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_cancelar:
                //Toast.makeText(this, "Boton Cancelar seleccionado", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
//Funcion que comprueba que este llenos los espacios y devuelve true si estan llenos
    private boolean bCamposCorrectos() {
        boolean res = true;
        if (edtTarea.getText().toString().trim().isEmpty()) {
            edtTarea.requestFocus();
            res = false;
        }
        if (edtFecha.getText().toString().trim().isEmpty()) {
            edtTarea.requestFocus();
            res = false;
        }
        if (edtImportancia.getText().toString().trim().isEmpty()) {
            edtTarea.requestFocus();
            res = false;
        }
        if (edtDescripcion.getText().toString().trim().isEmpty()) {
            edtTarea.requestFocus();
            res = false;
        }
        return res;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtFecha:
                showDatePickerDialog();
                break;
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                String strYear = year + "";
                String strMonth = month + "";
                String strDay = day + "";
                if (month < 9)
                {
                    strMonth = "0" + (month+1);
                }
                if (day < 10)
                {
                    strDay = "0" + day;
                }
                final String selectedDate = strYear + "-" + strMonth + "-" + strDay;
                edtFecha.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}