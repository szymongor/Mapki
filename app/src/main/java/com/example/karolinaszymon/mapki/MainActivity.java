package com.example.karolinaszymon.mapki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static final String URL = "http://szymgor.ayz.pl/Mapki/MapkiApi.php/login";

    Button loginBTN;
    Button registerBTN;
    EditText loginET;
    EditText passwordET;

    RequestQueue requestQueue;
    StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUIComponents();

        requestQueue = Volley.newRequestQueue(this);
    }

    private void initUIComponents(){
        loginBTN = (Button) findViewById(R.id.loginBTN);
        loginBTN.setOnClickListener(clickLoginBTN);

        loginET = (EditText) findViewById(R.id.loginET);
        passwordET = (EditText) findViewById(R.id.passwordET);


        registerBTN = (Button) findViewById(R.id.registerBTN);
    }

    private View.OnClickListener clickLoginBTN = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            request = new StringRequest(Request.Method.POST,URL,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.names().get(0).equals("success")){
                                    Toast.makeText(getApplicationContext(),
                                            "Success. "+jsonObject.getString("success"),Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(MainActivity.this,Navigation.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),
                                            "Error "+jsonObject.getString("error"),Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {e.printStackTrace();

                            }
                        }
            },
                    new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap = new HashMap<String,String>();
                    hashMap.put("login",loginET.getText().toString());
                    hashMap.put("password",passwordET.getText().toString());
                    return hashMap;
                }
            };

            requestQueue.add(request);

        }
    };
}
