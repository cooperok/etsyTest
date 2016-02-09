package ua.cooperok.etsy.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import ua.cooperok.etsy.R;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.view.adapter.BaseRecyclerAdapter;
import ua.cooperok.etsy.view.widget.ProgressWheel;

public abstract class ListingsLoadingFragment extends BaseFragment {

    private static final String KEY_LISTINGS = "listings";

    @Bind(R.id.listings_recycler)
    RecyclerView mRecyclerView;

    @Bind(R.id.progress_wheel)
    ProgressWheel mProgressWheel;

    @Bind(R.id.listings_message)
    TextView mMessage;

    protected List<Listing> mListings;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //trying to restore data and if there is nothing loading them
        if (savedInstanceState == null) {
            requestListings();
        } else {
            mListings = savedInstanceState.getParcelableArrayList(KEY_LISTINGS);
            if (mListings != null) {
                setListings(mListings);
            } else {
                requestListings();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mListings != null) {
            outState.putParcelableArrayList(KEY_LISTINGS, (ArrayList<? extends Parcelable>) mListings);
        }
    }

    public void onEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressWheel.setVisibility(View.GONE);
        mMessage.setVisibility(View.VISIBLE);
    }

    public void showProgress() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressWheel.spin();
        mProgressWheel.setVisibility(View.VISIBLE);
        mMessage.setVisibility(View.GONE);
    }

    public void hideProgress() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressWheel.setVisibility(View.GONE);
        mMessage.setVisibility(View.GONE);
    }

    public void onError() {
        mProgressWheel.setVisibility(View.GONE);
        Toast.makeText(getContext(), R.string.load_error, Toast.LENGTH_LONG).show();
    }

    protected abstract BaseRecyclerAdapter getAdapter();

    protected abstract void setListings(List<Listing> listings);

    protected abstract void requestListings();

}