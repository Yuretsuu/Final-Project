package com.cst2335.final_project.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cst2335.final_project.database.Cocktail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for handling methods related to handling the drinks API.
 * @author Elizabeth Quach
 */
public class CocktailAPIHelper {

    private static final String URL_SEARCH_NAME = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=";
    private static final String URL_SEARCH_INGREDIENT = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=";
    private RequestQueue queue;

    public CocktailAPIHelper(RequestQueue q){
        this.setQueue(q);
    }

    public CocktailAPIHelper(Context context){
        this(Volley.newRequestQueue(context));
    }

    /**
     *
     * @param query user input
     * @param callback when the API response comes back.
     */
    public void searchByName(String query, SearchCallback callback) {
        final List<Cocktail> result = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_SEARCH_NAME + query, null,
                (response) -> {
                    try {
                        JSONArray drinks = response.getJSONArray("drinks");

                        final int n = drinks.length();

                        for (int i = 0; i < n; i++) {
                            final JSONObject drink = drinks.getJSONObject(i);

                            final int id = drink.getInt("idDrink");
                            final String imageURL = drink.getString("strDrinkThumb");
                            final String drinkName = drink.getString("strDrink");
                            final String instructions = drink.getString("strInstructions");
                            final String ingredientOne = drink.getString("strIngredient1");
                            final String ingredientTwo = drink.getString("strIngredient2");
                            final String ingredientThree = drink.getString("strIngredient3");

                            final Cocktail cocktail = new Cocktail(
                                   id, imageURL, drinkName, instructions, ingredientOne, ingredientTwo,ingredientThree);

                            result.add(cocktail);
                        }
                        //
                        callback.onResponse(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                (error) -> {
                    Log.e("Error", error.getMessage());
                }
        );


        queue.add(request);
        return;
    }


    public void setQueue(RequestQueue queue) {
        this.queue = queue;
    }


    public interface SearchCallback {
        void onResponse(List<Cocktail> drinks);
    }
}
