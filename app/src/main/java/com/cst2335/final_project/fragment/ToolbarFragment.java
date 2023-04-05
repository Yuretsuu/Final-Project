package com.cst2335.final_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cst2335.final_project.R;

public class ToolbarFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View toolbar = LayoutInflater.from(getContext()).inflate(R.layout.fragment_toolbar,container,false);
        toolbar.findViewById(R.id.my_favourite).setOnClickListener((view) -> {
            Log.wtf("", "Favourites");

            // TODO: launch FavouritesActivity
        });
        toolbar.findViewById(R.id.find_by_name_TextView).setOnClickListener((view) -> {
            Log.wtf("", "Find by name");
            // TODO: launch ActivityMain
        });
        toolbar.findViewById(R.id.search_by_ingredient_TextView).setOnClickListener((view) -> {
            Log.wtf("", "Find by ingredient");
            // TODO: launch FindByIngredientsActivity
        });
        return toolbar;
    }
}