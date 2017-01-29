package com.example.karolinaszymon.mapki;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class GameLayout extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_layout);
        initUIComponents();

    }

    private void initUIComponents(){
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_game_layout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        android.app.FragmentManager fm = getFragmentManager();

        int id = item.getItemId();

        if(id == R.id.nav_map){
            fm.beginTransaction().replace(R.id.content_frame, new GmapFragment()).commit();
        }
        if(id == R.id.nav_account){

        }
        if(id == R.id.nav_logout){
            Toast.makeText(getApplicationContext(),"Bye", Toast.LENGTH_LONG).show();
            finish();
        }

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
