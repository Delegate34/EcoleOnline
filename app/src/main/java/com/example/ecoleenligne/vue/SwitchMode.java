package com.example.ecoleenligne.vue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecoleenligne.R;

public class SwitchMode extends Activity {
    Button connexion,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.content_main );

        connexion = findViewById( R.id.buttonconnexion );
        register = findViewById( R.id.buttonregister );

        connexion.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent connexion = new Intent(SwitchMode.this,Connexion.class);
                startActivity(connexion);
            }
        } );

        register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(SwitchMode.this,TypeAccount.class);
                startActivity(register);
            }
        } );
    }
}
