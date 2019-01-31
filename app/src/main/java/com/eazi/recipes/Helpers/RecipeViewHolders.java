package com.eazi.recipes.Helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eazi.recipes.R;

/**
 * Created by Novruz Engineer on 11/3/2018.
 */

public class RecipeViewHolders extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView name;

    public RecipeViewHolders(View itemView)
    {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.imageOfItem2);
        name = (TextView) itemView.findViewById(R.id.nameOfItem);
    }

}
