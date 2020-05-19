package com.example.ecoleenligne.vue;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecoleenligne.R;

public class ChildMain extends Activity {
    Button course;
    String id, nom, prenom, email, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.student_main );

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id = extras.getString("id");
            nom = extras.getString("nom");
            prenom = extras.getString("prenom");
            email = extras.getString("email");
            login = extras.getString("login");
        }

        course = (Button) findViewById( R.id.buttoncourse );
        course.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent( ChildMain.this, Course.class );
                change.putExtra("id", id);
                startActivity( change );
            }
        } );
    }
}