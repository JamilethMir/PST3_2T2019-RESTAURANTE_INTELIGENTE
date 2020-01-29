package com.example.apprestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.TextView;

public class InfoPopup extends AppCompatActivity {

    TextView titulo;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info_popup);

        Bundle bundle = getIntent().getExtras();
        String nombreplato =  bundle.getString("titulo");
        String descripcion = bundle.getString("descripcion");
        String tiempo = bundle.getString("tiempo");
        String mensaje = "";

        System.out.println(nombreplato);
        System.out.println(descripcion + "\n" + "\n" + "Tiempo de preparacion: " + tiempo );
        titulo = (TextView)findViewById(R.id.tituloplato);
        titulo.setText(nombreplato);
        titulo.setTextSize(24);
        titulo.setTypeface(titulo.getTypeface(), Typeface.BOLD);
        mensaje = descripcion + "\n" + "\n" + "Tiempo de preparacion: " + tiempo + " minutos" ;

        info = (TextView)findViewById(R.id.descripcion);
        info.setText(mensaje);
        info.setTextSize(20);

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho*0.85),(int)(alto*0.5));


    }
}
