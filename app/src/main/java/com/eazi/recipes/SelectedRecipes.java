package com.eazi.recipes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;


import com.eazi.recipes.Helpers.DBHelper;
import com.eazi.recipes.Helpers.Recipe;
import com.eazi.recipes.Helpers.RecipeRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Novruz Engineer on 10/28/2018.
 */

public class SelectedRecipes extends AppCompatActivity {

    DBHelper mDBHelper;
    GifImageView loadingGifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_selected_recipes);
        mDBHelper = new DBHelper(this);
        loadingGifView = (GifImageView) findViewById(R.id.gifImageView);
        final StaggeredGridLayoutManager _sGridLayoutManager;
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_for_selected_recipes);
        recyclerView.setHasFixedSize(true);

        _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(_sGridLayoutManager);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Recipe> sList = getListItemData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecipeRecyclerViewAdapter rcAdapter = new RecipeRecyclerViewAdapter(
                                getApplicationContext(), sList);
                        recyclerView.setAdapter(rcAdapter);
                        loadingGifView.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mDBHelper.dropViews();
        IngSearch.ingredients.clear();
        mDBHelper.close();

    }

    private List<Recipe> getListItemData()
    {
        ArrayList<Recipe> names = mDBHelper.getRecipePriorToIngredient();
        List<Recipe> listViewItems = new ArrayList<Recipe>();
        for(int i=0; i<names.size(); i++){
            listViewItems.add(new Recipe(R.drawable.cakes, names.get(i).getName()));

        }

        return listViewItems;
    }
}
