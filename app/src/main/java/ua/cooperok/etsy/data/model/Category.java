package ua.cooperok.etsy.data.model;

/**
 * Категория товара
 */
public class Category {

    private int mId;

    private String mTitle;

    private String mName;

    public Category(int id, String name, String title) {
        mId = id;
        mName = name;
        mTitle = title;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getTitle() {
        return mTitle;
    }
}