package ua.cooperok.etsy.data.net.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Image;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.data.model.Result;

public interface EtsyService {

    @GET("/v2/taxonomy/categories")
    Call<Result<Category>> getCategories(@Query("api_key") String apiKey);

    @GET("/v2/listings/{listingId}")
    Call<Result<Listing>> getListing(@Path("listingId") long listingId, @Query("api_key") String apiKey);

    @GET("/v2/listings/{listingId}/images")
    Call<Result<Image>> getListingImages(@Path("listingId") long listingId, @Query("api_key") String apiKey);

    @GET("/v2/listings/active")
    Call<Result<Listing>> getListings(@Query("api_key") String apiKey, @Query("category") String categoryName, @Query("keywords") String keywords);

}