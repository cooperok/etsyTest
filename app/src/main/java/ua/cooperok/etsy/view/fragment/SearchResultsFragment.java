package ua.cooperok.etsy.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ua.cooperok.etsy.R;
import ua.cooperok.etsy.dagger.components.DaggerViewComponent;
import ua.cooperok.etsy.dagger.components.DataServiceComponent;
import ua.cooperok.etsy.dagger.components.ViewComponent;
import ua.cooperok.etsy.dagger.module.ViewModule;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.presenter.IBasePresenter;
import ua.cooperok.etsy.presenter.ISearchResultPresenter;
import ua.cooperok.etsy.view.ISearchResultView;
import ua.cooperok.etsy.view.MarginDecoration;
import ua.cooperok.etsy.view.activity.MainActivity;
import ua.cooperok.etsy.view.adapter.BaseRecyclerAdapter;
import ua.cooperok.etsy.view.adapter.ListingsAdapter;

public class SearchResultsFragment extends ListingsLoadingFragment implements ISearchResultView, ListingsAdapter.OnListingClickListener {

    private static final String KEYWORDS_KEY = "keywords";

    private static final String CATEGORY_KEY = "category";

    private static final int COLUMNS_SIZE = 2;

    @Inject
    ISearchResultPresenter mPresenter;

    private ListingsAdapter mAdapter;

    private String mKeywords;

    private Category mCategory;

    public static SearchResultsFragment getInstance(String keywords, Category category) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEYWORDS_KEY, keywords);
        bundle.putParcelable(CATEGORY_KEY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(CATEGORY_KEY) && getArguments().containsKey(KEYWORDS_KEY)) {
            mKeywords = getArguments().getString(KEYWORDS_KEY);
            mCategory = getArguments().getParcelable(CATEGORY_KEY);
        } else {
            throw new IllegalArgumentException("Must be created through newInstance");
        }
    }

    @Override
    protected void updateView(View view) {
        mAdapter = new ListingsAdapter();
        mAdapter.setOnListingClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new MarginDecoration(getResources().getDimensionPixelOffset(R.dimen.listings_recycler_items_margin)));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), COLUMNS_SIZE));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void setUpComponent(DataServiceComponent component) {
        ViewComponent viewComponent = DaggerViewComponent.builder()
                .dataServiceComponent(component)
                .viewModule(new ViewModule(this))
                .build();

        viewComponent.inject(this);
    }

    @Override
    public void showListingDetailView(Listing listing) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_out_right, R.anim.slide_in_right)
                .replace(MainActivity.CONTAINER_ID, ListingDetailFragment.getInstance(listing))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onListingClicked(Listing listing) {
        mPresenter.onListingClick(listing);
    }

    @Override
    public void showSearchResult(List<Listing> listings) {
        mListings = listings;
        mAdapter.addAll(listings);
    }

    @Override
    public void onMoreListingsLoaded(List<Listing> listings) {
        mListings.addAll(listings);
        mAdapter.addAll(listings);
    }

    @Override
    public void showPreload() {
        showProgress();
    }

    @Override
    public void hidePreload() {
        hideProgress();
    }

    @Override
    public void onEmptySearch() {
        onEmpty();
    }

    @Override
    public void onLoadError() {
        onError();
    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected void setListings(List<Listing> listings) {
        mListings = listings;
        mAdapter.clear();
        mAdapter.addAll(listings);
    }

    @Override
    protected void requestListings() {
        mPresenter.searchListings(mCategory, mKeywords);
    }
}