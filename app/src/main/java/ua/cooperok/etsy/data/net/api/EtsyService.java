package ua.cooperok.etsy.data.net.api;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Image;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.data.model.Result;

public interface EtsyService {

    @GET("/v2/taxonomy/categories")
    Call<Result<Category>> getCategories(@Query("api_key") String apiKey);

    @GET("/v2/listings/{listingId}")
    Call<Result<Listing>> getListing(@Query("apiKey") String apiKey, @Path("listingId") long listingId);

    @GET("/v2/listings/{listingId}/images")
    Call<Result<Image>> getListingImages(@Query("apiKey") String apiKey, @Path("listingId") long listingId);

    @GET("/v2/listings/active")
    Call<Result<Listing>> getListings(@Query("apiKey") String apiKey, @Query("categoryName") String categoryName, @Query("keywords") String keywords);

}