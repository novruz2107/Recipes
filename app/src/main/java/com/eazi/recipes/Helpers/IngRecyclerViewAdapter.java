package com.eazi.recipes.Helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eazi.recipes.IngSearch;
import com.eazi.recipes.MainActivity;
import com.eazi.recipes.R;
import com.eazi.recipes.RecipeActivity;

import java.util.List;

import static android.R.attr.data;

/**
 * Created by Novruz Engineer on 11/10/2018.
 */

public class IngRecyclerViewAdapter extends RecyclerView.Adapter<IngViewHolder> {

    private List<ItemObject> itemList;
    private Context context;

    public IngRecyclerViewAdapter(Context context,
                                     List<ItemObject> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public IngViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.ing_itself_layout, null);
        IngViewHolder rcv = new IngViewHolder(layoutView);
        return rcv;
    }



    @Override
    public void onBindViewHolder(final IngViewHolder holder, final int position)
    {
        holder.image.setImageResource(itemList.get(position).getImage());
        holder.name.setText(itemList.get(position).getDesc());
        if(itemList.get(position).isSelected()) {
            holder.tick.animate().alpha(1f).setDuration(500);
            holder.image.animate().alpha(0.4f).setDuration(500);
        }else{
            holder.tick.animate().alpha(0f).setDuration(500);
            holder.image.animate().alpha(1f).setDuration(500);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemObject item = new ItemObject(itemList.get(position).getImage(), itemList.get(position).getDesc());
                if(!IngSearch.ingredients.contains(itemList.get(position).getDesc())) {
                    IngSearch.selectedIngs.add(item);
                    IngSearch.listView.getAdapter().notifyDataSetChanged();
                    IngSearch.ingredients.add(itemList.get(position).getDesc());
                    holder.tick.animate().alpha(1f).setDuration(500);
                    holder.image.animate().alpha(0.4f).setDuration(500);

                }else{
                    for(ItemObject itemObject : IngSearch.selectedIngs){
                        if(itemObject.getDesc().equals(itemList.get(position).getDesc())){
                            IngSearch.selectedIngs.remove(itemObject);
                            IngSearch.listView.getAdapter().notifyDataSetChanged();
                            break;
                        }
                    }
                    IngSearch.ingredients.remove(itemList.get(position).getDesc());
//                    IngSearch.selectedIngs.remove(0);
                    Log.d("novruz", String.valueOf(IngSearch.selectedIngs.indexOf(item)));
//                    IngSearch.listView.getAdapter().notifyDataSetChanged();
                    holder.tick.animate().alpha(0f).setDuration(500);
                    holder.image.animate().alpha(1f).setDuration(500);
                }
            }
        });
    }



    @Override
    public int getItemCount()
    {
        return this.itemList.size();
    }

}
