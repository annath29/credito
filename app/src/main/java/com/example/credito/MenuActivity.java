package com.example.credito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    TextView jtvusuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //ocultar barra menu
        getSupportActionBar().hide();
        jtvusuario=findViewById(R.id.tvusuario);
        //mostar en textView el usuario de la actividad principal
        String usuario;
        usuario=getIntent().getStringExtra("datos");
        jtvusuario.setText(usuario);
    }
    public void Credito(View view)
    {
        Intent intcredito=new Intent(this,CreditoActivity.class);
        startActivity(intcredito);
    }

    public void Usuario(View view)
    {
        Intent intusuario=new Intent(this,UsuarioActivity.class);
        startActivity(intusuario);
    }

    public void Salir(View view)
    {
        Intent intmain=new Intent(this,MainActivity.class);
        startActivity(intmain);
    }

}