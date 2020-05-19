package com.example.ecoleenligne.vue;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecoleenligne.BackgroundWorker;
import com.example.ecoleenligne.R;
import com.example.ecoleenligne.TaskCompleted;

public class Connexion extends Activity implements TaskCompleted {
    Button cnx;
    String login, mdp;
    Boolean validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.connexion );

        cnx = findViewById( R.id.button1 );
        validate = true;

        cnx.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recupDonnees();
                checkDonnee();
                if(validate){
                    BackgroundWorker backgroundWorker = new BackgroundWorker(Connexion.this);
                    backgroundWorker.execute( "connexion", login, mdp);

                }
            }
        } );
    }

    private void recupDonnees() {
        EditText l = (EditText) findViewById( R.id.editText );
        EditText m = (EditText) findViewById( R.id.editText1 );

        login = l.getText().toString();
        mdp = m.getText().toString();
    }

    private void checkDonnee() {
        if (login.isEmpty() || mdp.isEmpty()) {
            Toast.makeText( Connexion.this,
                    "Veuillez completer tout les champs.", Toast.LENGTH_LONG ).show();
            validate = false;
        }
    }

    @Override
    public void onTaskComplete(String result) {
        if(result.isEmpty()){
            Toast.makeText(Connexion.this,
                    "Echec Lors de la connexion", Toast.LENGTH_LONG).show();
            return;
        }
            String[] parts = result.split(":");
            if(parts.length > 1) {
                String error = parts[0];
                String donnee = parts[1];
                System.out.println( "************" + donnee );
                if (error.equals( "Error" )) {
                    Toast.makeText( Connexion.this,
                            "Erreur :" + donnee, Toast.LENGTH_LONG ).show();
                }else if (error.equals( "SUCCES" )) {
                    String[] data = donnee.split("/");
                    String type = parts[0];
                    String id = parts[1];
                    String nom = parts[2];
                    String prenom = parts[3];
                    String email = parts[4];
                    String login = parts[5];
                    switch (type) {
                        case "parent": {
                            Toast.makeText( Connexion.this,
                                    "La connexion s'est bien déroulée", Toast.LENGTH_LONG ).show();
                            Intent change = new Intent( Connexion.this, ParentMain.class );
                            change.putExtra("id", id);
                            change.putExtra("nom", nom);
                            change.putExtra("prenom", prenom);
                            change.putExtra("email", email);
                            change.putExtra("login", login);
                            startActivity( change );
                            break;
                        }
                        case "eleve": {
                            Toast.makeText( Connexion.this,
                                    "La connexion s'est bien déroulée", Toast.LENGTH_LONG ).show();
                            Intent change = new Intent( Connexion.this, ChildMain.class );
                            change.putExtra("id", id);
                            change.putExtra("nom", nom);
                            change.putExtra("prenom", prenom);
                            change.putExtra("email", email);
                            change.putExtra("login", login);
                            startActivity( change );
                            break;
                        }
                        case "prof": {
                            Toast.makeText( Connexion.this,
                                    "La connexion s'est bien déroulée", Toast.LENGTH_LONG ).show();
                            Intent change = new Intent( Connexion.this, MainActivity.class );
                            change.putExtra("id", id);
                            change.putExtra("nom", nom);
                            change.putExtra("prenom", prenom);
                            change.putExtra("email", email);
                            change.putExtra("login", login);
                            startActivity( change );
                            break;
                        }
                    }
                }
            }else{
                Toast.makeText(Connexion.this,
                        "Echec Lors de la connexion", Toast.LENGTH_LONG).show();
            }

        Toast.makeText(Connexion.this,
                "Echec Lors de l'inscription", Toast.LENGTH_LONG).show();
    }
}