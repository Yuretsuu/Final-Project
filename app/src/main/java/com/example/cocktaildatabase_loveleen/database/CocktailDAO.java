package com.example.cocktaildatabase_loveleen.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * This DAO interface represents the cocktail object that used to access the database.
 */
@Dao
public interface CocktailDAO {

    @Insert
    public long insertCocktail(Cocktail cocktail);

    @Query("Select * from Cocktail")
    public List<Cocktail> getAllCocktails();

    @Delete
    void deleteCocktail(Cocktail cocktail);

}
