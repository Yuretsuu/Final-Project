package com.cst2335.final_project;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Cocktail.class}, version= 1)
public abstract class CocktailDatabaseAccess extends RoomDatabase {

    public abstract CocktailDAO cocktailDao();
    private static CocktailDatabaseAccess connection;

    public static synchronized CocktailDatabaseAccess getInstance(Context context) {
        if(connection == null) {
            connection = Room.databaseBuilder(context.getApplicationContext(),
                            CocktailDatabaseAccess.class, "database-name")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return connection;
    }

}
