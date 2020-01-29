package com.example.apprestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class cocinero1 extends AppCompatActivity {

    TableLayout ordenes;
    RequestQueue rq;
    Boolean multicolor = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocinero1);
        ordenes = (TableLayout)findViewById(R.id.trabajo);
        TableRow encabezado = new TableRow(getApplicationContext());
        TextView numero = new TextView(getApplicationContext());
        TextView plato = new TextView(getApplicationContext());
        TextView cantidad = new TextView(getApplicationContext());
        TextView activa = new TextView(getApplicationContext());
        numero.setText("Orden No.");
        plato.setText("Plato");
        cantidad.setText("Cantidad");
        activa.setText("Finalizar");
        numero.setGravity(Gravity.CENTER);
        plato.setGravity(Gravity.CENTER);
        cantidad.setGravity(Gravity.CENTER);
        activa.setGravity(Gravity.CENTER);
        numero.setTypeface(plato.getTypeface(),Typeface.BOLD);
        plato.setTypeface(plato.getTypeface(),Typeface.BOLD);
        cantidad.setTypeface(plato.getTypeface(),Typeface.BOLD);
        activa.setTypeface(plato.getTypeface(),Typeface.BOLD);
        encabezado.addView(numero,params());
        encabezado.addView(plato,params());
        encabezado.addView(cantidad,params());
        encabezado.addView(activa,params());
        encabezado.setBackgroundColor(Color.GREEN);
        ordenes.addView(encabezado);



        llenarordenes("https://gyewebsite.000webhostapp.com/verordenes.php");
    }


    private void blanqueartabla(){
        for (int i = 0; i < ordenes.getChildCount(); i++) {
            View vi = ordenes.getChildAt(i);
            ordenes.removeView(vi);
            System.out.println(vi);
        }

    }

    private TableRow.LayoutParams params(){
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight = 1;
        params.gravity = Gravity.CENTER;
        return params;
    }



    private void llenarordenes(String URL){
        JsonArrayRequest jrq = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        TableRow fila = new TableRow(cocinero1.this);


                        if(jsonObject.getString("activa").equals("1")){

                            TextView numeroorden = new TextView(cocinero1.this);
                            TextView pedido = new TextView(cocinero1.this);
                            TextView cantidad = new TextView(cocinero1.this);
                            final RadioButton listo = new RadioButton(cocinero1.this);

                            final int numero = Integer.valueOf(jsonObject.getString("numero"));
                            listo.setId(numero);
                            listo.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    int orden = v.getId();


                                    for (int i = 1; i < ordenes.getChildCount(); i++) {
                                        TableRow actual = (TableRow)ordenes.getChildAt(i);
                                        Integer ordenactual = Integer.valueOf(((TextView)actual.getChildAt(0)).getText().toString());
                                        if (orden==ordenactual){
                                            ordenes.removeView(actual);
                                            cambiarestado(String.valueOf(orden));
                                            Recolorear();
                                        }

                                    }
                                }
                            });

                            numeroorden.setText(jsonObject.getString("numero"));
                            pedido.setText(jsonObject.getString("pedido"));
                            cantidad.setText(jsonObject.getString("cantidad"));
                            fila.addView(numeroorden,params());
                            fila.addView(pedido,params());
                            fila.addView(cantidad,params());
                            fila.addView(listo,params());


                            fila.setBackgroundColor((multicolor)?Color.CYAN:Color.WHITE);
                            multicolor = !multicolor;

                            ordenes.addView(fila);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }ordenes.setColumnStretchable('*',true);
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


    private void cambiarestado(final String numero){
        String url = "https://gyewebsite.000webhostapp.com/cambiarestadoorden.php";
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Se guardaron los cambios");
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
                parametros.put("numero",numero);
                return parametros;
            }

        };
        rq = Volley.newRequestQueue(this);
        rq.add(sr);


    }

    public void Recolorear(){

        for (int i = 1; i < ordenes.getChildCount(); i++) {

            TableRow fila = (TableRow)ordenes.getChildAt(i);
            fila.setBackgroundColor((multicolor)? Color.CYAN:Color.WHITE);
            multicolor = !multicolor;

        }


    }

    public void volver(View v){
        finish();
    }

}
