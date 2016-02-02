package ua.cooperok.etsy.presenter;

import ua.cooperok.etsy.data.model.Listing;

public interface ListingDetailPresenter extends ListingPresenter, BasePresenter {

    void loadListing(Listing listing);

}