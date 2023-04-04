package com.example.cocktaildatabase_loveleen.callback;

import com.example.cocktaildatabase_loveleen.database.Cocktail;

import java.util.List;

/**
 * Loveleen Kaur
 * 28/03/2023
 *
 * this is interface which handles the response received from seerver
 * and notify the activity/fragment to show data in graphical interface
 *
 */
public interface ResponseCallback {

    void onSuccess(List<Cocktail> listCocktails);
    void onError(String errorMessage);

}
