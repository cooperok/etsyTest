package ua.cooperok.etsy.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Категория товара
 */
public class Category {

    @SerializedName("category_id")
    @Expose
    private long mId;

    @SerializedName("page_title")
    @Expose
    private String mTitle;

    @SerializedName("category_name")
    @Expose
    private String mName;

    public Category(int id, String name, String title) {
        mId = id;
        mName = name;
        mTitle = title;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getTitle() {
        return mTitle;
    }
}