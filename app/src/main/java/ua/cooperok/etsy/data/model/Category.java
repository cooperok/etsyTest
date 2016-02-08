package ua.cooperok.etsy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Категория товара
 */
public class Category implements Parcelable {

    @SerializedName("category_id")
    @Expose
    private long mId;

    @SerializedName("page_title")
    @Expose
    private String mTitle;

    @SerializedName("category_name")
    @Expose
    private String mName;

    public Category(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mName = in.readString();
    }

    public Category(long id, String name, String title) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mTitle);
        dest.writeString(mName);
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}