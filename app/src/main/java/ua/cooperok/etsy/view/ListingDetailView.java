package ua.cooperok.etsy.view;

import ua.cooperok.etsy.data.model.Listing;

public interface ListingDetailView extends DataLoadingView, ListingView {

    void setListing(Listing listing);

}