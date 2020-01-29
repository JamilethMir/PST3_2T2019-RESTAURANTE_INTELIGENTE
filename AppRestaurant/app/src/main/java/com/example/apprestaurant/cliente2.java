package com.example.apprestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class cliente2 extends AppCompatActivity {

    TextView orden,detalle;
    String mensaje;
    Button volver;
    HashMap<String, Integer> ordenes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente2);
        orden = (TextView)findViewById(R.id.muestraorden);
        detalle = (TextView)findViewById(R.id.detallada);
        volver = (Button) findViewById(R.id.btnVolver);
        float total;


        String encabezado = "Su orden es: \n";
        orden.setText(encabezado);
        orden.setTextSize(24);
        orden.setTypeface(orden.getTypeface(), Typeface.BOLD);
        mensaje = "";
        Bundle bundle = getIntent().getExtras();

        total =  bundle.getFloat("total");
        ordenes = (HashMap<String, Integer>)bundle.getSerializable("ordenes");

        for (Map.Entry<String,Integer> entry : ordenes.entrySet()) {
            System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());
            mensaje += entry.getKey() + ": "+entry.getValue()+ "\n" ;
        }

        mensaje += "\n" + "El total a pagar es:  $" + String.valueOf(total) + "\n" ;


        detalle.setText(mensaje);
        detalle.setTextSize(20);

    }

    public void volver(View v){
        finish();
    }


}
