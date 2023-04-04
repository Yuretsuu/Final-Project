package com.cst2335.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cst2335.final_project.fragment.FavouriteFragment;
import com.cst2335.final_project.R;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar2));

        getSupportActionBar().setTitle(
                getResources().getString(R.string.favorite) +
                " " +
                getResources().getString(R.string.loveleen));

        //load favopurite fragment that will fetch cocktails from local database and show in
        // recyclervview
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout, new FavouriteFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favorite_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_help:
                // help button presssed
                showhelpDialog();
                return true;
        }
        return false;
    }


    private void showhelpDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.help));
        builder.setMessage(getResources().getString(R.string.help_desc));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }


}