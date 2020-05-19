package com.example.ecoleenligne.vue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ecoleenligne.BackgroundWorker;
import com.example.ecoleenligne.R;
import com.example.ecoleenligne.TaskCompleted;
//import com.example.ecoleenligne.controleur.Controle;

public class ParentRegister extends Activity implements TaskCompleted {
    Button mode;
    //Controle cp;
    String nom, prenom, email, login, mdp, paiement, annee, date, cvc, num;
    AppCompatSpinner nbEnfant, formation, formule, anneeScolaire;
    int formu, forma, nbChilds;
    LinearLayout l1, l2, l3;
    Boolean validate;
    int[] tab;
    String[][] mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.parent_register );

        //cp = Controle.getInstance();
        mode = findViewById( R.id.button1 );

        nbEnfant = (AppCompatSpinner) findViewById( R.id.childs );
        nbEnfant.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                switch (position){
                    case 3:
                        l1 = (LinearLayout) findViewById( R.id.layout0 );
                        l1.setVisibility( View.VISIBLE );
                        l2 = (LinearLayout) findViewById( R.id.layout1 );
                        l2.setVisibility( View.VISIBLE );
                        l3 = (LinearLayout) findViewById( R.id.layout2 );
                        l3.setVisibility( View.VISIBLE );
                        break;
                    case 2:
                        l1 = (LinearLayout) findViewById( R.id.layout0 );
                        l1.setVisibility( View.VISIBLE );
                        l2 = (LinearLayout) findViewById( R.id.layout1 );
                        l2.setVisibility( View.VISIBLE );
                        l3 = (LinearLayout) findViewById( R.id.layout2 );
                        l3.setVisibility( View.GONE );
                        break;
                    case 1:
                        l1 = (LinearLayout) findViewById( R.id.layout0 );
                        l1.setVisibility( View.VISIBLE );
                        l2 = (LinearLayout) findViewById( R.id.layout1 );
                        l2.setVisibility( View.GONE );
                        l3 = (LinearLayout) findViewById( R.id.layout2 );
                        l3.setVisibility( View.GONE );
                        break;
                    case 0:
                        l1 = (LinearLayout) findViewById( R.id.layout0 );
                        l1.setVisibility( View.GONE );
                        l2 = (LinearLayout) findViewById( R.id.layout1 );
                        l2.setVisibility( View.GONE );
                        l3 = (LinearLayout) findViewById( R.id.layout2 );
                        l3.setVisibility( View.GONE );
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        } );

        validate = true;

        /*boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService( Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        Toast.makeText( this, "***************"+connected, Toast.LENGTH_LONG ).show();
*/
        mode.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recupDonnees();
                checkDonnee();
                //cp.creerParent( nom, prenom, email, login, mdp, "123" );
                if(validate){
                    BackgroundWorker backgroundWorker = new BackgroundWorker(ParentRegister.this);
                    backgroundWorker.execute( "enregparent", nom, prenom, email, login, mdp, paiement,
                            nbChilds,formu,forma,annee,mat,tab);

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

        nbEnfant = (AppCompatSpinner) findViewById( R.id.childs );
        nbChilds = (int) nbEnfant.getSelectedItemPosition();
        anneeScolaire = (AppCompatSpinner) findViewById( R.id.date );
        annee = (String) anneeScolaire.getSelectedItem();
        formation = (AppCompatSpinner) findViewById( R.id.formation );
        forma = (int) formation.getSelectedItemPosition() - 1;
        formule = (AppCompatSpinner) findViewById( R.id.formule );
        formu = (int) formule.getSelectedItemPosition();

        if(nbChilds != 0) {
            tab = new int[nbChilds];
            mat = new String[nbChilds][3];
            switch (nbChilds) {
                case 3:
                    EditText ed4 = (EditText) findViewById( R.id.ed4 );
                    EditText ed5 = (EditText) findViewById( R.id.ed5 );
                    AppCompatSpinner lien2 = (AppCompatSpinner) findViewById( R.id.lien2 );
                    AppCompatSpinner niv2 = (AppCompatSpinner) findViewById( R.id.niv2 );
                    tab[2] = lien2.getSelectedItemPosition() - 1;
                    mat[2][2] = (String) niv2.getSelectedItem();
                    mat[2][0] = ed4.getText().toString();
                    mat[2][1] = ed5.getText().toString();
                case 2:
                    EditText ed3 = (EditText) findViewById( R.id.ed3 );
                    EditText ed2 = (EditText) findViewById( R.id.ed2 );
                    AppCompatSpinner lien1 = (AppCompatSpinner) findViewById( R.id.lien1 );
                    AppCompatSpinner niv1 = (AppCompatSpinner) findViewById( R.id.niv1 );
                    tab[1] = lien1.getSelectedItemPosition() - 1;
                    mat[1][2] = (String) niv1.getSelectedItem();
                    mat[1][0] = ed2.getText().toString();
                    mat[1][1] = ed3.getText().toString();
                case 1:
                    EditText ed0 = (EditText) findViewById( R.id.ed0 );
                    EditText ed1 = (EditText) findViewById( R.id.ed1 );
                    AppCompatSpinner lien0 = (AppCompatSpinner) findViewById( R.id.lien0 );
                    AppCompatSpinner niv0 = (AppCompatSpinner) findViewById( R.id.niv0 );
                    tab[0] = lien0.getSelectedItemPosition() - 1;
                    mat[0][2] = (String) niv0.getSelectedItem();
                    mat[0][0] = ed0.getText().toString();
                    mat[0][1] = ed1.getText().toString();
            }
        }else {
            Toast.makeText(ParentRegister.this,
                    "Veuillez choisir le nombre d'enfants.", Toast.LENGTH_LONG).show();
            validate = false;
        }
        EditText nb = (EditText) findViewById( R.id.cardNumberEditText );
        EditText dat = (EditText) findViewById( R.id.cardDateEditText );
        EditText cc = (EditText) findViewById( R.id.cardCVCEditText );
        num =  nb.getText().toString();
        date = dat.getText().toString();
        cvc = cc.getText().toString();
        paiement = num+"/"+date+"/"+cvc;
    }

    /*protected void sendEmail() {
        email = "darkwolf4521@gmail.com";
        String[] TO = {email};
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        //intent.setType("text/plain");
        emailIntent.setDataAndType( Uri.parse("mailto:"), "text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inscription EcoleEnLigne");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Votre inscription c'est bien faite.");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Toast.makeText(ParentRegister.this,
                    "Un mail vous a ete envoyé.", Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ParentRegister.this,
                    "Erreur d'envoie du mail.", Toast.LENGTH_SHORT).show();
        }
    }*/

    private void checkDonnee(){
        if(nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || login.isEmpty() || mdp.isEmpty()){
            Toast.makeText(ParentRegister.this,
                    "Veuillez completer tout les champs du parent.", Toast.LENGTH_LONG).show();
            validate = false;
        }
        if(formu == 0 || forma == -1 || annee.equals("Annee Scolaire")){
            Toast.makeText(ParentRegister.this,
                    "Veuillez correctement selectionner un element des spinners du parent.", Toast.LENGTH_LONG).show();
            validate = false;
        }
        for (int i = 0;i<nbChilds;i++){
            if(mat[i][0].isEmpty() || mat[i][1].isEmpty() || mat[i][2].equals( "Niveau Scolaire" )
            || tab[i] == -1){
                Toast.makeText(ParentRegister.this,
                        "Veuillez correctement completer tout les champs de l'enfant n°"+i, Toast.LENGTH_LONG).show();
                validate = false;
            }
        }
        if(num.isEmpty() || date.isEmpty() || cvc.isEmpty()){
            Toast.makeText(ParentRegister.this,
                    "Veuillez completer toutes les infos de votre carte bancaire.", Toast.LENGTH_LONG).show();
            validate = false;
        }
    }

    @Override
    public void onTaskComplete(String result) {
        if(result.isEmpty()){
            Toast.makeText(ParentRegister.this,
                    "Echec Lors de l'inscription", Toast.LENGTH_LONG).show();
            return;
        }
        if (result.contains( "SUCCES" )) {
            Toast.makeText( ParentRegister.this,
                    "L'inscription s'est bien déroulée", Toast.LENGTH_LONG ).show();
            Intent change = new Intent( ParentRegister.this, MainActivity.class );
            startActivity( change );
        }
        String[] parts = result.split(":");
        if(parts.length > 1) {
            String error = parts[0];
            String message = parts[1];
            System.out.println( "************" + message );
            if (error.equals( "Error" )) {
                Toast.makeText( ParentRegister.this,
                        "Erreur :" + message, Toast.LENGTH_LONG ).show();
            }
        }
        Toast.makeText(ParentRegister.this,
                "Echec Lors de l'inscription", Toast.LENGTH_LONG).show();
    }
}