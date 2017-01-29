package com.example.karolinaszymon.mapki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button loginBTN;
    Button registerBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUIComponents();
    }

    private void initUIComponents(){
        loginBTN = (Button) findViewById(R.id.loginBTN);
        loginBTN.setOnClickListener(clickLoginBTN);

        registerBTN = (Button) findViewById(R.id.registerBTN);
    }

    private View.OnClickListener clickLoginBTN = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,Navigation.class);
            startActivity(intent);
        }
    };
}
