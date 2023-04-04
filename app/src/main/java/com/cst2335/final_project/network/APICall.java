package com.cst2335.final_project.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cst2335.final_project.callback.ResponseCallback;
import com.cst2335.final_project.database.Cocktail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Loveleen Kaur
 * 28/03/2023
 *
 * this class handles network calls that retreive the data from server
 * and loads into the application
 */

public class APICall {

    /** the context **/
    private static Context mContext;

    /** the instance of this class **/
    private static APICall apiCall;

    /** the the URL **/
    private final String URL = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=";

    /** make it singleton class **/
    private APICall(){}


    /**
     * get instance of this class to access other member functions
     * @param _context
     * @return
     */
    public static APICall getInstance(Context _context){
        mContext = _context;
        if(apiCall == null)
            apiCall = new APICall();

        return apiCall;
    }


    /**
     * this method will hit the server with required cocktail keyword and retrieve
     * the result and  load into the model class
     *
     * @param cocktailDrink the drink to be searched
     */
    public void getCocktail(String cocktailDrink, ResponseCallback responseCallback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL + cocktailDrink,
                null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // on response received from server
                        List<Cocktail> cocktailDrinkList = new ArrayList<>();

                        try {
                            JSONArray drinks = response.getJSONArray("drinks");
                            for(int i = 0; i < drinks.length(); i++){
                                JSONObject drink = drinks.getJSONObject(i);
                                Cocktail cocktailDrink = new Cocktail();
                                cocktailDrink.setDrinkName(drink.getString("strDrink"));
                                cocktailDrink.setInstructions(drink.getString("strInstructions"));
                                cocktailDrink.setThumbnail(drink.getString("strDrinkThumb"));
                                cocktailDrink.setIngredientOne(drink.getString("strIngredient1"));
                                cocktailDrink.setIngredientTwo(drink.getString("strIngredient2"));
                                cocktailDrink.setIngredientThree(drink.getString("strIngredient3"));
                                cocktailDrinkList.add(cocktailDrink);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        responseCallback.onSuccess(cocktailDrinkList);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // on error occurred
                        responseCallback.onError(error.getMessage() + "");
                    }
                }
        );

        Volley.newRequestQueue(mContext).add(jsonObjectRequest);
    }




}
