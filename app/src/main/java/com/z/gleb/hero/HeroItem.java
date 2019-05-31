package com.z.gleb.hero;

import java.util.ArrayList;

public class HeroItem {
    private String title;
    private ArrayList<String> abilities;
    private String imageUrl;
    boolean Favorite;

    public boolean isFavorite() {
        return Favorite;
    }

    public void setFavorite(boolean favorite) {
        Favorite = favorite;
    }

    public HeroItem(String title, ArrayList<String> abilities, String imageUrl) {
        this.title = title;
        this.abilities = abilities;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<String> abilities) {
        this.abilities = abilities;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
