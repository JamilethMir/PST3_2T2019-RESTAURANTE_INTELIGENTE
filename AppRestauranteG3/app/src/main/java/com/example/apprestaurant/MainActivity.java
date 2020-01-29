package com.example.apprestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cliente(View v){
        Intent i = new Intent(this, cliente1.class );
        startActivity(i);

    }
    public void cocinero(View v){
        Intent i = new Intent(this, cocinero1.class );
        startActivity(i);

    }
    public void adm(View v){
        Intent i = new Intent(this, ADM2.class );
        startActivity(i);
    }

}
