package com.eazi.recipes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eazi.recipes.Helpers.ItemObject;
import com.eazi.recipes.Helpers.SampleRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Novruz Engineer on 10/24/2018.
 */

public class CategoriesFragment extends Fragment {

    private StaggeredGridLayoutManager _sGridLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categories, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_for_categories);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);

        _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(_sGridLayoutManager);

        List<ItemObject> sList = getListItemData();

        SampleRecyclerViewAdapter rcAdapter = new SampleRecyclerViewAdapter(
                getContext(), sList);
        recyclerView.setAdapter(rcAdapter);

        return v;
    }

    private List<ItemObject> getListItemData()
    {
        List<ItemObject> listViewItems = new ArrayList<ItemObject>();
        listViewItems.add(new ItemObject(R.drawable.cakes, "Cake"));
        listViewItems.add(new ItemObject(R.drawable.mexican, "Mexican Cuisine"));
        listViewItems.add(new ItemObject(R.drawable.breads, "Bread Recipes"));
        listViewItems.add(new ItemObject(R.drawable.deserts, "Desserts"));
        listViewItems.add(new ItemObject(R.drawable.azerbaijan, "Azerbaijan Cuisine"));
        listViewItems.add(new ItemObject(R.drawable.cocktails, "Cocktail"));
        listViewItems.add(new ItemObject(R.drawable.snacks, "Appetizers & Snacks"));
        listViewItems.add(new ItemObject(R.drawable.diabetic, "Diabetic"));
        listViewItems.add(new ItemObject(R.drawable.cookies, "Cookie"));
        listViewItems.add(new ItemObject(R.drawable.chinese, "Chinese Cuisine"));

        return listViewItems;
    }
}
