package ua.cooperok.etsy.view;

import ua.cooperok.etsy.data.model.Listing;

public interface IListingDetailView extends IDataLoadingView, IListingView {

    void setListing(Listing listing);

}