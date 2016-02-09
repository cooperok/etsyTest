package ua.cooperok.etsy.presenter;

import ua.cooperok.etsy.data.model.Category;

public interface IListingSearchPresenter {

    void loadCategories();

    void searchListings(String searchText);

    void onCategorySelected(Category category);

}