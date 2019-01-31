package com.eazi.recipes;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.eazi.recipes.Helpers.IngRecyclerViewAdapter;
import com.eazi.recipes.Helpers.IngViewHolder;
import com.eazi.recipes.Helpers.ItemObject;
import com.eazi.recipes.Helpers.ItemRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by Novruz Engineer on 10/28/2018.
 */

public class IngSearch extends Fragment {

    public static ArrayList<String> ingredients;
    AutoCompleteTextView autocomplete;
    ArrayList<IngViewHolder> holders;
    Button search;
    private StaggeredGridLayoutManager _sGridLayoutManager;
    private String string;
    public static ArrayList<ItemObject> selectedIngs;
    public static RecyclerView listView;
    public static IngRecyclerViewAdapter rcAdapter;
    public static ArrayList<ItemObject> sList;
    public static ArrayList<String> strings;
    public static RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ing_search, container, false);

        search = (Button) v.findViewById(R.id.searchForSelectedIngs);
        holders = new ArrayList<>();
        ingredients = new ArrayList<>();
        selectedIngs = new ArrayList<>();

        listView = (RecyclerView) v.findViewById(R.id.selectedIngs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listView.setLayoutManager(linearLayoutManager);
        final ItemRecyclerViewAdapter itemRecyclerViewAdapter = new ItemRecyclerViewAdapter(
                getContext(), selectedIngs);
        listView.setAdapter(itemRecyclerViewAdapter);
        autocomplete = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView1);

        strings = addIngs();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, strings);

        recyclerView = v.findViewById(R.id.recycler_of_ing_search);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(_sGridLayoutManager);
        sList = getListItemData();
        rcAdapter = new IngRecyclerViewAdapter(
                getContext(), sList);
        recyclerView.setAdapter(rcAdapter);
        autocomplete.setThreshold(1);
        autocomplete.setAdapter(adapter);

        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                string = String.valueOf(arg0.getItemAtPosition(arg2));
                if (!ingredients.contains(string)) {
                    ingredients.add(string);
                    for(ItemObject item : sList){
                        if(item.getDesc().equals(string)){
                            selectedIngs.add(item);
                            listView.getAdapter().notifyDataSetChanged();
                            break;
                        }
                    }
                    sList.get(strings.indexOf(string)).setSelected(true);
                    recyclerView.getAdapter().notifyDataSetChanged();
//                    IngViewHolder holder = (IngViewHolder) recyclerView.findViewHolderForAdapterPosition(strings.indexOf(string));
//                    if(holder!=null) {
//                        holder.tick.animate().alpha(1f).setDuration(500);
//                        holder.image.animate().alpha(0.4f).setDuration(500);
//                    }
//                    holders.add(holder);
                } else {
                    Toast.makeText(getContext(), "You've already chosen", Toast.LENGTH_SHORT).show();
                }
                autocomplete.setText("");
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for(int i=0; i<ingredients.size(); i++){
//                    IngViewHolder holder = (IngViewHolder) recyclerView.findViewHolderForAdapterPosition(strings.indexOf(ingredients.get(i)));
//                    if(holder!=null) {
//                        Log.d("novruz", String.valueOf(holder.name.getText()));
//                        holder.tick.setAlpha(0f);
//                        holder.image.setAlpha(1f);
//                    }
//                }
                for(ItemObject i : sList){
                    i.setSelected(false);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
                selectedIngs.clear();
                listView.getAdapter().notifyDataSetChanged();
                startActivity(new Intent(getContext(), SelectedRecipes.class));
            }
        });

        return v;
    }


    private ArrayList<ItemObject> getListItemData()
    {
        ArrayList<ItemObject> listViewItems = new ArrayList<ItemObject>();
        listViewItems.add(new ItemObject(R.drawable.potato, "Potato"));
        listViewItems.add(new ItemObject(R.drawable.cucumber, "Cucumber"));
        listViewItems.add(new ItemObject(R.drawable.beef, "Beef"));
        listViewItems.add(new ItemObject(R.drawable.onion, "Onion"));
        listViewItems.add(new ItemObject(R.drawable.butter, "Butter"));
        listViewItems.add(new ItemObject(R.drawable.rice, "Rice"));
        listViewItems.add(new ItemObject(R.drawable.tomato, "Tomato"));
        listViewItems.add(new ItemObject(R.drawable.floor, "Floor"));
        listViewItems.add(new ItemObject(R.drawable.cheese, "Cheese"));
        listViewItems.add(new ItemObject(R.drawable.bacon, "Bacon"));

        return listViewItems;
    }

    private ArrayList<String > addIngs(){
        ArrayList<String> ings = new ArrayList<>();

        ings.add("Potato");
        ings.add("Cucumber");
        ings.add("Beef");
        ings.add("Onion");
        ings.add("Butter");
        ings.add("Rice");
        ings.add("Tomato");
        ings.add("Floor");
        ings.add("Cheese");
        ings.add("Bacon");

        return ings;
    }

}
