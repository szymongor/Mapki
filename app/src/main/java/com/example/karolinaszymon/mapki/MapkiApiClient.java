package com.example.karolinaszymon.mapki;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Szymon on 05.02.2017.
 */

public class MapkiApiClient{
    static final String URLLOGIN = "http://szymgor.ayz.pl/Mapki/MapkiApi.php/login";
    static MapkiApiClient mapkiApiClient;
    static ApiTask apiTask = null;

    private MapkiApiClient(){

    }


    public static void login(String login, String password, Context context){
        apiTask = new ApiTask(login, password, context);
        URL url = null;
        try {
            url = new URL(URLLOGIN);
            apiTask.execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
