package ua.cooperok.etsy.presenter;

import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Listing;

public interface ISearchResultPresenter extends IBasePresenter {

    void onListingClick(Listing listing);

    void searchListings(Category catalog, String search);

    void loadMoreListings();

}