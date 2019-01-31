package com.eazi.recipes.Helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eazi.recipes.R;

/**
 * Created by Novruz Engineer on 11/10/2018.
 */

public class IngViewHolder extends RecyclerView.ViewHolder {

    public ImageView image, tick;
    public TextView name;

    public IngViewHolder(View itemView)
    {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.imageOfIng);
        tick =(ImageView) itemView.findViewById(R.id.imageOfTick);
        name = (TextView) itemView.findViewById(R.id.nameOfIng);
    }
}
