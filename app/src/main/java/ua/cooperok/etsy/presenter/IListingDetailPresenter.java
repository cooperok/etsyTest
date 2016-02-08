package ua.cooperok.etsy.presenter;

import ua.cooperok.etsy.data.model.Listing;

public interface IListingDetailPresenter extends IListingPresenter, IBasePresenter {

    void loadListingInfo(Listing listing);

    void onSaveListingClick(Listing listing);

    void onRemoveFromSavedClick(Listing listing);

}