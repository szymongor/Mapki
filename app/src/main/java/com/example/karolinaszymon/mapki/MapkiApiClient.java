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
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Szymon on 05.02.2017.
 */

public class MapkiApiClient{
    static final String URLLOGIN = "http://szymgor.ayz.pl/Mapki/MapkiApi.php/login";
    static final String URLLOGOUT = "http://szymgor.ayz.pl/Mapki/MapkiApi.php/logout";
    static final String URLADDLOCATION = "http://szymgor.ayz.pl/Mapki/MapkiApi.php/addLocation";
    private final boolean isLoggedOn = false;

    static MapkiApiClient mapkiApiClient;
    static ApiTask apiTask = null;
    static CookieManager cookieManager = new CookieManager();

    private MapkiApiClient(){

    }


    public static void login(String login, String password, Context context, Class moveTo){
        HashMap params = new HashMap<String, String>();
        params.put("login",login );
        params.put("password",password );
        apiTask = new ApiTask(params, context, cookieManager, moveTo);
        URL url = null;
        try {
            url = new URL(URLLOGIN);
            apiTask.execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void logout(Context context, Class moveTo){
        HashMap params = new HashMap<String, String>();
        apiTask = new ApiTask(params, context, cookieManager, moveTo);
        URL url = null;
        try {
            url = new URL(URLLOGOUT);
            apiTask.execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public static void addLocation(Context context, Double coord_x, Double coord_y, String description, boolean privateLoc){
        HashMap params = new HashMap<String, String>();
        params.put("coord_x",coord_x.toString() );
        params.put("coord_y",coord_y.toString() );
        params.put("description",description );
        params.put("private", Boolean.toString(privateLoc) );
        apiTask = new ApiTask(params, context, cookieManager, null);
        URL url = null;
        try {
            url = new URL(URLADDLOCATION);
            apiTask.execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
