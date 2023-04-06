package com.cst2335.final_project.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.final_project.R;
import com.cst2335.final_project.adapter.CocktailRecyclerViewAdapter;
import com.cst2335.final_project.database.Cocktail;
import com.cst2335.final_project.database.callback.RecyclerItemClickCallback;
import com.cst2335.final_project.network.CocktailAPIHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Igor Malov
 * A Fragment that allows the user to search for cocktail drinks by ingredient.
 * This is the first fragment that will be loaded into the MainActivity.
 */
public class HomeFragmentForSearchByIngredients extends Fragment implements View.OnClickListener {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String SEARCHED_DRINK_KEY = "searchedDrink";

    /* The parent view. **/
    private View mParentView;

    /**
     * The search edit text.
     **/
    private EditText edtSearch;

    /**
     * The search button.
     **/
    private Button btnSearch;

    /**
     * The recyclerview.
     **/
    private RecyclerView recyclerViewCockTail;

    /**
     * The recyclerview adapter responsible for filling recyclerview with items.
     **/
    private CocktailRecyclerViewAdapter cocktailRecyclerViewAdapter;

    /**
     * The list of cocktail drinks received from server.
     **/
    private List<Cocktail> listCocktailDrinks;

    /**
     * The shared preferences object.
     **/
    private SharedPreferences sharedPrefs;


    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mParentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, container, false);
        init();
        //display toast message
        Toast.makeText(getContext(), getResources().getString(R.string.find_by_ingredient_toast_message), Toast.LENGTH_SHORT).show();
        cocktailRecyclerViewAdapter.notifyDataSetChanged();
        return mParentView;
    }

    /**
     * Initializes controls and widgets.
     */
    private void init() {
        // Initialize shared preferences
        sharedPrefs = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Initialize UI elements
        edtSearch = mParentView.findViewById(R.id.edtSearch);
        btnSearch = mParentView.findViewById(R.id.btnSearch);
        recyclerViewCockTail = mParentView.findViewById(R.id.cocktailRecyclerView);

        // Load previously searched cocktail
        String searchedDrink = sharedPrefs.getString(SEARCHED_DRINK_KEY, "");
        edtSearch.setText(searchedDrink);

        // Initialize an empty list to hold the cocktail drinks
        listCocktailDrinks = new ArrayList<>();

        // Create a new adapter for the recyclerview, passing in the context and the list of cocktail drinks
        cocktailRecyclerViewAdapter = new CocktailRecyclerViewAdapter(getContext(), listCocktailDrinks);

        // Set the adapter for the recyclerview
        recyclerViewCockTail.setAdapter(cocktailRecyclerViewAdapter);

        // Set the layout manager for the recyclerview
        recyclerViewCockTail.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set item click callback for RecyclerView
        cocktailRecyclerViewAdapter.setRecyclerItemClickCallback(new RecyclerItemClickCallback() {
            @Override
            public void onItemClick(int position) {
                // Launch detail fragment for selected cocktail
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment_content_main, new DetailFragment(listCocktailDrinks.get(position)))
                        .addToBackStack(null)
                        .commit();
            }
        });
        // Set click listener for search button
        btnSearch.setOnClickListener(this);
    }

    /**
     * Called when the search button is clicked.
     * Validates the search input and searches for cocktails by ingredient.
     * @param view The view that was clicked.
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                searchByIngredient(edtSearch.getText().toString().trim());
                break;

        }
    }

    /**
     * Searches for cocktails by ingredient.
     * Saves the searched ingredient to SharedPreferences for future use.
     * @param search the ingredient to search for
     */
    private void searchByIngredient(String search) {
        if (search.length() == 0) {
            Snackbar.make(mParentView, "Please enter cocktail ingredient to search.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // Save the searched drink into shared preferences
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(SEARCHED_DRINK_KEY, search);
        editor.apply();

        listCocktailDrinks.clear();
        cocktailRecyclerViewAdapter.notifyDataSetChanged();

        // Perform the search operation using the CocktailAPIHelper
        CocktailAPIHelper apiHelper = new CocktailAPIHelper(getContext());
        apiHelper.searchByIngredient(search, (drinks) -> {
            for (Cocktail drink : drinks)
                listCocktailDrinks.add(drink);
            cocktailRecyclerViewAdapter.notifyDataSetChanged();
        });
    }
}