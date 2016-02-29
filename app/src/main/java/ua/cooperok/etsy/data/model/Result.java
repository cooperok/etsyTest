package ua.cooperok.etsy.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Additional class for mappign. All data received from API are wrapped with object, but all needed data is inside field "result"
 *
 * @param <T>
 */
public class Result<T> {

    @SerializedName("count")
    @Expose
    private int mCount;

    @SerializedName("results")
    @Expose
    private List<T> mResult;

    public int getCount() {
        return mCount;
    }

    public List<T> getResult() {
        return mResult;
    }

}