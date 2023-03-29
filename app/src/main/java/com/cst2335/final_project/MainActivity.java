package com.cst2335.final_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cst2335.final_project.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is responsible for handling the search function after the user has created a profile.
 */
public class MainActivity extends AppCompatActivity {

    private static final String URL_SEARCH = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        mainBinding.mainSearchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //TODO Refactor into method later
            public boolean onQueryTextSubmit(String s) {
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_SEARCH + s, null,
                    (response) -> {
                        try {
                            JSONArray drinks = response.getJSONArray("drinks");
                            final int n = drinks.length();

                            for (int i = 0; i < n; i++) {
                                final JSONObject drink = drinks.getJSONObject(i);

                                final String drinkName = drink.getString("strDrink");
                                final String instructions = drink.getString("strInstructions");
                                final String ingredientOne = drink.getString("strIngredient1");
                                final String ingredientTwo = drink.getString("strIngredient2");
                                final String ingredientThree = drink.getString("strIngredient3");

                                Log.i("Drink",drinkName);

                                final Cocktail cocktail = new Cocktail(
                                        drinkName, instructions, ingredientOne, ingredientTwo,ingredientThree);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },

                    (error) -> {
                        Log.e("Error", error.getMessage());
                    }
                );


                queue.add(request);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.e("TextChange","Change works");
                return false;
            }
        });


    }
}