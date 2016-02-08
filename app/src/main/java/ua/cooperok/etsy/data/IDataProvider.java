package ua.cooperok.etsy.data;

import java.util.List;

import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Image;
import ua.cooperok.etsy.data.model.Listing;

/**
 * Data provider. Every method have additional callback parameter for async load
 */
public interface IDataProvider {

    /**
     * Loading listing information
     *
     * @param listingId id of listing
     * @param callback
     */
    void requestListing(long listingId, Callback<Listing> callback);

    /**
     * Loading listing images. Images stores separately and have different URL's with different image sizes
     *
     * @param listingId if of listing
     * @param callback
     */
    void requestListingImages(long listingId, Callback<List<Image>> callback);

    /**
     * Loading listings list from certain category by matching search text. Have additional parameters offset and limit
     * to be able download listings by parts
     *
     * @param category category
     * @param search   search text
     * @param offset
     * @param limit
     * @param callback
     */
    void requestListings(Category category, String search, int offset, int limit, Callback<List<Listing>> callback);

    /**
     * Loading list of top level categories
     *
     * @param callback
     */
    void requestCategories(Callback<List<Category>> callback);

    /**
     * Loading listings which was saved by user in custom list. Have additional parameters offset and limit
     * to be able download listings by parts
     *
     * @param offset
     * @param limit
     * @param callback
     */
    void requestSavedList(int offset, int limit, Callback<List<Listing>> callback);

    /**
     * Checking if listing present in user's saved list
     *
     * @param listingId
     * @param callback
     */
    void checkListingInSavedList(long listingId, Callback<Boolean> callback);

    /**
     * Adding listing to saved list
     *
     * @param listingId id of listing
     */
    void addListingToSavedList(long listingId);

    /**
     * Removing listing from saved list
     *
     * @param listingId id of listing
     */
    void removeListingFromSavedList(long listingId);

}