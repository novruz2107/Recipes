package com.eazi.recipes.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eazi.recipes.IngSearch;
import com.eazi.recipes.R;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import java.util.ArrayList;

import static android.R.attr.type;

/**
 * Created by Novruz Engineer on 10/28/2018.
 */

public class DBHelper extends SQLiteAssetHelper {

    public static String DATABASE_NAME = "recipes.db";
    public static String NAME_COLUMN = "Name";
    public static String INGREDIENTS_COLUMN = "Ingredients";
    private static String HEALTH_COLUMN = "Health";
    public static String TYPE_COLUMN = "Type";
    private static String CUISINE_COLUMN = "Cuisine";
    private static final int DATABASE_VERSION = 1;
    public int numberOfIng;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //While upgrading database file, uncomment the lines below!!!
//        File file = new File(context.getApplicationInfo().dataDir + "/databases/recipes.db");
//        file.delete();
        if(IngSearch.ingredients == null){

        }else {
            numberOfIng = IngSearch.ingredients.size();
        }
    }

    public ArrayList<Recipe> getRecipeByType(String type){
        ArrayList<Recipe> recipes = new ArrayList<>();
        String query = "SELECT Name, Type FROM Recipes0 WHERE " + TYPE_COLUMN + " ='" + type +"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Recipe recipe = new Recipe();
                recipe.setName(c.getString(c.getColumnIndex(NAME_COLUMN)));
                recipe.setType(c.getString(c.getColumnIndex(TYPE_COLUMN)));
                recipe.setPhoto(R.drawable.breakfast);
                recipes.add(recipe);
            }while(c.moveToNext());
        }
        return recipes;
    }

    public Recipe getRecipeByName(String name){
        Recipe recipe = new Recipe();
        String query = "SELECT Name, Type FROM Recipes0 WHERE " + NAME_COLUMN + " ='" + name +"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                recipe.setPhoto(R.drawable.cakes);
                recipe.setName(c.getString(c.getColumnIndex(NAME_COLUMN)));
                recipe.setType(c.getString(c.getColumnIndex(TYPE_COLUMN)));
            } while (c.moveToNext());
        }
        return recipe;
    }

    public ArrayList<Recipe> getAllRecipes(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        String query = "SELECT Name, Type FROM Recipes0";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Recipe recipe = new Recipe();
                recipe.setName(c.getString(c.getColumnIndex(NAME_COLUMN)));
                recipe.setType(c.getString(c.getColumnIndex(TYPE_COLUMN)));
                recipes.add(recipe);
            }while(c.moveToNext());
        }
        return recipes;
    }

    public ArrayList<Recipe> getRecipePriorToIngredient(){
        ArrayList<Recipe> recipes = new ArrayList<>();

        for(int i=0; i<numberOfIng; i++) {
            String selectQuery = "CREATE VIEW IF NOT EXISTS Recipes" + (i+1) + " AS SELECT * FROM " +
                    "Recipes"+ i + " WHERE " + INGREDIENTS_COLUMN + " LIKE '%" + IngSearch.ingredients.get(i) + "%'";
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(selectQuery);
        }

        String selectQuery = "SELECT * FROM Recipes" + numberOfIng;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                Recipe recipe = new Recipe();
                recipe.setName(c.getString(c.getColumnIndex(NAME_COLUMN)));
                recipe.setIngredients(c.getString(c.getColumnIndex(INGREDIENTS_COLUMN)));
                recipe.setHealth(c.getString(c.getColumnIndex(HEALTH_COLUMN)));
                recipe.setType(c.getString(c.getColumnIndex(TYPE_COLUMN)));
                recipe.setCuisine(c.getString(c.getColumnIndex(CUISINE_COLUMN)));
                recipes.add(recipe);
            }while(c.moveToNext());
        }

        return recipes;
    }

    public ArrayList<Recipe> findByWord(String word){
        ArrayList<Recipe> recipes = new ArrayList<>();
        String query = "SELECT Name, Type FROM Recipes0 WHERE " + NAME_COLUMN +" LIKE '%" + word + "%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Recipe recipe = new Recipe();
                recipe.setName(c.getString(c.getColumnIndex(NAME_COLUMN)));
                recipe.setType(c.getString(c.getColumnIndex(TYPE_COLUMN)));
                recipes.add(recipe);
            }while(c.moveToNext());
        }
        return recipes;

    }

    public void dropViews(){
        for(int i=0; i<numberOfIng; i++){
            String query = "DROP VIEW IF EXISTS Recipes" + (i+1);
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }


    @Override
    public void close() {
        super.close();
    }

    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
