package ua.cooperok.etsy.presenter;

public interface ISavedListingsPresenter extends IListingPresenter, IBasePresenter {

    /**
     * Loading saved list listings
     */
    void loadSavedListings();

    /**
     * Loading more listings if it's available, using for pagination
     */
    void loadMoreListings();

}