package com.example.karolinaszymon.mapki;

/**
 * Created by Szymon on 05.02.2017.
 */

public class MapkiApiClient {
    private static String login;
    private static String password;

    public static void setCredentials(String login, String password){
        MapkiApiClient.login = login;
        MapkiApiClient.password = password;
    }



}
