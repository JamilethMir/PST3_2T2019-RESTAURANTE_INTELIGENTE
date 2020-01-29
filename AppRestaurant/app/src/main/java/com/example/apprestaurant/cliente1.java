package com.example.apprestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class cliente1 extends AppCompatActivity {

    TableLayout menu;
    RequestQueue rq;
    boolean multicolor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente1);
        menu = (TableLayout)findViewById(R.id.espaciomenu);
        llenarmenu("https://gyewebsite.000webhostapp.com/llenarmenu.php");
        TableRow encabezado = new TableRow(getApplicationContext());
        TextView plato = new TextView(getApplicationContext());
        TextView precio = new TextView(getApplicationContext());
        TextView cantidad = new TextView(getApplicationContext());
        plato.setText("Plato");
        precio.setText("Precio");
        cantidad.setText("Cantidad");
        plato.setGravity(Gravity.CENTER);
        precio.setGravity(Gravity.CENTER);
        cantidad.setGravity(Gravity.CENTER);
        precio.setTypeface(plato.getTypeface(), Typeface.BOLD);
        plato.setTypeface(plato.getTypeface(),Typeface.BOLD);
        cantidad.setTypeface(plato.getTypeface(),Typeface.BOLD);
        encabezado.addView(plato,params());
        encabezado.addView(precio,params());
        encabezado.addView(cantidad,params());
        encabezado.setBackgroundColor(Color.BLUE);
        menu.addView(encabezado);

    }



    private void llenarmenu(String URL){
        JsonArrayRequest jrq = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        TableRow fila = new TableRow(cliente1.this);
                        TextView plato = new TextView(cliente1.this);
                        TextView precio = new TextView(cliente1.this);
                        EditText cantidad = new EditText(cliente1.this);
                        plato.setText(jsonObject.getString("plato"));
                        precio.setText(jsonObject.getString("precio"));
                        cantidad.setText("0");
                        plato.setGravity(Gravity.CENTER);
                        precio.setGravity(Gravity.CENTER);
                        cantidad.setGravity(Gravity.CENTER);

                        plato.setTextSize(14);
                        precio.setTextSize(14);

                        final String nombreplato = jsonObject.getString("plato");
                        final String descripcion = jsonObject.getString("descripcion");
                        final String tiempo = jsonObject.getString("tiempo");


                        plato.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Intent i = new Intent(getApplicationContext(), InfoPopup.class );
                                i.putExtra("titulo",nombreplato);
                                i.putExtra("descripcion",descripcion);
                                i.putExtra("tiempo",tiempo);

                                startActivity(i);

                            }
                        });

                        fila.addView(plato,params());
                        fila.addView(precio,params());
                        fila.addView(cantidad,params());

                        fila.setBackgroundColor((multicolor)?Color.CYAN:Color.WHITE);
                        multicolor = !multicolor;

                        menu.addView(fila);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERROR DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        rq = Volley.newRequestQueue(this);
        rq.add(jrq);
    }



    public TableRow.LayoutParams params(){
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight = 1;
        params.gravity = Gravity.CENTER;
        return params;
    }

    private void agregarOrden(String URL,final String id,final String pedido,final String cantidad){

        StringRequest sr = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("id",id);
                parametros.put("pedido",pedido);
                parametros.put("cantidad",cantidad);
                return parametros;
            }

        };
        rq = Volley.newRequestQueue(this);
        rq.add(sr);


    }

    public void ordenar(View v){
        HashMap<String,Integer> ordenes = new HashMap<>();

        int cuentaorden = 0;
        float total = 0;
        for (int i = 1; i < menu.getChildCount(); i++) {

            TableRow filaactual = (TableRow)menu.getChildAt(i);
            int cantidad = Integer.valueOf(((EditText)filaactual.getChildAt(2)).getText().toString());
            if(cantidad > 0){
                String platopedido = ((TextView)filaactual.getChildAt(0)).getText().toString();
                float valor = Float.valueOf(((TextView)filaactual.getChildAt(1)).getText().toString());

                float costo = valor*cantidad;

                total+= costo;
                cuentaorden += cantidad;
                agregarOrden("https://gyewebsite.000webhostapp.com/agregarorden.php",String.valueOf(i-1),platopedido,String.valueOf(cantidad));
                ordenes.put(platopedido,cantidad);
            }
        }Intent i = new Intent(this, cliente2.class );
        i.putExtra("ordenes",ordenes);
        i.putExtra("total",total);
        startActivity(i);


    }

    public void volver(View v){
        finish();
    }



}

