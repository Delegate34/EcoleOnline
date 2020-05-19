package com.example.ecoleenligne.vue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecoleenligne.R;

public class MainActivity extends Activity {
    Button mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mode = findViewById( R.id.buttononline );

        mode.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(MainActivity.this,SwitchMode.class);
                startActivity(change);
            }
        } );
    }
}

