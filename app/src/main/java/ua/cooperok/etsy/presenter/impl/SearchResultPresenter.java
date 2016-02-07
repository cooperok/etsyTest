package ua.cooperok.etsy.presenter.impl;

import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.presenter.ISearchResultPresenter;
import ua.cooperok.etsy.view.ISearchResultView;

public class SearchResultPresenter implements ISearchResultPresenter {

    public SearchResultPresenter(ISearchResultView view) {
    }

    @Override
    public void onListingClick(Listing listing) {

    }

    @Override
    public void searchListings(Category catalog, String search) {

    }

    @Override
    public void loadMoreListings() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
}