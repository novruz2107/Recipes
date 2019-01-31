package com.eazi.recipes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eazi.recipes.Helpers.DBHelper;
import com.eazi.recipes.Helpers.ItemObject;
import com.eazi.recipes.Helpers.Recipe;
import com.eazi.recipes.Helpers.RecipeRecyclerViewAdapter;
import com.eazi.recipes.Helpers.SampleRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.eazi.recipes.R.drawable.breakfast;

/**
 * Created by Novruz Engineer on 11/4/2018.
 */

public class RecipesCatalogActivity extends AppCompatActivity {

    private StaggeredGridLayoutManager _sGridLayoutManager;
    private String value = MainActivity.passingValue;
    Runnable mRunnable;
    TextView header, statistics;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_catalog_activity);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        header = findViewById(R.id.header);
        statistics = findViewById(R.id.resultStatistics);

        _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(_sGridLayoutManager);

        List<Recipe> sList = getListItemData();

        RecipeRecyclerViewAdapter rcAdapter = new RecipeRecyclerViewAdapter(
                this, sList);
        recyclerView.setAdapter(rcAdapter);

    }

    private List<Recipe> getListItemData()
    {
        final List<Recipe> listViewItems = new ArrayList<Recipe>();

        if(value.equals("trending")){

        }else if(value.equals("popular")){

        }else if(value.equals("happy halloween")){

        }else if(value.equals("breakfast chills")){

        }else if(value.equals("weight-loss recipes")){

        }else if(value.equals("dishes with eggs")){

        }else{
            final DBHelper dbHelper = new DBHelper(this);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listViewItems.addAll(dbHelper.getRecipeByType(value));
                    header.setText("Results for " + value);
                    statistics.setText(String.valueOf(listViewItems.size()) + " are found");
                }
            });

        }

        return listViewItems;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
