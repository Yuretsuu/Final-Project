package com.cst2335.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
        View cocktailItems = LayoutInflater.from(context).inflate(R.layout.cocktail_item, parent, false);
        return new CocktailAdapter.ViewHolder(cocktailItems);
//        CocktailDisplayBinding view = CocktailDisplayBinding.inflate(inflater);
//        return new ViewHolder(view.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cocktail currentCocktail = cocktailList.get(position);

        //set image from API via URL
        Glide.with(context).load(currentCocktail.getImageURL()).into(holder.cocktailImg);

        holder.nameOfCocktail.setText(currentCocktail.getDrinkName());
        holder.ingredientsList.get(0).setText(currentCocktail.getIngredientOne());
        holder.ingredientsList.get(1).setText(currentCocktail.getIngredientTwo());
        holder.ingredientsList.get(2).setText(currentCocktail.getIngredientThree());
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
