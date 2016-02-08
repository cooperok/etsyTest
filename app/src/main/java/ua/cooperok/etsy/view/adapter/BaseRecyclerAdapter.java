package ua.cooperok.etsy.view.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all RecyclerViews. Incapsulates logic of storing, retrieving and removing data
 *
 * @param <T>  type of storing data
 * @param <VH> type of ViewHolder created by adapter
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<T> mData;

    public BaseRecyclerAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * Adding one object to the end of the array
     *
     * @param item
     */
    public void addItem(T item) {
        if (item != null) {
            int position = mData.size();
            mData.add(item);
            notifyItemInserted(position);
        }
    }

    /**
     * Adding list of objects to the end of the array
     *
     * @param items
     */
    public void addAll(List<T> items) {
        if (items != null && items.size() > 0) {
            int start = mData.size();
            mData.addAll(items);
            notifyItemRangeInserted(start, items.size());
        }
    }

    /**
     * Returns list of data
     */
    public List<T> getData() {
        return mData;
    }

    /**
     * Gives ability to get object by it's position
     *
     * @param position
     * @return object from given position if it's avalaible, or null otherwise
     */
    public T getItem(int position) {
        T item = null;
        if (position >= 0 && position < mData.size()) {
            item = mData.get(position);
        }
        return item;
    }

    /**
     * Clears data
     */
    public void clear() {
        int size = mData.size();
        mData.clear();
        notifyItemRangeRemoved(0, size);
    }

}