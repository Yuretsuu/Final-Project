package com.cst2335.final_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.final_project.database.Cocktail;
import com.example.cocktaildatabase_loveleen.R;
import com.example.cocktaildatabase_loveleen.databinding.CocktailDisplayBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.ViewHolder> {
    List<Cocktail> cocktailList;
    Context context;
    LayoutInflater inflater;

    //Constructor
    public CocktailAdapter(Context context, List<Cocktail> cocktailList){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.cocktailList = cocktailList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CocktailDisplayBinding view = CocktailDisplayBinding.inflate(inflater);
        return new ViewHolder(view.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.drinkName.setText(cocktailList.get(position).getDrinkName());
    }

    @Override
    public int getItemCount() {
        if(cocktailList == null){
            return 0;
        }
        return cocktailList.size();
    }
    //View class that extends RecyclerView to display the drink name in Recycler View
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView drinkName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.cocktail_drink_name);
        }
    }

    public void setDrinks(List<Cocktail> drinks) {
        this.cocktailList = drinks;
        this.notifyDataSetChanged();
    }
}
