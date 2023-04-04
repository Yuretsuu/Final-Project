package com.cst2335.final_project.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cocktaildatabase_loveleen.R;
import com.cst2335.final_project.adapter.CocktailRecyclerViewAdapter;
import com.cst2335.final_project.callback.RecyclerItemClickCallback;
import com.cst2335.final_project.database.Cocktail;
import com.cst2335.final_project.database.CocktailDatabaseAccess;
import com.cst2335.final_project.database.CocktailPrefrences;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Loveleen Kaur
 * 31/03/2023
 * @version 1
 *
 * this class represents a fragment that shows favourite cocktails
 * which are saved in local database
 */
public class FavouriteFragment extends Fragment implements TextWatcher {

    /** the parent view **/
    private View mParentView;

    /** the edit text to seach cocktail **/
    private EditText edtSearch;

    /** the recyclerview **/
    private RecyclerView recyclerViewCockTail;

    /** the recyclerview adapter responsible for filling recyclerview with items **/
    private CocktailRecyclerViewAdapter cocktailRecyclerViewAdapter;

    /** the list of cocktail drinks received from local database  **/
    private List<Cocktail> listCocktailDrinks;
    private List<Cocktail> listCocktailDrinksAll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fragment_favourite, container, false);
        init();
        loadCocktailsFromDatabase();
        return mParentView;
    }

    /**
     * initializing controls/widgets
     */
    private void init(){
        edtSearch = mParentView.findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(this);
         recyclerViewCockTail = mParentView.findViewById(R.id.recyclerCockTail);
         //databaseHandler = new DatabaseHandler(getContext());

        listCocktailDrinks = new ArrayList<>();
        listCocktailDrinksAll = new ArrayList<>();
        cocktailRecyclerViewAdapter = new CocktailRecyclerViewAdapter(getContext(), listCocktailDrinks);
        recyclerViewCockTail.setAdapter(cocktailRecyclerViewAdapter);
        recyclerViewCockTail.setLayoutManager(new LinearLayoutManager(getContext()));
        cocktailRecyclerViewAdapter.setRecyclerItemClickCallback(new RecyclerItemClickCallback() {
            @Override
            public void onItemClick(int position) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frameLayout, DetailFragment.getInstance(listCocktailDrinks.get(position)))
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onItemLongClick(int position) {
                showDeleteConfirmationDailog(position);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    /**
     * Loading cocktails from local database to show
     *
     */
    private void loadCocktailsFromDatabase(){
        // starting thread to executing databse code
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Getting cocktail drinks from local databse
                List<Cocktail> cocktailList = CocktailDatabaseAccess.getInstance(getContext()).cocktailDao().getAllCocktails();
                for(Cocktail cocktail : cocktailList){
                    listCocktailDrinks.add(cocktail);
                    listCocktailDrinksAll.add(cocktail);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), listCocktailDrinks.size() + getResources().getString(R.string.favorite_cocktail_shown), Toast.LENGTH_SHORT).show();
                        cocktailRecyclerViewAdapter.notifyDataSetChanged();
                        edtSearch.setText(CocktailPrefrences.getInstance(getContext())
                                .getFromPrefrences("term", ""));
                    }
                });


            }
        });

    }


    /**
     * show confirmation dialog for deleting the item
     */
    private void showDeleteConfirmationDailog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.confirmation));
        builder.setMessage(getResources().getString(R.string.do_you_want_to_delete));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        /** Deleting cocktail from local database **/
                        CocktailDatabaseAccess.getInstance(getContext()).cocktailDao().deleteCocktail(listCocktailDrinks.get(position));

                        /** removing item from recyclerview list **/
                        Cocktail deletedCocktail = listCocktailDrinks.remove(position);
                        listCocktailDrinksAll.remove(deletedCocktail);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /** updating UI **/
                                Toast.makeText(getContext(), getResources().getString(R.string.cocktail_deleted), Toast.LENGTH_SHORT).show();
                                cocktailRecyclerViewAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /** dismiss the dialog when no button pressed **/
                dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // this method is called when something is being typed in search edit text
        String typedString = s.toString().toLowerCase();
        listCocktailDrinks.clear();

        // traverse for each record found from databse to check
        // wheather the typed term is a part of cocktail name or ingredient
        for(Cocktail cocktail : listCocktailDrinksAll){
            if(cocktail.getDrinkName().toLowerCase().contains(typedString)
            || cocktail.getIngredientOne().toLowerCase().contains(typedString)
            || cocktail.getIngredientTwo().toLowerCase().contains(typedString)
            || cocktail.getIngredientThree().toLowerCase().contains(typedString)){
                listCocktailDrinks.add(cocktail);
            }
        }

        // updating Sahred preferences with typed term
        CocktailPrefrences.getInstance(getActivity()).saveToPrefrences("term", s.toString());

        cocktailRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}