package ua.cooperok.etsy.data.net;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import ua.cooperok.etsy.app.ApiHelper;
import ua.cooperok.etsy.data.Callback;
import ua.cooperok.etsy.data.IDataProvider;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Image;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.data.model.Result;
import ua.cooperok.etsy.data.net.api.EtsyService;

/**
 * Provides data from Internet
 */
public class NetDataProvider implements IDataProvider {

    private Retrofit mRetrofit;

    private EtsyService mService;

    public NetDataProvider(Retrofit retrofit) {
        mRetrofit = retrofit;
        mService = mRetrofit.create(EtsyService.class);
    }

    @Override
    public void requestListing(long listingId, final Callback<Listing> callback) {
        Call<Result<Listing>> call = mService.getListing(ApiHelper.API_KEY, listingId);
        enqueueCall(call, callback);
    }

    @Override
    public void requestListingImages(long listingId, Callback<List<Image>> callback) {
        Call<Result<Image>> call = mService.getListingImages(ApiHelper.API_KEY, listingId);
        enqueueCallForList(call, callback);
    }

    @Override
    public void requestListings(Category category, String search, int offset, int limit, Callback<List<Listing>> callback) {
        Call<Result<Listing>> call = mService.getListings(ApiHelper.API_KEY, category.getName(), search);
        enqueueCallForList(call, callback);
    }

    @Override
    public void requestCategories(Callback<List<Category>> callback) {
        Call<Result<Category>> call = mService.getCategories(ApiHelper.API_KEY);
        enqueueCallForList(call, callback);
    }

    @Override
    public void requestSavedList(long offset, long limit, Callback<List<Listing>> callback) {
        //This functionality is not required, but it can exist
    }

    @Override
    public void addListingToSavedList(long listingId) {
        //This functionality is not required, but it can exist
    }

    @Override
    public void removeListingToSavedList(long listingId) {
        //This functionality is not required, but it can exist
    }

    /**
     * Used to get single object
     *
     * @param call
     * @param callback
     * @param <T>
     */
    private <T> void enqueueCall(Call<Result<T>> call, final Callback<T> callback) {
        call.enqueue(new retrofit2.Callback<Result<T>>() {

            @Override
            public void onResponse(Response<Result<T>> response) {
                //Result data always is an array, so we should retrieve first element
                //if it's not present to prevent IndexOutOfBoundsException return null
                Result result = response.body();
                if (result.getCount() > 0) {
                    callback.onDataReceived(response.body().getResult().get(0));
                } else {
                    callback.onDataReceived(null);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError();
            }
        });
    }

    /**
     * Used to get list of objects
     *
     * @param call
     * @param callback
     * @param <T>
     */
    private <T> void enqueueCallForList(Call<Result<T>> call, final Callback<List<T>> callback) {
        call.enqueue(new retrofit2.Callback<Result<T>>() {
            @Override
            public void onResponse(Response<Result<T>> response) {
                if (response.body() != null) {
                    callback.onDataReceived(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError();
            }
        });
    }

}