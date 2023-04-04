package com.cst2335.final_project.database;

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

    @Query("Select * from Cocktail Where id =:id")
    public Cocktail getCocktail(int id);

    @Delete
    void deleteCocktail(Cocktail cocktail);
}
