package ua.cooperok.etsy.presenter;

import ua.cooperok.etsy.data.model.Listing;

public interface IListingDetailPresenter extends IListingPresenter, IBasePresenter {

    void loadListing(Listing listing);

}