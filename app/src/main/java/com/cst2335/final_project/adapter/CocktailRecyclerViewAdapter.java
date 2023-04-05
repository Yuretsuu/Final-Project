package com.cst2335.final_project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.final_project.database.Cocktail;
import com.cst2335.final_project.R;
import com.squareup.picasso.Picasso;
import com.cst2335.final_project.database.callback.RecyclerItemClickCallback;

import java.util.List;

public class CocktailRecyclerViewAdapter extends RecyclerView.Adapter<CocktailRecyclerViewAdapter.ViewHolder> {

    /** the context refrence **/
    private Context mContext;

    /** the list of cocktail drinks received from server **/
    private List<Cocktail> listCocktailDrink;

    /** the RecyclerView item click callback **/
    private RecyclerItemClickCallback recyclerItemClickCallback;

    /**
     * constructor
     * @param context
     * @param listCocktailDrinks
     */
    public CocktailRecyclerViewAdapter(Context context, List<Cocktail> listCocktailDrinks){
        this.listCocktailDrink = listCocktailDrinks;
        this.mContext = context;
    }

    /**
     * set RecyclerItem click callback
     * @param recyclerItemClickCallback
     */
    public void setRecyclerItemClickCallback(RecyclerItemClickCallback recyclerItemClickCallback) {
        this.recyclerItemClickCallback = recyclerItemClickCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view_holder, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // binding recycler view with data
        Cocktail cocktailDrink = listCocktailDrink.get(position);
        holder.txtCocktailDrink.setText(cocktailDrink.getDrinkName());
        holder.txtIngredients.setText(
                cocktailDrink.getIngredientOne()
                + ", "
                + cocktailDrink.getIngredientTwo()
                + ", "
                + cocktailDrink.getIngredientThree()
        );
        Picasso.get()
                .load(cocktailDrink.getThumbnail())
                .resize(70,70)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imgThumbnail);

        // implementing recyclerview item's click listerner
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerItemClickCallback.onItemClick(holder.getAdapterPosition());
            }
        });

        // implementing recyclerview item's long click listerner
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                recyclerItemClickCallback.onItemLongClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCocktailDrink.size();
    }


    /**
     * this class represents recyclerView item
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        // thumbnail image
        ImageView imgThumbnail;

        // cocktail drink
        TextView txtCocktailDrink;

        // ingredients
        TextView txtIngredients;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
            txtCocktailDrink = itemView.findViewById(R.id.txtCocktailDrink);
            txtIngredients = itemView.findViewById(R.id.txtIngredients);
        }
    }
}
