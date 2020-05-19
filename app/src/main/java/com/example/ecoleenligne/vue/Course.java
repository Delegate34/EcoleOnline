package com.example.ecoleenligne.vue;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ecoleenligne.BackgroundWorker;
import com. example.ecoleenligne.R;
import com.example.ecoleenligne.TaskCompleted;

public class Course extends Activity implements TaskCompleted {
    ListView myList;
    String[] ListURL;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.course );

        myList = (ListView) findViewById( R.id.ListView );

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id = extras.getString("id");
        }

        if (id!=null) {
            BackgroundWorker backgroundWorker = new BackgroundWorker( Course.this );
            backgroundWorker.execute( "getCours", id );
        }

        myList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                Intent intent = new Intent(  );
                intent.setData( Uri.parse( ListURL[position] ) );
                startActivity( intent );
            }
        } );
        /*final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        WebView web_view = findViewById(R.id.web_view);
        web_view.requestFocus();
        web_view.getSettings().setJavaScriptEnabled(true);
        String myPdfUrl = "https://www.anacours.com/programmes-scolaires/Programme%20classe%20de%20CE2.pdf";
        //String url = "https://docs.google.com/viewer?embedded = true&url = "+myPdfUrl;
        web_view.loadUrl(myPdfUrl);
        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        web_view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });*/
        /*String path = "https://youtu.be/ADWaQrxZ5a0";

        Uri uri=Uri.parse(path);

        VideoView video=(VideoView)findViewById(R.id.video_view);
        video.setVideoURI(uri);
        video.start();*/
    }

    @Override
    public void onTaskComplete(String result) {
        if(result.isEmpty()){
            Toast.makeText(Course.this,
                    "Echec Lors de au Cours", Toast.LENGTH_LONG).show();
            return;
        }
        String[] parts = result.split(":");
        if(parts.length > 1) {
            String error = parts[0];
            String message = parts[1];
            System.out.println( "************" + message );
            if (error.equals( "Error" )) {
                Toast.makeText( Course.this,
                        "Erreur :" + message, Toast.LENGTH_LONG ).show();
            }else if (error.equals( "SUCCES" )){
                String[] cours = message.split("/");
                String[] ListNom = new String[cours.length];
                ListURL = new String[cours.length];
                String[] temp = null;
                for (int i = 0;i<cours.length;i++){
                    temp = cours[i].split( "-" );
                    ListNom[i] = temp[0];
                    ListURL[i] = temp[1];
                }
                ArrayAdapter<String> adapter = new ArrayAdapter <String>( getApplicationContext(),android.R.layout.simple_list_item_1,ListNom);
                myList.setAdapter( adapter );
            }
        }
        Toast.makeText(Course.this,
                "Echec Lors de l'inscription", Toast.LENGTH_LONG).show();
    }
}
