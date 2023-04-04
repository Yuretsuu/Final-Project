package com.example.cocktaildatabase_loveleen.database;

import android.content.Context;
import android.content.SharedPreferences;

public class CocktailPrefrences {

    /** the object of this class **/
    private static CocktailPrefrences cocktailPrefrences;

    /** the preferences object **/
    private SharedPreferences sharedPreferences;

    /** the private constructor **/
     private CocktailPrefrences(Context context){
        sharedPreferences = context.getSharedPreferences("Cocktail_pref", Context.MODE_PRIVATE);
     }

    /** the method to get instance of this class **/
    public static CocktailPrefrences getInstance(Context context){
        if(cocktailPrefrences == null)
            cocktailPrefrences = new CocktailPrefrences(context);

        return cocktailPrefrences;
    }

    /**
     * to save string to prefrences
     * @param key
     * @param value
     */
    public void saveToPrefrences(String key, String value){
        sharedPreferences.edit().putString(key, value).commit();
    }

    public String getFromPrefrences(String key, String defValue){
        return sharedPreferences.getString(key, defValue);
    }


}
