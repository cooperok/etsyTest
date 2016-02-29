package ua.cooperok.etsy.data.net;

import android.content.Intent;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.LinkedList;
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

    /**
     * Queue of calls to load images info
     */
    private LinkedList<Call<Result<Image>>> mImagesCalls;

    /**
     * Queue of listings that are waiting for images info
     */
    private LinkedList<Listing> mListings;

    /**
     * Callback that uses when all images info are loaded for all listings
     */
    private Callback<Void> mImagesCallback;

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
    public void requestListingsImages(List<Listing> listings, Callback<Void> callback) {
        mImagesCallback = callback;
        mImagesCalls = new LinkedList<>();
        mListings = new LinkedList<>();
        for (Listing listing : listings) {
            //fill in queues
            mImagesCalls.add(mService.getListingImages(listing.getId(), ApiHelper.API_KEY));
            mListings.add(listing);
        }
        loadImages();
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
     * Retrieving image call from queue and execute it.
     * Easiest way, loading is going synchronously one by one. It would be better to do it in parallel
     */
    private void loadImages() {
        if (mImagesCalls.size() > 0) {
            Call call = mImagesCalls.poll();
            enqueueImageCall(call, new ImageCallback(call));
        } else {
            mImagesCallback.onDataReceived(null);
        }
    }

    private void enqueueImageCall(Call<Result<Image>> call, retrofit2.Callback callback) {
        call.enqueue(callback);
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
     *
     * @param data
     * @param dataType
     */
    private void launchCacheService(List<Parcelable> data, CacheService.DataType dataType) {
        Intent intent = new Intent(EtsyApplication.getContext(), CacheService.class);
        intent.putParcelableArrayListExtra(CacheService.EXTRA_DATA, new ArrayList<>(data));
        intent.putExtra(CacheService.EXTRA_DATA_TYPE, dataType.ordinal());
        EtsyApplication.getContext().startService(intent);
    }

    /**
     * Retrofit callback for loading images info
     */
    private class ImageCallback implements retrofit2.Callback<Result<Image>> {

        private Call mCall;

        ImageCallback(Call call) {
            mCall = call;
        }

        @Override
        public void onResponse(Response<Result<Image>> response) {
            //Bad request could be when limit of requests per second is reached, so we need to resend this request
            //this is easiest approach, without timeouts and so on
            if (response.code() == 400) {
                enqueueImageCall(mCall, this);
            } else {
                if (response.body() != null) {
                    Listing listing = mListings.poll();
                    listing.addImages(response.body().getResult());
                }
                finish();
            }
        }

        @Override
        public void onFailure(Throwable t) {
            finish();
        }

        private void finish() {
            mCall = null;
            //after load request new call to execute
            loadImages();
        }
    }

}