package com.example.ecoleenligne.vue;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecoleenligne.BackgroundWorker;
import com.example.ecoleenligne.R;
import com.example.ecoleenligne.TaskCompleted;

public class TeacherRegister extends Activity implements TaskCompleted {
    Button mode;
    Boolean validate;
    String nom, prenom, email, login, mdp, annee, niveau;
    AppCompatSpinner anneeScolaire, niveauScolaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.teacher_register );

        mode = findViewById( R.id.button1 );

        validate = true;

        mode.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recupDonnees();
                checkDonnee();

                if(validate){
                    BackgroundWorker backgroundWorker = new BackgroundWorker(TeacherRegister.this);
                    backgroundWorker.execute( "enregprof", nom, prenom, email, login, mdp,annee,niveau);
                }
            }
        } );
    }
    private void recupDonnees(){
        EditText n = (EditText) findViewById( R.id.editText );
        EditText p = (EditText) findViewById( R.id.editText1 );
        EditText e = (EditText) findViewById( R.id.editText2 );
        EditText l = (EditText) findViewById( R.id.editText3 );
        EditText m = (EditText) findViewById( R.id.editText4 );

        nom = n.getText().toString();
        prenom = p.getText().toString();
        email = e.getText().toString();
        login = l.getText().toString();
        mdp = m.getText().toString();

        anneeScolaire = (AppCompatSpinner) findViewById( R.id.date );
        annee = (String) anneeScolaire.getSelectedItem();
        niveauScolaire = (AppCompatSpinner) findViewById( R.id.niv );
        niveau = (String) niveauScolaire.getSelectedItem();
    }

    private void checkDonnee(){
        if(nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || login.isEmpty() || mdp.isEmpty()){
            Toast.makeText(TeacherRegister.this,
                    "Veuillez completer tout les champs du professeur.", Toast.LENGTH_LONG).show();
            validate = false;
        }
        if(annee.equals("Annee Scolaire") || niveau.equals( "Niveau Scolaire" )){
            Toast.makeText(TeacherRegister.this,
                    "Veuillez correctement selectionner un element des spinners du professeur.", Toast.LENGTH_LONG).show();
            validate = false;
        }
    }

    @Override
    public void onTaskComplete(String result) {
        if (result.isEmpty()) {
            Toast.makeText( TeacherRegister.this,
                    "Echec Lors de l'inscription", Toast.LENGTH_LONG ).show();
            return;
        }
        if (result.contains( "SUCCES" )) {
            Toast.makeText( TeacherRegister.this,
                    "L'inscription s'est bien déroulée", Toast.LENGTH_LONG ).show();
            Intent change = new Intent( TeacherRegister.this, MainActivity.class );
            startActivity( change );
        }
        String[] parts = result.split( ":" );
        if (parts.length > 1) {
            String error = parts[0];
            String message = parts[1];
            System.out.println( "************" + message );
            if (error.equals( "Error" )) {
                Toast.makeText( TeacherRegister.this,
                        "Erreur :" + message, Toast.LENGTH_LONG ).show();
            }
        }
        Toast.makeText( TeacherRegister.this,
                "Echec Lors de l'inscription", Toast.LENGTH_LONG ).show();
    }
}