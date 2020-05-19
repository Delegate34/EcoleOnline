package com.example.ecoleenligne.vue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecoleenligne.BackgroundWorker;
import com.example.ecoleenligne.R;
import com.example.ecoleenligne.TaskCompleted;

public class ChildRegister extends Activity implements TaskCompleted {
    Button mode;
    Boolean validate;
    String nom, prenom, email, login, mdp, paiement, annee, date, cvc, num, niveau;
    AppCompatSpinner formation, formule, anneeScolaire, niveauScolaire;
    int formu, forma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.child_register );

        mode = findViewById( R.id.button1 );

        validate = true;

        mode.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recupDonnees();
                checkDonnee();

                if(validate){
                    BackgroundWorker backgroundWorker = new BackgroundWorker(ChildRegister.this);
                    backgroundWorker.execute( "enregeleve", nom, prenom, email, login, mdp, paiement,
                            formu,forma,annee,niveau);

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
        formation = (AppCompatSpinner) findViewById( R.id.formation );
        forma = (int) formation.getSelectedItemPosition() - 1;
        formule = (AppCompatSpinner) findViewById( R.id.formule );
        formu = (int) formule.getSelectedItemPosition();
        niveauScolaire = (AppCompatSpinner) findViewById( R.id.niv );
        niveau = (String) niveauScolaire.getSelectedItem();

        EditText nb = (EditText) findViewById( R.id.cardNumberEditText );
        EditText dat = (EditText) findViewById( R.id.cardDateEditText );
        EditText cc = (EditText) findViewById( R.id.cardCVCEditText );
        num =  nb.getText().toString();
        date = dat.getText().toString();
        cvc = cc.getText().toString();
        paiement = num+"/"+date+"/"+cvc;
    }

    private void checkDonnee(){
        if(nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || login.isEmpty() || mdp.isEmpty()){
            Toast.makeText(ChildRegister.this,
                    "Veuillez completer tout les champs de l'eleve.", Toast.LENGTH_LONG).show();
            validate = false;
        }
        if(formu == 0 || forma == -1 || annee.equals("Annee Scolaire") || niveau.equals( "Niveau Scolaire" )){
            Toast.makeText(ChildRegister.this,
                    "Veuillez correctement selectionner un element des spinners de l'eleve.", Toast.LENGTH_LONG).show();
            validate = false;
        }
        if(num.isEmpty() || date.isEmpty() || cvc.isEmpty()){
            Toast.makeText(ChildRegister.this,
                    "Veuillez completer toutes les infos de votre carte bancaire.", Toast.LENGTH_LONG).show();
            validate = false;
        }
    }

    @Override
    public void onTaskComplete(String result) {
        if (result.isEmpty()) {
            Toast.makeText( ChildRegister.this,
                    "Echec Lors de l'inscription", Toast.LENGTH_LONG ).show();
            return;
        }
        if (result.contains( "SUCCES" )) {
            Toast.makeText( ChildRegister.this,
                    "L'inscription s'est bien déroulée", Toast.LENGTH_LONG ).show();
            Intent change = new Intent( ChildRegister.this, MainActivity.class );
            startActivity( change );
        }
        String[] parts = result.split( ":" );
        if (parts.length > 1) {
            String error = parts[0];
            String message = parts[1];
            System.out.println( "************" + message );
            if (error.equals( "Error" )) {
                Toast.makeText( ChildRegister.this,
                        "Erreur :" + message, Toast.LENGTH_LONG ).show();
            }
        }
        Toast.makeText( ChildRegister.this,
                "Echec Lors de l'inscription", Toast.LENGTH_LONG ).show();
    }
}