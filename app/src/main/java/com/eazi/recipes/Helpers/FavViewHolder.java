package com.eazi.recipes.Helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eazi.recipes.R;
import com.github.siyamed.shapeimageview.CircularImageView;

/**
 * Created by Novruz Engineer on 11/18/2018.
 */

public class FavViewHolder extends RecyclerView.ViewHolder {

    public ImageView delete;
    public CircularImageView image;
    public TextView name, type;

    public FavViewHolder(View itemView)
    {
        super(itemView);
        image = (CircularImageView) itemView.findViewById(R.id.circularImage);
        delete =(ImageView) itemView.findViewById(R.id.deleteImage);
        name = (TextView) itemView.findViewById(R.id.textView4);
        type = itemView.findViewById(R.id.textView2);
    }
}
