package com.eazi.recipes.Helpers;


import android.content.Context;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eazi.recipes.MainActivity;
import com.eazi.recipes.R;
import com.eazi.recipes.RecipesCatalogActivity;

import java.util.List;

/**
 * Created by Novruz Engineer on 10/24/2018.
 */

public class SampleRecyclerViewAdapter extends RecyclerView.Adapter<SampleViewHolders>
{
    private List<ItemObject> itemList;
    private Context context;

    public SampleRecyclerViewAdapter(Context context,
                                     List<ItemObject> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public SampleViewHolders onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.book_list_item, null);
        SampleViewHolders rcv = new SampleViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final SampleViewHolders holder, final int position)
    {
        holder.image.setImageResource(itemList.get(position).getImage());
        holder.desc.setText(itemList.get(position).getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.passingValue = itemList.get(position).getDesc().toLowerCase();
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), RecipesCatalogActivity.class));
            }
        });
    }



    @Override
    public int getItemCount()
    {
        return this.itemList.size();
    }
}
