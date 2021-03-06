package ua.cooperok.etsy.view;

import java.util.List;

import ua.cooperok.etsy.data.model.Listing;

public interface ISavedListingsView extends IDataLoadingView {

    void setListings(List<Listing> listings);

    void showListingDetailView(Listing listing);

    void onEmptyResult();

    void onMoreListingsLoaded(List<Listing> listings);
}