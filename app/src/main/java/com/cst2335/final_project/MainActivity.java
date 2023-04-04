package com.cst2335.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;

import com.cst2335.final_project.FavouriteActivity;
import com.cst2335.final_project.fragment.HomeFragment;
import com.example.cocktaildatabase_loveleen.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/***
 * Author :- Loveleen Kaur
 * 28/03/2023
 * this is a Dashboard class which is the main class from all other activities
 * will be accessed
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.nav_host_fragment_content_main, new HomeFragment())
                .commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            // favourite icon selected
            case R.id.action_favorites:
                // move to favourite activity
                startActivity(new Intent(this, FavouriteActivity.class));
                return true;
        }
        return false;
    }

}