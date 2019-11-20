package com.example.bettertinder;

public class ItemModel {

    private String title;
    private String city;
    private String imageUrl;

    public ItemModel(String title, String city, String imageUrl) {
        this.title = title;
        this.city = city;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
