package com.eazi.recipes.Helpers;

/**
 * Created by Novruz Engineer on 10/28/2018.
 */

public class Recipe implements java.io.Serializable {

    private String name;
    private String ingredients;
    private String health;
    private String type;
    private String cuisine;
    private String description;
    private String youtube;
    private int photo;

    public Recipe(){

    }

    public Recipe(int photo, String name){
        this.photo = photo;
        this.name = name;
    }

    public Recipe(String name, String type){
        this.name = name;
        this.type = type;
    }


    public Recipe(String name, String ingredients, String health, String type, String cuisine) {
        this.name = name;
        this.ingredients = ingredients;
        this.health = health;
        this.type = type;
        this.cuisine = cuisine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
