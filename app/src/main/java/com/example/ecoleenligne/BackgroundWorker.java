package com.example.ecoleenligne;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.text.style.TabStopSpan;
import android.widget.Toast;

//import com.example.ecoleenligne.modele.Parent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BackgroundWorker extends AsyncTask<Object,Void,String> {
    private Context mContext;
    private TaskCompleted mCallback;

    public BackgroundWorker(Context c){
        this.mContext = c;
        this.mCallback = (TaskCompleted) c;
    }

    @Override
    protected String doInBackground(Object... strings) {
        String type = (String) strings[0];
        String server_url = "Votre adresse ip+path du serveur";
        switch (type) {
            case "enregparent": {
                String data = "";
                try {
                    URL url = new URL( server_url );
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod( "POST" );
                    httpURLConnection.setDoOutput( true );
                    httpURLConnection.setDoInput( true );

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter( outputStream, "UTF-8" ) );

                    int nbChild = (int) strings[7];
                    String[][] eleves = (String[][]) strings[11];
                    int[] lien = (int[]) strings[12];
                    data = URLEncoder.encode( "operation", "UTF-8" ) + "=" + URLEncoder.encode( "enregparent", "UTF-8" ) + "&"
                            + URLEncoder.encode( "nom", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[1], "UTF-8" ) + "&"
                            + URLEncoder.encode( "prenom", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[2], "UTF-8" ) + "&"
                            + URLEncoder.encode( "email", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[3], "UTF-8" ) + "&"
                            + URLEncoder.encode( "login", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[4], "UTF-8" ) + "&"
                            + URLEncoder.encode( "mdp", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[5], "UTF-8" ) + "&"
                            + URLEncoder.encode( "moyenpaie", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[6], "UTF-8" ) + "&"
                            + URLEncoder.encode( "nbChild", "UTF-8" ) + "=" + URLEncoder.encode( Integer.toString( (int) strings[7] ), "UTF-8" ) + "&"
                            + URLEncoder.encode( "formule", "UTF-8" ) + "=" + URLEncoder.encode( Integer.toString( (int) strings[8] ), "UTF-8" ) + "&"
                            + URLEncoder.encode( "formation", "UTF-8" ) + "=" + URLEncoder.encode( Integer.toString( (int) strings[9] ), "UTF-8" ) + "&"
                            + URLEncoder.encode( "annee", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[10], "UTF-8" ) + "&";

                    for (int i = 0; i < nbChild; i++) {
                        data += URLEncoder.encode( "nom" + i, "UTF-8" ) + "=" + URLEncoder.encode( eleves[i][0], "UTF-8" ) + "&"
                                + URLEncoder.encode( "prenom" + i, "UTF-8" ) + "=" + URLEncoder.encode( eleves[i][1], "UTF-8" ) + "&"
                                + URLEncoder.encode( "lien" + i, "UTF-8" ) + "=" + URLEncoder.encode( eleves[i][2], "UTF-8" ) + "&"
                                + URLEncoder.encode( "niveau" + i, "UTF-8" ) + "=" + URLEncoder.encode( Integer.toString( lien[i] ), "UTF-8" );
                    }

                    bufferedWriter.write( data );
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream, StandardCharsets.ISO_8859_1 ) );
                    String result = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    System.out.println( "******************" + result );
                    return result;
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "enregeleve": {
                String data = "";
                try {
                    URL url = new URL( server_url );
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod( "POST" );
                    httpURLConnection.setDoOutput( true );
                    httpURLConnection.setDoInput( true );

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter( outputStream, "UTF-8" ) );

                    data = URLEncoder.encode( "operation", "UTF-8" ) + "=" + URLEncoder.encode( "enregeleve", "UTF-8" ) + "&"
                            + URLEncoder.encode( "nom", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[1], "UTF-8" ) + "&"
                            + URLEncoder.encode( "prenom", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[2], "UTF-8" ) + "&"
                            + URLEncoder.encode( "email", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[3], "UTF-8" ) + "&"
                            + URLEncoder.encode( "login", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[4], "UTF-8" ) + "&"
                            + URLEncoder.encode( "mdp", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[5], "UTF-8" ) + "&"
                            + URLEncoder.encode( "moyenpaie", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[6], "UTF-8" ) + "&"
                            + URLEncoder.encode( "formule", "UTF-8" ) + "=" + URLEncoder.encode( Integer.toString( (int) strings[7] ), "UTF-8" ) + "&"
                            + URLEncoder.encode( "formation", "UTF-8" ) + "=" + URLEncoder.encode( Integer.toString( (int) strings[8] ), "UTF-8" ) + "&"
                            + URLEncoder.encode( "annee", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[9], "UTF-8" ) + "&"
                            + URLEncoder.encode( "niveau", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[10], "UTF-8" );

                    bufferedWriter.write( data );
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream, StandardCharsets.ISO_8859_1 ) );
                    String result = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    System.out.println( "******************" + result );
                    return result;
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "enregprof": {
                String data = "";
                try {
                    URL url = new URL( server_url );
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod( "POST" );
                    httpURLConnection.setDoOutput( true );
                    httpURLConnection.setDoInput( true );

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter( outputStream, "UTF-8" ) );

                    data = URLEncoder.encode( "operation", "UTF-8" ) + "=" + URLEncoder.encode( "enregprof", "UTF-8" ) + "&"
                            + URLEncoder.encode( "nom", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[1], "UTF-8" ) + "&"
                            + URLEncoder.encode( "prenom", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[2], "UTF-8" ) + "&"
                            + URLEncoder.encode( "email", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[3], "UTF-8" ) + "&"
                            + URLEncoder.encode( "login", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[4], "UTF-8" ) + "&"
                            + URLEncoder.encode( "mdp", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[5], "UTF-8" ) + "&"
                            + URLEncoder.encode( "annee", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[6], "UTF-8" ) + "&"
                            + URLEncoder.encode( "niveau", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[7], "UTF-8" );

                    bufferedWriter.write( data );
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream, StandardCharsets.ISO_8859_1 ) );
                    String result = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    System.out.println( "******************" + result );
                    return result;
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "connexion": {
                String data = "";
                try {
                    URL url = new URL( server_url );
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod( "POST" );
                    httpURLConnection.setDoOutput( true );
                    httpURLConnection.setDoInput( true );

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter( outputStream, "UTF-8" ) );

                    data = URLEncoder.encode( "operation", "UTF-8" ) + "=" + URLEncoder.encode( "connexion", "UTF-8" ) + "&"
                            + URLEncoder.encode( "login", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[1], "UTF-8" ) + "&"
                            + URLEncoder.encode( "mdp", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[2], "UTF-8" );

                    bufferedWriter.write( data );
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream, StandardCharsets.ISO_8859_1 ) );
                    String result = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    System.out.println( "******************" + result );
                    return result;
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "getCours": {
                String data = "";
                try {
                    URL url = new URL( server_url );
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod( "POST" );
                    httpURLConnection.setDoOutput( true );
                    httpURLConnection.setDoInput( true );

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter( outputStream, "UTF-8" ) );

                    data = URLEncoder.encode( "operation", "UTF-8" ) + "=" + URLEncoder.encode( "getCours", "UTF-8" ) + "&"
                            + URLEncoder.encode( "id", "UTF-8" ) + "=" + URLEncoder.encode( (String) strings[1], "UTF-8" );

                    bufferedWriter.write( data );
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream, StandardCharsets.ISO_8859_1 ) );
                    String result = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    System.out.println( "******************" + result );
                    return result;
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        mCallback.onTaskComplete( result );
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate( values );
    }
}
