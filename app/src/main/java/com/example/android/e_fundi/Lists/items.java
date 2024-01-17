package com.example.android.e_fundi.Lists;

/**
 * Created by gb on 10/23/18.
 */

public class items {
    private String item_name;
    private String item_shop;
    private String item_date;
    private int item_image;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_shop() {
        return item_shop;
    }

    public void setItem_shop(String item_shop) {
        this.item_shop = item_shop;
    }

    public String getItem_date() {
        return item_date;
    }

    public void setItem_date(String item_date) {
        this.item_date = item_date;
    }

    public items(String date, String ItemNameStr, String ItemShop, int thumbnail) {
        this.item_date = date;
        this.item_name = ItemNameStr;
        this.item_shop = ItemShop;
        this.item_image = thumbnail;
    }

    public int getItem_image() {
        return item_image;
    }

    public void setItem_image(int item_image) {
        this.item_image = item_image;
    }
}
