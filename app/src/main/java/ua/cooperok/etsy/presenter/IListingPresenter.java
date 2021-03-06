package ua.cooperok.etsy.presenter;

import ua.cooperok.etsy.data.model.Listing;

public interface IListingPresenter {

    void addListingToSavedList(Listing listing);

    void removeListingFromSavedList(Listing listing);

}