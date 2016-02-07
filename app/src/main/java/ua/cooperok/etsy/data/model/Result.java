package ua.cooperok.etsy.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Вспомогательный класс для маппинга. Все данные API отдает заворачивая в отдельный объект, а сами данные всегда лежат в поле result
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