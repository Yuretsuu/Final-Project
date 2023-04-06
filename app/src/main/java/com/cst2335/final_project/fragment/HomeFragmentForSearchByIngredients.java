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

/**S
 * Loveleen Kaur
 * 28/03/2023
 * this the very first fragment that will be loaded into the MainActivity (Dashboard screen)
 * it loads the searched cocktail from server and shows in graphical interface
 */
public class HomeFragmentForSearchByIngredients extends Fragment implements View.OnClickListener {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String SEARCHED_DRINK_KEY = "searchedDrink";

    /* the parent view **/
    private View mParentView;

    /** the search edit text **/
    private EditText edtSearch;

    /** the search button **/
    private Button btnSearch;

    /** the recyclerview **/
    private RecyclerView recyclerViewCockTail;

    /** the recyclerview adapter responsible for filling recyclerview with items **/
    private CocktailRecyclerViewAdapter cocktailRecyclerViewAdapter;

    /** the list of cocktail drinks received from server **/
    private List<Cocktail> listCocktailDrinks;

    private SharedPreferences sharedPrefs;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mParentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home,container,false);
        init();
        Toast.makeText(getContext(),  getResources().getString(R.string.find_by_ingredient_toast_message), Toast.LENGTH_SHORT).show();
        cocktailRecyclerViewAdapter.notifyDataSetChanged();
        return mParentView;
    }

    /**
     * initializing controls/widgets
     */
    private void init(){
        // Initialize shared preferences
        sharedPrefs = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        edtSearch = mParentView.findViewById(R.id.edtSearch);
        btnSearch = mParentView.findViewById(R.id.btnSearch);
        recyclerViewCockTail = mParentView.findViewById(R.id.cocktailRecyclerView);

        // Check shared preferences for previously searched drink and display it in the search bar
        String searchedDrink = sharedPrefs.getString(SEARCHED_DRINK_KEY, "");
        edtSearch.setText(searchedDrink);

        listCocktailDrinks = new ArrayList<>();
        cocktailRecyclerViewAdapter = new CocktailRecyclerViewAdapter(getContext(), listCocktailDrinks);
        recyclerViewCockTail.setAdapter(cocktailRecyclerViewAdapter);
        recyclerViewCockTail.setLayoutManager(new LinearLayoutManager(getContext()));
        cocktailRecyclerViewAdapter.setRecyclerItemClickCallback(new RecyclerItemClickCallback() {
            @Override
            public void onItemClick(int position) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment_content_main, new DetailFragment(listCocktailDrinks.get(position)))
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnSearch.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSearch:
                searchByIngredient(edtSearch.getText().toString().trim());
                break;

        }
    }

    /**
     * implementing search operation
     * @param search
     */
    private void searchByIngredient(String search){
        if(search.length() == 0){
            Snackbar.make(mParentView,"Please enter cocktail drink to search.",Snackbar.LENGTH_SHORT).show();
            return;
        }

        // Save the searched drink into shared preferences
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(SEARCHED_DRINK_KEY, search);
        editor.apply();

        listCocktailDrinks.clear();
        cocktailRecyclerViewAdapter.notifyDataSetChanged();

        CocktailAPIHelper apiHelper = new CocktailAPIHelper(getContext());
        apiHelper.searchByIngredient(search, (drinks) -> {
            for(Cocktail drink : drinks)
                listCocktailDrinks.add(drink);
            cocktailRecyclerViewAdapter.notifyDataSetChanged();
        });
    }
}