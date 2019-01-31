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

public class HomeFragment extends Fragment {

    private StaggeredGridLayoutManager _sGridLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
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
        listViewItems.add(new ItemObject(R.drawable.trending, "Trending"));
        listViewItems.add(new ItemObject(R.drawable.popular, "Popular"));
        listViewItems.add(new ItemObject(R.drawable.hallowen, "Happy Halloween"));
        listViewItems.add(new ItemObject(R.drawable.breakfast, "Breakfast chills"));
        listViewItems.add(new ItemObject(R.drawable.eggs, "Dishes with eggs"));
        listViewItems.add(new ItemObject(R.drawable.weightloss, "Weight-loss Recipes"));

        return listViewItems;
    }
}
