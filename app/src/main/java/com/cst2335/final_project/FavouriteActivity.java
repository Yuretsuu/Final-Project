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
import com.cst2335.final_project.fragment.ToolbarFragment;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load favourite fragment that will fetch cocktails from local database and show in
        // recyclerview
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.nav_host_fragment_content_main, new FavouriteFragment())
                .add(R.id.toolbar, new ToolbarFragment())
                .commit();
    }

    private void showhelpDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.help));
        builder.setMessage(getResources().getString(R.string.help_desc));
        builder.setPositiveButton(getResources().getString(R.string.yes), (dialog, which) -> dialog.dismiss());
        builder.create().show();

    }


}