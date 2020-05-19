package com.example.ecoleenligne.vue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecoleenligne.R;

public class TypeAccount extends Activity {
    Button parent, child, teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.type_account );

        parent = findViewById( R.id.buttonparent );
        child = findViewById( R.id.buttonchild );
        teacher = findViewById( R.id.buttonteacher );

        parent.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(TypeAccount.this,ParentRegister.class);
                startActivity(change);
            }
        } );

        child.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(TypeAccount.this,ChildRegister.class);
                startActivity(change);
            }
        } );

        teacher.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(TypeAccount.this,TeacherRegister.class);
                startActivity(change);
            }
        } );
    }
}