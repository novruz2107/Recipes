package com.eazi.recipes;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eazi.recipes.Helpers.DBHelper;
import com.eazi.recipes.Helpers.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Novruz Engineer on 11/3/2018.
 */

public class RecipeActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    TextView name, type, desc;
    ImageView makeFav;
    String value = MainActivity.passingValue;
    private final String YOUTUBE_API = "AIzaSyDCmkrGP6OhyjYqf6P1-RETR8mmQqHE8yE";
    private YouTubePlayerFragment playerFragment;
    private YouTubePlayer mPlayer;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recipe_activity);

        name = findViewById(R.id.nameOfRecipe);
        type = findViewById(R.id.typeOfRecipe);
        desc = findViewById(R.id.descOfRecipe);
        makeFav = findViewById(R.id.make_favorite);

        makeUI();

        playerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_player_fragment);

        playerFragment.initialize(YOUTUBE_API, this);

        final String uid = FirebaseAuth.getInstance().getUid();
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            final DatabaseReference ref = myRef.child("users").child(uid).child("favs");
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(dataSnapshot.child(recipe.getName()).exists()){
                                        makeFav.setBackgroundResource(R.drawable.ic_favorite);
                                    }else{
                                        makeFav.setBackgroundResource(R.drawable.ic_favorite_border);
                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

            makeFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeFav.animate().scaleX(1.2f).scaleY(1.2f).setDuration(500);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(recipe.getName()).exists()){
                                dataSnapshot.child(recipe.getName()).getRef().removeValue();
                                removeFavRecipeOffline();
                                makeFav.setBackgroundResource(R.drawable.ic_favorite_border);
                                makeFav.animate().scaleX(1.0f).scaleY(1.0f).setDuration(500);
                                Snackbar.make(findViewById(android.R.id.content), "Removed from favorites", Snackbar.LENGTH_SHORT).show();
                            }else{
                                myRef.child("users").child(uid).child("favs").child(recipe.getName()).setValue(recipe).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        makeFav.animate().scaleX(1.0f).scaleY(1.0f).setDuration(500);
                                        saveFavRecipeOffline();
                                        makeFav.setBackgroundResource(R.drawable.ic_favorite);
                                        Snackbar.make(findViewById(android.R.id.content), "Added to favorites", Snackbar.LENGTH_SHORT).show();
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Snackbar.make(findViewById(android.R.id.content), "Error", Snackbar.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });
        }else{

        }



    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        mPlayer = player;

        //Enables automatic control of orientation
        mPlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);

        //Show full screen in landscape mode always
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);

        //System controls will appear automatically
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);

        if (!wasRestored) {
            mPlayer.loadVideo("X5oD_thIk3c");
        }
        else
        {
            mPlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        mPlayer = null;
    }

    private void makeUI(){
        final DBHelper dbHelper = new DBHelper(getApplicationContext());
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                recipe = dbHelper.getRecipeByName(MainActivity.passingValue);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        name.setText(recipe.getName());
                        type.setText(recipe.getType());
                        desc.setText("This is for how to cook the meal\n \n");
                    }
                });

                final SharedPreferences prefs = getSharedPreferences("FAVS", 0);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(prefs.getString(recipe.getName(), "").equals(recipe.getName())){
                            makeFav.setBackgroundResource(R.drawable.ic_favorite_border);
                        }else{
                            makeFav.setBackgroundResource(R.drawable.ic_favorite);
                        }
                    }
                });

            }
        });
    }

    private void removeFavRecipeOffline(){
        SharedPreferences prefs = getSharedPreferences("FAVS", 0);
        prefs.edit().remove(recipe.getName());
    }

    private void saveFavRecipeOffline(){
        SharedPreferences prefs = getSharedPreferences("FAVS", 0);
        prefs.edit().putString(recipe.getName(), recipe.getName()).apply();
    }
}
