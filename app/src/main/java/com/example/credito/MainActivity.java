package com.example.credito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText jetusuario,jetclave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ocultar barra de titulo y asociar xml a java
        getSupportActionBar().hide();
        jetusuario=findViewById(R.id.etusuario);
        jetclave=findViewById(R.id.etclave);

    }

    public void Ingresar(View view){
        String usuario=jetusuario.getText().toString();
        Intent intmenu=new Intent(this,MenuActivity.class);
        intmenu.putExtra("datos",usuario);
        startActivity(intmenu);

    }

    public void limpiar(View view)
    {
        jetclave.setText("");
        jetusuario.setText("");
        jetusuario.requestFocus();
    }
}