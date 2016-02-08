package ua.cooperok.etsy.data;

import java.util.List;

import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Image;
import ua.cooperok.etsy.data.model.Listing;

/**
 * Поставщик данных. Каждый метод на получение данных имеет дополонительный callback параметр для возможности получать
 * данные асинхронно
 */
public interface IDataProvider {

    void requestListing(long listingId, Callback<Listing> callback);

    void requestListingImages(long listingId, Callback<List<Image>> callback);

    void requestListings(Category categoryId, String search, int offset, int limit, Callback<List<Listing>> callback);

    void requestCategories(Callback<List<Category>> callback);

    void requestSavedList(long offset, long limit, Callback<List<Listing>> callback);

    void addListingToSavedList(long listingId);

    void removeListingToSavedList(long listingId);

}