package com.eazi.recipes.Helpers;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eazi.recipes.MainActivity;
import com.eazi.recipes.R;
import com.eazi.recipes.RecipeActivity;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;

/**
 * Created by Novruz Engineer on 10/31/2018.
 */

public class CustomSuggestionsAdapter extends SuggestionsAdapter<Recipe, CustomSuggestionsAdapter.SuggestionHolder> {

    CustomItemClickListener listener;
    DBHelper mDBHelper;

    public CustomSuggestionsAdapter(LayoutInflater inflater, DBHelper mDBHelper) {
        super(inflater);
        this.mDBHelper = mDBHelper;
    }

    @Override
    public int getSingleViewHeight() {
        return 80;
    }

    @Override
    public SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.item_custom_suggestion, parent, false);
        final SuggestionHolder mViewHolder = new SuggestionHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getAdapterPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void addSuggestion(Recipe r) {
        super.addSuggestion(r);
    }

    @Override
    public void onBindSuggestionHolder(Recipe suggestion, SuggestionHolder holder, int position) {
        holder.title.setText(suggestion.getName());
        holder.subtitle.setText(suggestion.getType());

    }

    public Recipe getItem(int position) {
        return suggestions.get(position);
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults results = new FilterResults();
//                String term = constraint.toString();
//                if(term.isEmpty()) {
//                    suggestions = suggestions_clone;
//                }
//                else {
//                    suggestions = new ArrayList<>();
//                    for (Recipe item: suggestions_clone)
//                        if(item.getName().toLowerCase().contains(term.toLowerCase()))
//                            addSuggestion(item);
//                }
//
//
//                results.values = suggestions;
//                return results;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                suggestions = (ArrayList<Recipe>) results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    @Override
    public void onBindViewHolder(final SuggestionHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.passingValue = getItem(position).getName();
                Intent intent = new Intent(holder.itemView.getContext(), RecipeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    static class SuggestionHolder extends RecyclerView.ViewHolder{
        protected TextView title;
        protected TextView subtitle;
        protected ImageView image;

        public SuggestionHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        }
    }

    public interface CustomItemClickListener {
        void onItemClick(View v, int position);
    }

}
