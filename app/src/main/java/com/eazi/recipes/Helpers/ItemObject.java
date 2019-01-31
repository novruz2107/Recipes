package com.eazi.recipes.Helpers;

/**
 * Created by Novruz Engineer on 10/24/2018.
 */

public class ItemObject {

    private int image;
    private String desc;
    private boolean isSelected;

    public ItemObject(int image, String desc) {
        this.image = image;
        this.desc = desc;
        this.isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
