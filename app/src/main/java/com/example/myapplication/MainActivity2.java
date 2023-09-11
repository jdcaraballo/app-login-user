package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    TextView lblWellcome;
    public static final String nombres = "names";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lblWellcome= (TextView) findViewById(R.id.textWellcome);
        String usuario = getIntent().getStringExtra("names");
        lblWellcome.setText("¡Bienvenido "+usuario+"!");
    }
}