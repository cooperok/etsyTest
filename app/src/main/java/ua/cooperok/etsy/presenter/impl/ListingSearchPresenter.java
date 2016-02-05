package ua.cooperok.etsy.presenter.impl;

import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.presenter.IListingSearchPresenter;
import ua.cooperok.etsy.view.IListingSearchView;

public class ListingSearchPresenter implements IListingSearchPresenter {

    private IListingSearchView mView;

    public ListingSearchPresenter(IListingSearchView view) {
        mView = view;
    }

    @Override
    public void loadCategories() {

    }

    @Override
    public void searchListings(String searchText) {

    }

    @Override
    public void onCategorySelected(Category category) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

}