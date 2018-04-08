package me.abdullahalrifat.firebase.model;

import java.net.URL;

/**
 * Created by Abdullah Al Rifat on 08-Apr-18.
 */

public class Item
{
    private int itemId;
    private String imageUrl;
    private String Name;
    private String Description;

    public Item() {
    }

    public Item(int itemId, String imageUrl, String name, String description) {
        this.itemId = itemId;
        this.imageUrl = imageUrl;
        Name = name;
        Description = description;
    }

    public Item(String imageUrl, String name, String description) {
        this.imageUrl = imageUrl;
        Name = name;
        Description = description;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
