package ua.cooperok.etsy.data.net;

import android.content.Intent;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import ua.cooperok.etsy.app.ApiHelper;
import ua.cooperok.etsy.app.EtsyApplication;
import ua.cooperok.etsy.data.Callback;
import ua.cooperok.etsy.data.IDataProvider;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Image;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.data.model.Result;
import ua.cooperok.etsy.data.net.api.EtsyService;
import ua.cooperok.etsy.service.CacheService;

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
        Call<Result<Listing>> call = mService.getListing(listingId, ApiHelper.API_KEY);
        enqueueCall(call, callback, CacheService.DataType.LISTINGS);
    }

    @Override
    public void requestListingImages(long listingId, Callback<List<Image>> callback) {
        Call<Result<Image>> call = mService.getListingImages(listingId, ApiHelper.API_KEY);
        enqueueCallForList(call, callback, CacheService.DataType.IMAGES);
    }

    @Override
    public void requestListings(Category category, String search, int offset, int limit, Callback<List<Listing>> callback) {
        Call<Result<Listing>> call = mService.getListings(ApiHelper.API_KEY, category.getName(), search);
        enqueueCallForList(call, callback, CacheService.DataType.LISTINGS);
    }

    @Override
    public void requestCategories(Callback<List<Category>> callback) {
        Call<Result<Category>> call = mService.getCategories(ApiHelper.API_KEY);
        enqueueCallForList(call, callback, CacheService.DataType.CATEGORIES);
    }

    @Override
    public void requestSavedList(int offset, int limit, Callback<List<Listing>> callback) {
        //This functionality is not required, but it can exist
    }

    @Override
    public void checkListingInSavedList(long listingId, Callback<Boolean> callback) {
        //This functionality is not required, but it can exist
    }

    @Override
    public void addListingToSavedList(long listingId) {
        //This functionality is not required, but it can exist
    }

    @Override
    public void removeListingFromSavedList(long listingId) {
        //This functionality is not required, but it can exist
    }

    /**
     * Used to get single object
     *
     * @param call
     * @param callback
     * @param <T>
     * @param dataType used to send information about data to caching service
     */
    private <T extends Parcelable> void enqueueCall(Call<Result<T>> call, final Callback<T> callback, final CacheService.DataType dataType) {
        call.enqueue(new retrofit2.Callback<Result<T>>() {

            @Override
            public void onResponse(Response<Result<T>> response) {
                //Result data always is an array, so we should retrieve first element
                //if it's not present to prevent IndexOutOfBoundsException return null
                Result result = response.body();
                if (result.getCount() > 0) {
                    List<T> res = response.body().getResult();

                    //Returning data immediately to show it to user and after that launching service in foreground to cache it
                    callback.onDataReceived(res.get(0));
                    launchCacheService((List<Parcelable>) res, dataType);
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
     * @param dataType used to send information about data to caching service
     */
    private <T extends Parcelable> void enqueueCallForList(Call<Result<T>> call, final Callback<List<T>> callback, final CacheService.DataType dataType) {
        call.enqueue(new retrofit2.Callback<Result<T>>() {
            @Override
            public void onResponse(Response<Result<T>> response) {
                if (response.body() != null) {
                    List<T> res = response.body().getResult();

                    //Returning data immediately to show it to user and after that launching service in foreground to cache it
                    callback.onDataReceived(res);
                    launchCacheService((List<Parcelable>) res, dataType);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError();
            }
        });
    }

    /**
     * Launching service for caching loaded data to database
     * @param data
     * @param dataType
     */
    private void launchCacheService(List<Parcelable> data, CacheService.DataType dataType) {
                Intent intent = new Intent(EtsyApplication.getContext(), CacheService.class);
                intent.putParcelableArrayListExtra(CacheService.EXTRA_DATA, new ArrayList<>(data));
                intent.putExtra(CacheService.EXTRA_DATA_TYPE, dataType.ordinal());
                EtsyApplication.getContext().startService(intent);
    }

}