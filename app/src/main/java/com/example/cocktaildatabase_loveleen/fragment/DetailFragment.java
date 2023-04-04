package com.example.cocktaildatabase_loveleen.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cocktaildatabase_loveleen.R;
import com.example.cocktaildatabase_loveleen.database.Cocktail;
import com.example.cocktaildatabase_loveleen.database.CocktailDatabaseAccess;
import com.example.cocktaildatabase_loveleen.network.MyExecutor;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailFragment extends Fragment implements View.OnClickListener {

    /** the parent view **/
    private View mParentView;

    /** the cocktail object **/
    private static Cocktail cocktailDrink;

    /** the thumbnail image **/
    private ImageView imgThumbnail;

    /** the Drink name **/
    private TextView txtDrinkName;

    /** the ingredient1 **/
    private TextView txtIngredient1;

    /** the ingredient2 **/
    private TextView txtIngredient2;

    /** the ingredient3 **/
    private TextView txtIngredient3;

    /** instructions **/
    private TextView txtInstructions;

    /** the save button **/
    private Button btnSave;

    /** the Dabasehandler class **/
//    private DatabaseHandler databaseHandler;

    private static DetailFragment detailFragment;

    /**
     * getinstance of this fragment
     * @return
     */
    public static DetailFragment getInstance(Cocktail cocktailDrink){
        if(detailFragment == null)
            detailFragment = new DetailFragment();
        DetailFragment.cocktailDrink = cocktailDrink;
        return detailFragment;
    }

    /** the default constructor **/
    public DetailFragment(){}

    /** the customised constructor **/
    public DetailFragment(Cocktail cocktailDrink){
        this.cocktailDrink = cocktailDrink;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mParentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_detail, container, false);
        init();
        return mParentView;
    }


    /**
     * initializing controls
     */
    private void init(){
        imgThumbnail = mParentView.findViewById(R.id.imgThumbnail);
        txtDrinkName = mParentView.findViewById(R.id.txtDrinkName);
        txtIngredient1 = mParentView.findViewById(R.id.txtIngredient1);
        txtIngredient2 = mParentView.findViewById(R.id.txtIngredient2);
        txtIngredient3 = mParentView.findViewById(R.id.txtIngredient3);
        txtInstructions = mParentView.findViewById(R.id.txtInstructions);
        btnSave = mParentView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        /** load image from server to ImageView **/
        Picasso.get()
                .load(cocktailDrink.getThumbnail())
                .resize(700,400)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgThumbnail);

        txtDrinkName.setText(cocktailDrink.getDrinkName());
        txtIngredient1.setText(cocktailDrink.getIngredientOne());
        txtIngredient2.setText(cocktailDrink.getIngredientTwo());
        txtIngredient3.setText(cocktailDrink.getIngredientThree());
        txtInstructions.setText(cocktailDrink.getInstructions());

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSave:
                // save button pressed
                saveCocktailDrink();
                break;
        }
    }


    /**
     * save cocktail drink to local Database **
     */
    private void saveCocktailDrink(){
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(new Runnable() {
            @Override
            public void run() {
                /**  inserting cocktail record into databse **/
                CocktailDatabaseAccess.getInstance(getActivity()).cocktailDao().insertCocktail(cocktailDrink);

                /** Executing main ui thread so that UI can be updated **/
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), getResources().getString(R.string.cocktail_added),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }




}
