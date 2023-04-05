package com.cst2335.final_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.cst2335.final_project.FavouriteActivity;
import com.cst2335.final_project.FindByIngredientsActivity;
import com.cst2335.final_project.MainActivity;
import com.cst2335.final_project.R;

public class ToolbarFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View toolbar = LayoutInflater.from(getContext()).inflate(R.layout.fragment_toolbar,container,false);
        toolbar.findViewById(R.id.my_favourite).setOnClickListener((view) -> {
            Log.wtf("", "Favourites");



            // TODO: launch FavouritesActivity
            Intent intent = new Intent(getContext(), FavouriteActivity.class);
            startActivity(intent);

        });
        toolbar.findViewById(R.id.find_by_name_TextView).setOnClickListener((view) -> {
            Log.wtf("", "Find by name");
            // TODO: launch ActivityMain
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);

        });
        toolbar.findViewById(R.id.search_by_ingredient_TextView).setOnClickListener((view) -> {
            Log.wtf("", "Find by ingredient");
            // TODO: launch FindByIngredientsActivity
            Intent intent = new Intent(getContext(), FindByIngredientsActivity.class);
            startActivity(intent);

        });
        // TODO: set Onclick on image view to display help message
        toolbar.findViewById(R.id.imageView).setOnClickListener((view) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Help");
            builder.setMessage(R.string.help_desc);
            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return toolbar;


    }


}