package com.example.cocktaildatabase_loveleen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktaildatabase_loveleen.R;
import com.example.cocktaildatabase_loveleen.adapter.CocktailRecyclerViewAdapter;
import com.example.cocktaildatabase_loveleen.callback.RecyclerItemClickCallback;
import com.example.cocktaildatabase_loveleen.callback.ResponseCallback;
import com.example.cocktaildatabase_loveleen.database.Cocktail;
import com.example.cocktaildatabase_loveleen.network.APICall;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Loveleen Kaur
 * 28/03/2023
 * this the very first fragment that will be loaded into the MainActivity (Dashboard screen)
 * it loads the searched cocktail from server and shows in graphical interface
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mParentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home,container,false);
        init();
        return mParentView;

    }

    /**
     * initializing controls/widgets
     */
    private void init(){
        edtSearch = mParentView.findViewById(R.id.edtSearch);
        btnSearch = mParentView.findViewById(R.id.btnSearch);
        recyclerViewCockTail = mParentView.findViewById(R.id.recyclerCockTail);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSearch:
                searchCockTailDrink(edtSearch.getText().toString().trim());
                break;

        }
    }

    /**
     * implementing search operation
     * @param cocktailDrink
     */
    private void searchCockTailDrink(String cocktailDrink){
        if(cocktailDrink.length() == 0){
            Snackbar.make(mParentView,"Please enter cocktail drink to search.",Snackbar.LENGTH_SHORT).show();
            return;
        }

        listCocktailDrinks.clear();
        cocktailRecyclerViewAdapter.notifyDataSetChanged();

        APICall.getInstance(getContext()).getCocktail(
                cocktailDrink,
                new ResponseCallback() {
                    @Override
                    public void onSuccess(List<Cocktail> listCocktails) {
                        for(Cocktail ctd : listCocktails){
                            listCocktailDrinks.add(ctd);
                        }

                        cocktailRecyclerViewAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                }
        );
    }
}