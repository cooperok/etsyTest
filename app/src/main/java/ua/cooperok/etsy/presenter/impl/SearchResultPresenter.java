package ua.cooperok.etsy.presenter.impl;

import java.util.List;

import ua.cooperok.etsy.data.Callback;
import ua.cooperok.etsy.data.DataProvider;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Image;
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
        mOffset = 0;
        mView.showPreload();
        mDataProvider.requestListings(catalog, search, mOffset, mLimit, new Callback<List<Listing>>() {
            @Override
            public void onDataReceived(final List<Listing> listings) {
                mOffset += mLimit;
                if (listings.isEmpty()) {
                    mView.onEmptySearch();
                } else {
                    //If there is listings then we load images for them, and only after that sending full data to view
                    mDataProvider.requestListingsImages(listings, new Callback<Void>() {
                        @Override
                        public void onDataReceived(Void data) {
                            mView.hidePreload();
                            mView.showSearchResult(listings);
                        }

                        @Override
                        public void onError() {
                            mView.hidePreload();
                            mView.showSearchResult(listings);
                        }
                    });
                }
            }

            @Override
            public void onError() {
                mView.hidePreload();
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

    public void setLimit(int limit) {
        mLimit = limit;
    }

}