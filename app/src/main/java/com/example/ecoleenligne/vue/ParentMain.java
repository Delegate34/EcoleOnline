package com.example.ecoleenligne.vue;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecoleenligne.R;

public class ParentMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.parent_main );

        String id, nom, prenom, email, login;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id = extras.getString("id");
            nom = extras.getString("nom");
            prenom = extras.getString("prenom");
            email = extras.getString("email");
            login = extras.getString("login");
        }

    }
}