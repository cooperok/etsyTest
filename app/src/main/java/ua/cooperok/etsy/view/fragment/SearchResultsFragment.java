package ua.cooperok.etsy.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
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
import ua.cooperok.etsy.view.adapter.ListingsAdapter;

public class SearchResultsFragment extends BaseFragment implements ISearchResultView {

    private static final String KEYWORDS_KEY = "keywords";

    private static final String CATEGORY_KEY = "category";

    private static final int COLUMNS_SIZE = 2;

    private static final String KEY_LISTINGS = "listings";

    @Inject
    ISearchResultPresenter mPresenter;

    private String mKeywords;

    private Category mCategory;

    @Bind(R.id.listings_recycler)
    RecyclerView mRecyclerView;

    private ListingsAdapter mAdapter;

    private List<Listing> mListings;

    public static SearchResultsFragment getInstance(String keywords, Category category) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEYWORDS_KEY, keywords);
        bundle.putParcelable(CATEGORY_KEY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //trying to restore data and if there is nothing loading them
        if (savedInstanceState == null) {
            mPresenter.searchListings(mCategory, mKeywords);
        } else {
            mListings = savedInstanceState.getParcelableArrayList(KEY_LISTINGS);
            if (mListings != null) {
                showSearchResult(mListings);
            } else {
                mPresenter.searchListings(mCategory, mKeywords);
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

    @Override
    protected void updateView(View view) {
        mAdapter = new ListingsAdapter();
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

    }

    @Override
    public void showSearchResult(List<Listing> listings) {
        mListings = listings;
        mAdapter.addAll(listings);
    }

    @Override
    public void onEmptySearch() {

    }

    @Override
    public void onMoreListingsLoaded(List<Listing> listings) {
        mAdapter.addAll(listings);
    }

    @Override
    public void showPreload() {

    }

    @Override
    public void hidePreload() {

    }

    @Override
    public void onLoadError() {
        Toast.makeText(getContext(), R.string.load_error, Toast.LENGTH_LONG).show();
    }

}