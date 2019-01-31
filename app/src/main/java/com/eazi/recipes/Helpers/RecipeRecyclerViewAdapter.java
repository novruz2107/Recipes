package com.eazi.recipes.Helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eazi.recipes.MainActivity;
import com.eazi.recipes.R;
import com.eazi.recipes.RecipeActivity;
import com.eazi.recipes.RecipesCatalogActivity;

import java.util.List;

/**
 * Created by Novruz Engineer on 11/3/2018.
 */

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeViewHolders> {


    private List<Recipe> itemList;
    private Context context;

    public RecipeRecyclerViewAdapter(Context context,
                                     List<Recipe> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecipeViewHolders onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recipe_list_item, null);
        RecipeViewHolders rcv = new RecipeViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolders holder, final int position)
    {
        holder.image.setImageResource(itemList.get(position).getPhoto());
        holder.name.setText(itemList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.passingValue = itemList.get(position).getName();
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), RecipeActivity.class));
            }
        });
    }



    @Override
    public int getItemCount()
    {
        return this.itemList.size();
    }

}
