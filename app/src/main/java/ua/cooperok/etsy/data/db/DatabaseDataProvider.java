package ua.cooperok.etsy.data.db;

import java.util.List;

import ua.cooperok.etsy.data.Callback;
import ua.cooperok.etsy.data.IDataProvider;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Image;
import ua.cooperok.etsy.data.model.Listing;

/**
 * Provides data from local database
 */
public class DatabaseDataProvider implements IDataProvider {

    @Override
    public void requestListing(long listingId, Callback<Listing> callback) {

    }

    @Override
    public void requestListingImages(long listingId, Callback<List<Image>> callback) {

    }

    @Override
    public void requestListings(Category category, String search, int offset, int limit, Callback<List<Listing>> callback) {

    }

    @Override
    public void requestCategories(Callback<List<Category>> callback) {

    }

    @Override
    public void requestSavedList(int offset, int limit, Callback<List<Listing>> callback) {

    }

    @Override
    public void checkListingInSavedList(long listingId, Callback<Boolean> callback) {

    }

    @Override
    public void addListingToSavedList(long listingId) {

    }

    @Override
    public void removeListingFromSavedList(long listingId) {

    }
}