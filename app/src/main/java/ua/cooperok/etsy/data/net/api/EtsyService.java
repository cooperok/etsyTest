package ua.cooperok.etsy.data.net.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Image;
import ua.cooperok.etsy.data.model.Listing;

public interface EtsyService {

    @GET("/v2/taxonomy/categories?api_key={apiKey}")
    void getCategories(@Path("apiKey") String apiKey, Callback<List<Category>> callback);

    @GET("/v2/listings/{listingId}?api_key={apiKey}")
    void getListing(@Path("apiKey") String apiKey, @Path("listingId") long listingId, Callback<Listing> callback);

    @GET("/v2/listings/{listingId}/images?api_key={apiKey}")
    void getListingImages(@Path("apiKey") String apiKey, @Path("listingId") long listingId, Callback<List<Image>> callback);

    @GET("/v2/listings/active?api_key={apiKey}&category={categoryName}&keywords={keywords}")
    void getListings(@Path("apiKey") String apiKey, @Path("categoryName") String categoryName, @Path("keywords") String keywords, Callback<List<Listing>> callback);

}