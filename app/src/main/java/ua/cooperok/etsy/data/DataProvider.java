package ua.cooperok.etsy.data;

import java.util.List;

import ua.cooperok.etsy.app.CheckConnection;
import ua.cooperok.etsy.data.db.DatabaseDataProvider;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Image;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.data.net.NetDataProvider;

/**
 * Provides data from different sources, depends on internet connection
 */
public class DataProvider implements IDataProvider {

    private NetDataProvider mNetProvider;

    private DatabaseDataProvider mDbProvider;

    public DataProvider(NetDataProvider netDataProvider, DatabaseDataProvider databaseDataProvider) {
        mNetProvider = netDataProvider;
        mDbProvider = databaseDataProvider;
    }

    @Override
    public void requestListing(long listingId, Callback<Listing> callback) {
        getProvider().requestListing(listingId, callback);
    }

    @Override
    public void requestListingImages(long listingId, Callback<List<Image>> callback) {
        getProvider().requestListingImages(listingId, callback);
    }

    @Override
    public void requestListings(Category category, String search, int offset, int limit, Callback<List<Listing>> callback) {
        getProvider().requestListings(category, search, offset, limit, callback);
    }

    @Override
    public void requestCategories(Callback<List<Category>> callback) {
        getProvider().requestCategories(callback);
    }

    @Override
    public void requestSavedList(int offset, int limit, Callback<List<Listing>> callback) {
        //this functionality available only locally
        mDbProvider.requestSavedList(offset, limit, callback);
    }

    @Override
    public void checkListingInSavedList(long listingId, Callback<Boolean> callback) {
        //this functionality available only locally
        mDbProvider.checkListingInSavedList(listingId, callback);
    }

    @Override
    public void addListingToSavedList(long listingId) {
        //this functionality available only locally
        mDbProvider.addListingToSavedList(listingId);
    }

    @Override
    public void removeListingFromSavedList(long listingId) {
        //this functionality available only locally
        mDbProvider.removeListingFromSavedList(listingId);
    }

    /**
     * Выбирает подходящего поставщика данных, проверяя наличие интернет соединения
     */
    private IDataProvider getProvider() {
        return CheckConnection.isInternetAvailable() ? mNetProvider : mDbProvider;
    }

}