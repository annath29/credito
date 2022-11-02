package com.example.credito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UsuarioActivity extends AppCompatActivity {

    EditText jetidentificacion,jetnombre,jetprofesion,jetempresa,jetsalario,jetextras,jetgastos;
    CheckBox jcbactivo;
    // Instanciar la clase que heredo de la clase SqliteOpenHelper
    ClsOpenHelper admin=new ClsOpenHelper(this,"Banco.db",null,1);
    String identificacion,nombre,profesion,empresa,salario,extras,gastos;
    Long respuesta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        getSupportActionBar().hide();

        //asociar elementos xml a java

        jetidentificacion=findViewById(R.id.etidentificacion);
        jetnombre=findViewById(R.id.etnombre);
        jetprofesion=findViewById(R.id.etprofesion);
        jetempresa=findViewById(R.id.etempresa);
        jetsalario=findViewById(R.id.etsalario);
        jetextras=findViewById(R.id.etextra);
        jetgastos=findViewById(R.id.etgastos);
        jcbactivo=findViewById(R.id.cbactivo);
    }

    public void Guardar(View view) {
        identificacion = jetidentificacion.getText().toString();
        nombre = jetnombre.getText().toString();
        profesion = jetprofesion.getText().toString();
        empresa = jetempresa.getText().toString();
        salario = jetsalario.getText().toString();
        extras = jetextras.getText().toString();
        gastos = jetgastos.getText().toString();

        if (identificacion.isEmpty() || nombre.isEmpty() || profesion.isEmpty() || empresa.isEmpty() || salario.isEmpty() || extras.isEmpty() || gastos.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        }
        else {
            SQLiteDatabase fila=admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("identificacion",identificacion);
            registro.put("nombre",nombre);
            registro.put("profesion",profesion);
            registro.put("empresa",empresa);
            registro.put("salario",Integer.parseInt(salario));
            registro.put("ingreso_extra",Integer.parseInt(extras));
            registro.put("gastos",Integer.parseInt(gastos));
            respuesta=fila.insert("TblCliente",null,registro);
            if (respuesta ==0)
            {
                Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }
            fila.close();
        }
    }
    private void Limpiar_campos(){
        jetidentificacion.setText("");
        jetnombre.setText("");
        jetprofesion.setText("");
        jetempresa.setText("");
        jetsalario.setText("");
        jetextras.setText("");
        jetgastos.setText("");
        jcbactivo.setChecked(false);
        jetidentificacion.requestFocus();

    }
}