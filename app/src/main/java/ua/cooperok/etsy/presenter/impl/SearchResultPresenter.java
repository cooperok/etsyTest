package ua.cooperok.etsy.presenter.impl;

import java.util.List;

import javax.inject.Inject;

import ua.cooperok.etsy.data.Callback;
import ua.cooperok.etsy.data.DataProvider;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.presenter.ISearchResultPresenter;
import ua.cooperok.etsy.view.ISearchResultView;

public class SearchResultPresenter implements ISearchResultPresenter {

    private ISearchResultView mView;

    private int mOffset = 0;

    /**
     * Default limit value
     */
    private int mLimit = 20;

    private Category mCategory;

    private String mKeywords;

    private DataProvider mDataProvider;

    public SearchResultPresenter(ISearchResultView view, DataProvider dataProvider) {
        super();
        mView = view;
        mDataProvider = dataProvider;
    }

    @Override
    public void onListingClick(Listing listing) {
        mView.showListingDetailView(listing);
    }

    @Override
    public void searchListings(Category catalog, String search) {
        mCategory = catalog;
        mKeywords = search;
        mDataProvider.requestListings(catalog, search, mOffset, mLimit, new Callback<List<Listing>>() {
            @Override
            public void onDataReceived(List<Listing> data) {
                mOffset += mLimit;
                if (data.isEmpty()) {
                    mView.onEmptySearch();
                } else {
                    mView.showSearchResult(data);
                }
            }

            @Override
            public void onError() {
                mView.onLoadError();
            }
        });
    }

    @Override
    public void loadMoreListings() {
        mDataProvider.requestListings(mCategory, mKeywords, mOffset, mLimit, new Callback<List<Listing>>() {
            @Override
            public void onDataReceived(List<Listing> data) {
                mOffset += mLimit;
                mView.onMoreListingsLoaded(data);
            }

            @Override
            public void onError() {
            }
        });
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    public void setLimit(int limit) {
        mLimit = limit;
    }

}