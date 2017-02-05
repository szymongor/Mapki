package com.example.karolinaszymon.mapki;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Szymon on 05.02.2017.
 */

public class ApiTask extends AsyncTask<URL,Integer,String> {

    Context context;
    HashMap<String,String> parameters;
    CookieManager cookieManager;
    Class moveTo;
    GmapFragment gmap;

    public ApiTask(HashMap<String,String> parameters, Context context, CookieManager cookieManager, Class moveTo){
        this.parameters = parameters;
        this.context = context;
        this.cookieManager = cookieManager;
        this.moveTo = moveTo;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public ApiTask(GmapFragment gmap, CookieManager cookieManager, Class moveTo){
        this.parameters = new HashMap<String, String>();
        this.gmap = gmap;
        this.context = gmap.getContext();
        this.cookieManager = cookieManager;
        this.moveTo = moveTo;
    }

    @Override
    protected String doInBackground(URL... params) {
        URL url = null;
        String response = null;
        HttpURLConnection urlConnection = null;
        try {
            CookieHandler.setDefault(cookieManager);
            url = params[0];
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder().appendQueryParameter("", "");

            for (Map.Entry<String, String> entry:parameters.entrySet()) {
                builder.appendQueryParameter(entry.getKey(), entry.getValue());
            }
            String query = builder.build().getEncodedQuery();

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            urlConnection.connect();
            InputStream in = urlConnection.getInputStream();
            response = readStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }
        return response;
    }

    protected void onPostExecute(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.names().get(0).equals("success")){

                Toast.makeText(context,
                        "Success: "+jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                if(jsonObject.names().length() == 2 && jsonObject.names().get(1).equals("locations") && gmap != null){
                    gmap.showLocation(23.3,32.0,"Dziala cos tam");
                }
                else if(moveTo!= null){
                    Intent startActivity = new Intent();
                    startActivity.setClass(context, moveTo);
                    startActivity.setAction(moveTo.getName());
                    startActivity.setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    context.startActivity(startActivity);
                }

            }
            else{
                Toast.makeText(context,
                        "Error: "+jsonObject.getString("error"),Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}
