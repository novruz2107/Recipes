package com.eazi.recipes.Helpers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eazi.recipes.MainActivity;
import com.eazi.recipes.R;
import com.eazi.recipes.RecipeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Novruz Engineer on 11/18/2018.
 */

public class FavRecyclerViewAdapter extends RecyclerView.Adapter<FavViewHolder> {
    private List<Recipe> itemList;
    private Context context;

    public FavRecyclerViewAdapter(Context context,
                                     List<Recipe> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fav_list_item, null);
        FavViewHolder rcv = new FavViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final FavViewHolder holder, final int position)
    {
        holder.image.setImageResource(R.drawable.azerbaijan);
        holder.name.setText(itemList.get(position).getName());
        holder.type.setText(itemList.get(position).getType());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove from favorites
                holder.itemView.animate().alpha(0f).setDuration(400);
                final String uid = FirebaseAuth.getInstance().getUid();
                final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference ref = myRef.child("users").child(uid).child("favs");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        dataSnapshot.child(itemList.get(position).getName()).getRef().removeValue();
                        itemList.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
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
