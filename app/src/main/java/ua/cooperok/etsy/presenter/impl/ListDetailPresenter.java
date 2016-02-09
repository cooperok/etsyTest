package ua.cooperok.etsy.presenter.impl;

import java.util.List;

import ua.cooperok.etsy.data.Callback;
import ua.cooperok.etsy.data.DataProvider;
import ua.cooperok.etsy.data.model.Image;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.presenter.IListingDetailPresenter;
import ua.cooperok.etsy.view.IListingDetailView;

public class ListDetailPresenter implements IListingDetailPresenter {

    private DataProvider mDataProvider;

    private IListingDetailView mView;

    public ListDetailPresenter(IListingDetailView view, DataProvider dataProvider) {
        mDataProvider = dataProvider;
        mView = view;
    }

    @Override
    public void loadListingInfo(final Listing listing) {
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

    @Override
    public void onSaveListingClick(Listing listing) {
        mDataProvider.addListingToSavedList(listing.getId());
        mView.onListingSavedStateChanged(true);
    }

    @Override
    public void onRemoveFromSavedClick(Listing listing) {
        mDataProvider.removeListingFromSavedList(listing.getId());
        mView.onListingSavedStateChanged(false);
    }

    @Override
    public void checkListingState(Listing listing) {
        mDataProvider.checkListingInSavedList(listing.getId(), new Callback<Boolean>() {
            @Override
            public void onDataReceived(Boolean data) {
                mView.onListingSavedStateChanged(data);
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

    @Override
    public void addListingToSavedList(Listing listing) {
        mDataProvider.addListingToSavedList(listing.getId());
    }

    @Override
    public void removeListingFromSavedList(Listing listing) {
        mDataProvider.removeListingFromSavedList(listing.getId());
    }
}