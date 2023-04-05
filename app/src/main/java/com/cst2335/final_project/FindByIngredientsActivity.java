package com.cst2335.final_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cst2335.final_project.fragment.HomeFragmentForSearchByIngredients;
import com.cst2335.final_project.fragment.ToolbarFragment;

public class FindByIngredientsActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.nav_host_fragment_content_main, new HomeFragmentForSearchByIngredients())
                    .add(R.id.toolbar, new ToolbarFragment())
                    .commit();
        }

}
