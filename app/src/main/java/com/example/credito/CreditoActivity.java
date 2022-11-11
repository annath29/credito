package com.example.credito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreditoActivity extends AppCompatActivity {

    EditText  jetcodigoPrestamo,jetidentificacion;
    TextView jtvnombre,jtvprofesion,jtvsalario,jtvextras,jtvgastos,jtvvalorprestamo;
    CheckBox jcbactivo;

    // Instanciar la clase que heredo de la clase SqliteOpenHelper
    ClsOpenHelper admin=new ClsOpenHelper(this,"Banco.db",null,1);
    String identificacion,codigo,nombre,profesion;
    int salario,extras,gastos,valorprestamo=0;
    long resp;
    byte id,sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credito);

        //ocultart barra
        getSupportActionBar().hide();

        //asociar xml a java
        jetcodigoPrestamo=findViewById(R.id.etcodigoPrestamo);
        jetidentificacion=findViewById(R.id.etidentificacion);
        jtvnombre=findViewById(R.id.tvnombre);
        jtvprofesion=findViewById(R.id.tvprofesion);
        jtvsalario=findViewById(R.id.tvsalario);
        jtvextras=findViewById(R.id.tvextras);
        jtvgastos=findViewById(R.id.tvgastos);
        jtvvalorprestamo=findViewById(R.id.tvvalorprestamo);
        jcbactivo=findViewById(R.id.cbactivo);
        id=0;
        sw=0;
    }

    public void Buscar_cliente(View view) {
        identificacion = jetidentificacion.getText().toString();
        if (identificacion.isEmpty()) {
            Toast.makeText(this, "Identificacion requerida", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        }
        else{
            SQLiteDatabase fila=admin.getReadableDatabase();
            Cursor dato=fila.rawQuery("select * from TblCliente where identificacion='"+identificacion+"'",null);
            if(dato.moveToNext()){
                jtvnombre.setText(dato.getString(1));
                jtvprofesion.setText(dato.getString(2));
                jtvsalario.setText(dato.getString(4));
                jtvextras.setText(dato.getString(5));
                jtvgastos.setText(dato.getString(6));
                id=1;
                if (dato.getString(7).equals("si")){
                    jcbactivo.setChecked(true);
                }
                else {
                    jcbactivo.setChecked(false);
                }
            }
            else{
                Toast.makeText(this, "Cliente no registrado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Ejecutar(View view){
        if(id==0)
        {
            Toast.makeText(this, "Debe primero buscar cliente primero para ejecutar un credito", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        }
        else {
            SQLiteDatabase fila=admin.getReadableDatabase();
            Cursor dato=fila.rawQuery("select * from TblCliente where identificacion='"+identificacion+"'",null);
            if(dato.moveToNext()) {
                if (dato.getString(7).equals("no")){
                    Toast.makeText(this, "Usuario inactivo no se puede ejecutar", Toast.LENGTH_SHORT).show();
                }
                else {
                    salario=Integer.parseInt(jtvsalario.getText().toString());
                    gastos=Integer.parseInt(jtvgastos.getText().toString());
                    extras=Integer.parseInt(jtvextras.getText().toString());
                    //Calculo valor del Prestamo
                    valorprestamo=(salario+extras-gastos)*10;
                    //muestro en pantalla el valor del prestamo
                    jtvvalorprestamo.setText(String.valueOf(valorprestamo));
                }
            }

        }
    }

    public void Guardar_credito(View view)
    {
        if (valorprestamo==0)
        {
            Toast.makeText(this, "Primero debe ejecutar", Toast.LENGTH_SHORT).show();
        }
        else
        {
            codigo = jetcodigoPrestamo.getText().toString();
            if (codigo.isEmpty()) {
                Toast.makeText(this, "Debe identificar su credito con un codigo", Toast.LENGTH_SHORT).show();
                jetidentificacion.requestFocus();
            }
            else {
                SQLiteDatabase fila=admin.getReadableDatabase();
                Cursor cliente=fila.rawQuery("select * from TblCredito where cod_credito='"+codigo+"'",null);
                if(cliente.moveToNext()){

                    Toast.makeText(this, "Codigo registrado, ingrese otro codigo", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SQLiteDatabase row=admin.getWritableDatabase();
                    ContentValues registro = new ContentValues();
                    registro.put("identificacion",identificacion);
                    registro.put("cod_credito",codigo);
                    registro.put("valor_prestamo",valorprestamo);

                    resp=row.insert("TblCredito",null,registro);
                    if (resp ==0)
                    {
                        Toast.makeText(this, "Error guardando credito", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this, "Credito guardado", Toast.LENGTH_SHORT).show();
                        Limpiar_campos();
                    }
                    fila.close();
                }

            }
        }
    }
    public void Consultar_credito(View view){
        codigo = jetcodigoPrestamo.getText().toString();
        if (codigo.isEmpty()) {
            Toast.makeText(this, "Codigo requerido", Toast.LENGTH_SHORT).show();
            jetcodigoPrestamo.requestFocus();
        }
        else{
            SQLiteDatabase row=admin.getReadableDatabase();
            Cursor cliente=row.rawQuery("select *from TblCredito inner join TblCliente on TblCredito.identificacion=TblCliente.identificacion where TblCredito.cod_credito='"+codigo+"'",null);
            if(cliente.moveToNext()){
                jetidentificacion.setText(cliente.getString(1));
                jtvvalorprestamo.setText(cliente.getString(2));
                if (cliente.getString(3).equals("si")){
                    Toast.makeText(this, "Credito activo", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Credito inactivo", Toast.LENGTH_SHORT).show();
                }
                jtvnombre.setText(cliente.getString(5));
                jtvprofesion.setText(cliente.getString(6));
                jtvsalario.setText(cliente.getString(8));
                jtvextras.setText(cliente.getString(9));
                jtvgastos.setText(cliente.getString(10));
                if (cliente.getString(11).equals("si")){
                    jcbactivo.setChecked(true);
                }
                else {
                    jcbactivo.setChecked(false);
                }
                sw=1;
            }
            else{
                Toast.makeText(this, "Credito no registrado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Anular_credito(View view)
    {
        
    }
    private void Limpiar_campos(){
        jetcodigoPrestamo.setText("");
        jetidentificacion.setText("");
        jtvnombre.setText("");
        jtvprofesion.setText("");
        jtvsalario.setText("");
        jtvextras.setText("");
        jtvgastos.setText("");
        jtvvalorprestamo.setText("");
        jcbactivo.setChecked(false);
        jetidentificacion.requestFocus();
        id=0;
        sw=0;
    }

}