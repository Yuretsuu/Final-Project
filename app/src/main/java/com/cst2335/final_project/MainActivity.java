package com.cst2335.final_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.android.volley.toolbox.Volley;
import com.cst2335.final_project.databinding.ActivityMainBinding;

import java.util.List;

/**
 * This class is responsible for handling the search function after the user has created a profile.
 * @author Elizabeth Quach
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        // Instantiate the RequestQueue. Please refer to class CocktailAPIHelper for more information.
        CocktailAPIHelper apiHelper = new CocktailAPIHelper(this);

        mainBinding.mainSearchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            /**
             * @param s represents the text input that the user inputs in the search bar.
             */
            public boolean onQueryTextSubmit(String s) {
                //Create drinks List<Cocktail> and utilize helper method (searchByName())in CocktailAPIHelper
                List<Cocktail> drinks = apiHelper.searchByName(s);

                //Iterate through list of drink array of objects.
                for (Cocktail drink : drinks)

                    Log.i("Search", drink.drinkName);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


    }
}