package com.eazi.recipes.Helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eazi.recipes.IngSearch;
import com.eazi.recipes.R;

import java.util.List;

import static com.eazi.recipes.IngSearch.ingredients;
import static com.eazi.recipes.IngSearch.listView;

/**
 * Created by Novruz Engineer on 11/19/2018.
 */

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private List<ItemObject> itemList;
    private LayoutInflater mInflater;

    public ItemRecyclerViewAdapter(Context context,
                                  List<ItemObject> itemList) {
        this.itemList = itemList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layoutView = mInflater.inflate(R.layout.item_view, parent, false);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.image.setImageResource(itemList.get(position).getImage());
        holder.name.setText(itemList.get(position).getDesc());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = IngSearch.selectedIngs.get(position).getDesc();
                ItemObject item = new ItemObject(itemList.get(position).getImage(), itemList.get(position).getDesc());
                IngSearch.selectedIngs.remove(position);
                IngSearch.ingredients.remove(string);
                IngSearch.sList.get(IngSearch.strings.indexOf(string)).setSelected(false);
                IngSearch.recyclerView.getAdapter().notifyItemChanged(IngSearch.strings.indexOf(string));
                IngSearch.listView.getAdapter().notifyDataSetChanged();
//
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image, remove;
        public TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.circularImage2);
            remove =(ImageView) itemView.findViewById(R.id.removeItem);
            name = (TextView) itemView.findViewById(R.id.nameOfItem2);
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
    }

//    // allows clicks events to be caught
//    public void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//
//    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
//
    @Override
    public int getItemCount()
    {
        return this.itemList.size();
    }
}
