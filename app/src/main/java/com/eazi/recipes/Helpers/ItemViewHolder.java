package com.eazi.recipes.Helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eazi.recipes.R;

/**
 * Created by Novruz Engineer on 11/19/2018.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView image, remove;
    public TextView name;

    public ItemViewHolder(View itemView)
    {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.circularImage2);
        remove =(ImageView) itemView.findViewById(R.id.removeItem);
        name = (TextView) itemView.findViewById(R.id.nameOfItem2);
    }
}
