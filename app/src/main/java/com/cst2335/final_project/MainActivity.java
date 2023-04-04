package com.cst2335.final_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cst2335.final_project.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for handling the search function after the user has created a profile.
 * @author Elizabeth Quach
 */
public class MainActivity extends AppCompatActivity {
    List<Cocktail> drinks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

//        // Instantiate the RequestQueue. Please refer to class CocktailAPIHelper for more information.
//        CocktailAPIHelper apiHelper = new CocktailAPIHelper(this);
//
//        CocktailAdapter adapter = new CocktailAdapter(this, drinks);
//        mainBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
//        mainBinding.recycler.setAdapter(adapter);
//
//        mainBinding.mainSearchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            //s represents the text input that the user inputs in the search bar
//            public boolean onQueryTextSubmit(String s) {
//
////                ((ViewGroup)mainBinding.mainTitle.getParent()).removeView(mainBinding.mainTitle);
//                mainBinding.linearLayout.removeView(mainBinding.mainTitle);
//
//                //utilize helper method (searchByName())in CocktailAPIHelper. response refers to class APIHelper response..
//                apiHelper.searchByName(s, (response) -> {
//                    //seeting reference in main and adapter to List of drinks given by API call.
//                    adapter.setDrinks((drinks = response));
//                });
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });


    }
}