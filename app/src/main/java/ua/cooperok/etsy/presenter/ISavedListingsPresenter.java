package ua.cooperok.etsy.presenter;

import ua.cooperok.etsy.data.model.Listing;

public interface ISavedListingsPresenter extends IListingPresenter, IBasePresenter {

    /**
     * Loading saved list listings
     */
    void loadSavedListings();

    /**
     * Loading more listings if it's available, using for pagination
     */
    void loadMoreListings();

    void onListingClick(Listing listing);

}