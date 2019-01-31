package com.eazi.recipes.Helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eazi.recipes.R;

/**
 * Created by Novruz Engineer on 10/24/2018.
 */

public class SampleViewHolders extends RecyclerView.ViewHolder
{
    public ImageView image;
    public TextView desc;

    public SampleViewHolders(View itemView)
    {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.imageOfItem);
        desc = (TextView) itemView.findViewById(R.id.textOfItem);
    }
}
