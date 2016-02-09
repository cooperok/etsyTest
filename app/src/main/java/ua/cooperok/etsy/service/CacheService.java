package ua.cooperok.etsy.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

import ua.cooperok.etsy.app.EtsyApplication;
import ua.cooperok.etsy.dagger.components.DaggerDataServiceComponent;
import ua.cooperok.etsy.data.db.DatabaseHelper;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Image;
import ua.cooperok.etsy.data.model.Listing;

/**
 * Service for caching data. Used for all types of data, type is cheched by parameter {@link #EXTRA_DATA_TYPE}
 * which should be one of the values from {@link DataType}
 */
public class CacheService extends IntentService {

    public enum DataType {
        LISTINGS, CATEGORIES, IMAGES
    }

    private static final String SERVICE_NAME = "CacheService";

    public static final String EXTRA_DATA = "data";

    public static final String EXTRA_DATA_TYPE = "dataType";

    private DatabaseHelper mDbHelper;

    public CacheService() {
        this(SERVICE_NAME);
        mDbHelper = EtsyApplication.getInstance().getDataServiceComponent().getDatabaseHelper();
    }

    public CacheService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int typeOrdinal = intent.getIntExtra(EXTRA_DATA_TYPE, Integer.MAX_VALUE);
        if (typeOrdinal >= DataType.values().length) {
            return;
        }

        ArrayList<Parcelable> data = intent.getParcelableArrayListExtra(EXTRA_DATA);
        if (data == null) {
            return;
        }

        DataType type = DataType.values()[typeOrdinal];
        switch (type) {
            case LISTINGS:
                cacheListings(intent.<Listing>getParcelableArrayListExtra(EXTRA_DATA));
                break;
            case CATEGORIES:
                cacheCategories(intent.<Category>getParcelableArrayListExtra(EXTRA_DATA));
                break;
            case IMAGES:
                cacheImages(intent.<Image>getParcelableArrayListExtra(EXTRA_DATA));
                break;
        }
    }

    private void cacheListings(ArrayList<Listing> listings) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        for (Listing listing : listings) {
            try {
                ContentValues cv = new ContentValues();
                cv.put(DatabaseHelper.KEY_ID, listing.getId());
                cv.put(DatabaseHelper.KEY_TITLE, listing.getTitle());
                cv.put(DatabaseHelper.KEY_CATEGORY_ID, listing.getCategoryId());
                cv.put(DatabaseHelper.KEY_DESCRIPTION, listing.getDescription());
                cv.put(DatabaseHelper.KEY_PRICE, listing.getPrice().longValue());
                cv.put(DatabaseHelper.KEY_CURRENCY, listing.getCurrency());
                cv.put(DatabaseHelper.KEY_QUANTITY, listing.getQuantity());
                cv.put(DatabaseHelper.KEY_URL, listing.getUrl());
                db.insertWithOnConflict(DatabaseHelper.TABLE_LISTINGS, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            } catch (SQLiteException e) {
                //
            }
        }
        db.close();
    }

    private void cacheCategories(ArrayList<Category> categories) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        for (Category category : categories) {
            try {
                ContentValues cv = new ContentValues();
                cv.put(DatabaseHelper.KEY_ID, category.getId());
                cv.put(DatabaseHelper.KEY_TITLE, category.getTitle());
                cv.put(DatabaseHelper.KEY_NAME, category.getName());
                db.insertWithOnConflict(DatabaseHelper.TABLE_CATEGORIES, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            } catch (SQLiteException e) {
                //
            }
        }
        db.close();
    }

    private void cacheImages(ArrayList<Image> images) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        for (Image image : images) {
            try {
                ContentValues cv = new ContentValues();
                cv.put(DatabaseHelper.KEY_ID, image.getId());
                cv.put(DatabaseHelper.KEY_LISTING_ID, image.getListingId());
                cv.put(DatabaseHelper.KEY_FULL_HEIGHT, image.getFullHeight());
                cv.put(DatabaseHelper.KEY_FULL_WIDTH, image.getFullWidth());
                cv.put(DatabaseHelper.KEY_URL_75x75, image.getUrl75x75());
                cv.put(DatabaseHelper.KEY_URL_170x135, image.getUrl170x135());
                cv.put(DatabaseHelper.KEY_URL_570xN, image.getUrl570xN());
                cv.put(DatabaseHelper.KEY_URL_FULLxFULL, image.getUrlFullxFull());
                db.insertWithOnConflict(DatabaseHelper.TABLE_LISTINGS_IMAGES, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            } catch (SQLiteException e) {
                //
            }
        }
        db.close();
    }

}