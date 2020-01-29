package com.example.apprestaurant;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class ADM2 extends AppCompatActivity {
    EditText edtid,edtplato,edtPrecio,edtCantidad;
    Button btnmodificar,btneliminar,btnAgregar;
    TableLayout menu;
    RequestQueue rq;
    boolean multicolor = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm2);
        edtid= (EditText)findViewById(R.id.editText);
        edtplato= (EditText)findViewById(R.id.editText2);
        edtPrecio= (EditText)findViewById(R.id.editText3);
        edtCantidad= (EditText)findViewById(R.id.editText4);
        btnAgregar= (Button)findViewById(R.id.button5);
        btnmodificar= (Button)findViewById(R.id.button3);
        btneliminar= (Button)findViewById(R.id.button6);
        menu = (TableLayout)findViewById(R.id.espaciomenu2);
        TableRow encabezado = new TableRow(getApplicationContext());
        TextView plato = new TextView(getApplicationContext());
        TextView precio = new TextView(getApplicationContext());
        plato.setText("Plato");
        precio.setText("Precio");
        plato.setGravity(Gravity.CENTER);
        precio.setGravity(Gravity.CENTER);
        precio.setTypeface(plato.getTypeface(), Typeface.BOLD);
        plato.setTypeface(plato.getTypeface(),Typeface.BOLD);
        encabezado.addView(plato,params());
        encabezado.addView(precio,params());
        encabezado.setBackgroundColor(Color.BLUE);
        menu.addView(encabezado);
        llenarmenu("https://gyewebsite.000webhostapp.com/llenarmenu.php");

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Url= "gyewebsite.000webhostapp.com/insertar1.php";
                ejecutarServicio(Url);

            }
        });
        btnmodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Url= "gyewebsite.000webhostapp.com/modificar.php";
                ejecutarServicio(Url);

            }
        });
        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarProducto("gyewebsite.000webhostapp.com/eliminar.php");

            }
        });


    }
    private void ejecutarServicio(String URL){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("id",edtid.getText().toString());
                parametros.put("plato",edtplato.getText().toString());
                parametros.put("precio",edtPrecio.getText().toString());
                parametros.put("ventas",edtCantidad.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void eliminarProducto(String URL){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                limpiar();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("plato",edtplato.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void limpiar(){
        edtid.setText("");
        edtplato.setText("");
        edtCantidad.setText("");
        edtPrecio.setText("");
    }
    private void llenarmenu(String URL){
        JsonArrayRequest jrq = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        TableRow fila = new TableRow(ADM2.this);
                        TextView plato = new TextView(ADM2.this);
                        TextView precio = new TextView(ADM2.this);
                        plato.setText(jsonObject.getString("plato"));
                        precio.setText(jsonObject.getString("precio"));
                        plato.setGravity(Gravity.CENTER);
                        precio.setGravity(Gravity.CENTER);

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

                        fila.setBackgroundColor((multicolor)? Color.CYAN:Color.WHITE);
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
}

