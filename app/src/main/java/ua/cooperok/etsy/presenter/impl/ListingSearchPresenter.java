package ua.cooperok.etsy.presenter.impl;

import java.util.List;

import javax.inject.Inject;

import ua.cooperok.etsy.data.Callback;
import ua.cooperok.etsy.data.DataProvider;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.presenter.IListingSearchPresenter;
import ua.cooperok.etsy.view.IListingSearchView;

public class ListingSearchPresenter implements IListingSearchPresenter {

    private IListingSearchView mView;

    private Category mCategory;

    private DataProvider mDataProvider;

    public ListingSearchPresenter(IListingSearchView view, DataProvider dataProvider) {
        mView = view;
        mDataProvider = dataProvider;
    }

    @Override
    public void loadCategories() {
        mDataProvider.requestCategories(new Callback<List<Category>>() {
            @Override
            public void onDataReceived(List<Category> data) {
                if (data.isEmpty()) {
                    mView.onEmptyCategories();
                } else {
                    mView.setCategories(data);
                }
            }

            @Override
            public void onError() {
                mView.onLoadError();
            }
        });
    }

    @Override
    public void searchListings(String searchText) {
        mView.showSearchResultView(searchText, mCategory);
    }

    @Override
    public void onCategorySelected(Category category) {
        mCategory = category;
    }

}