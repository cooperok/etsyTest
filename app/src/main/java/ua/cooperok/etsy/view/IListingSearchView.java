package ua.cooperok.etsy.view;

import java.util.List;

import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Listing;

public interface IListingSearchView extends IDataLoadingView {

    void setCategories(List<Category> categories);

    void showSearchResultView(List<Listing> listings);

}