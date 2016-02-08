package ua.cooperok.etsy.view;

import java.util.List;

import ua.cooperok.etsy.data.model.Category;

public interface IListingSearchView extends IDataLoadingView {

    /**
     * Setting loaded categories to view
     *
     * @param categories list of categories, which size is always > 0
     */
    void setCategories(List<Category> categories);

    /**
     * Called to notify use that loading was successful but there is any data
     */
    void onEmptyCategories();

    void showSearchResultView(String keywords, Category category);

}