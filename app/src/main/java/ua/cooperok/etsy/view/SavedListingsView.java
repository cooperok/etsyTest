package ua.cooperok.etsy.view;

import java.util.List;

import ua.cooperok.etsy.data.model.Listing;

public interface SavedListingsView extends DataLoadingView, ListingView {

    void setListings(List<Listing> listings);

    void showListingDetailView(Listing listing);

}