package ua.cooperok.etsy.presenter;

import ua.cooperok.etsy.data.model.Listing;

public interface IListingDetailPresenter extends IListingPresenter {

    void loadListingInfo(Listing listing);

    void onSaveListingClick(Listing listing);

    void onRemoveFromSavedClick(Listing listing);

    void checkListingState(Listing listing);

}