package com.cst2335.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.final_project.databinding.CocktailDisplayBinding;

import java.util.ArrayList;
import java.util.List;

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
        //holder.nameOfCocktail.setText(cocktailList.get(position).drinkName);
        //holder.cocktailImg.setImageResource();
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

        ImageView cocktailImg;
        TextView nameOfCocktail;
        List<TextView> ingredientsList = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cocktailImg = itemView.findViewById(R.id.cocktail_image);
            nameOfCocktail = itemView.findViewById(R.id.name_of_cocktail);
            ingredientsList.add(itemView.findViewById(R.id.first_ingredient));
            ingredientsList.add(itemView.findViewById(R.id.secod_ingredient));
            ingredientsList.add(itemView.findViewById(R.id.third_ingredient));
        }
    }

    public void setDrinks(List<Cocktail> drinks) {
        this.cocktailList = drinks;
        this.notifyDataSetChanged();
    }
}
