package ua.cooperok.etsy.presenter.impl;

import java.util.List;

import ua.cooperok.etsy.data.Callback;
import ua.cooperok.etsy.data.DataProvider;
import ua.cooperok.etsy.data.model.Image;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.presenter.ISavedListingsPresenter;
import ua.cooperok.etsy.view.ISavedListingsView;

public class SavedListingPresenter implements ISavedListingsPresenter {

    private DataProvider mDataProvider;

    private ISavedListingsView mView;

    private int mLimit = 20;

    private int mOffset = 0;

    public SavedListingPresenter(ISavedListingsView view, DataProvider dataProvider) {
        mDataProvider = dataProvider;
        mView = view;
    }

    @Override
    public void loadSavedListings() {
        mOffset = 0;
        mView.showPreload();
        mDataProvider.requestSavedList(mOffset, mLimit, new Callback<List<Listing>>() {
            @Override
            public void onDataReceived(List<Listing> data) {
                mView.hidePreload();
                mOffset += mLimit;
                if (data.isEmpty()) {
                    mView.onEmptyResult();
                } else {
                    mView.setListings(data);
                }
                loadListingsImages(data);
            }

            @Override
            public void onError() {
                mView.hidePreload();
                mView.onLoadError();
            }
        });
    }

    private void loadListingsImages(List<Listing> data) {
        //for every listing sending request for images
        for (final Listing listing : data) {
            mDataProvider.requestListingImages(listing.getId(), new Callback<List<Image>>() {
                @Override
                public void onDataReceived(List<Image> data) {
                    listing.addImages(data);
                }

                @Override
                public void onError() {

                }
            });
        }
    }

    @Override
    public void loadMoreListings() {
        mDataProvider.requestSavedList(mOffset, mLimit, new Callback<List<Listing>>() {

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
    public void onListingClick(Listing listing) {
        mView.showListingDetailView(listing);
    }

    @Override
    public void addListingToSavedList(Listing listing) {
        mDataProvider.addListingToSavedList(listing.getId());
    }

    @Override
    public void removeListingFromSavedList(Listing listing) {
        mDataProvider.removeListingFromSavedList(listing.getId());
    }

    public void setLimit(int limit) {
        mLimit = limit;
    }
}