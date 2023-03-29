package com.cst2335.final_project;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CocktailAPI {

    private static final String URL_SEARCH_NAME = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=";
    private static final String URL_SEARCH_INGREDIENT = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=";
    private RequestQueue queue;

    public CocktailAPI(RequestQueue q){
        this.setQueue(q);
    }

    public List<Cocktail> searchByName(String query) {
        final List<Cocktail> result = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_SEARCH_NAME + query, null,
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

        return result;
    }




    public void setQueue(RequestQueue queue) {
        this.queue = queue;
    }
}
