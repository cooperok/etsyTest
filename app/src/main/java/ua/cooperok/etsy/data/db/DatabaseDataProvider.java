package ua.cooperok.etsy.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    private DatabaseHelper mDatabaseHelper;

    public DatabaseDataProvider(DatabaseHelper helper) {
        mDatabaseHelper = helper;
    }

    @Override
    public void requestListing(long listingId, Callback<Listing> callback) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(DatabaseHelper.SELECT_LISTING, new String[]{String.valueOf(listingId)});
        if (c != null) {
            List<Listing> listings = createListings(c);
            callback.onDataReceived(listings.get(0));
            c.close();
        } else {
            callback.onError();
        }
        db.close();
    }

    @Override
    public void requestListings(Category category, String search, int offset, int limit, Callback<List<Listing>> callback) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        String query = String.format(DatabaseHelper.SELECT_LISTINGS, search, offset, limit);
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(category.getId())});
        if (c != null) {
            callback.onDataReceived(createListings(c));
            c.close();
        } else {
            callback.onError();
        }
        db.close();
    }

    @Override
    public void requestSavedList(int offset, int limit, Callback<List<Listing>> callback) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        String query = String.format(DatabaseHelper.SELECT_SAVED_LISTINGS, offset, limit);
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                List<Integer> ids = new ArrayList<>();
                do {
                    ids.add(c.getInt(c.getColumnIndex(DatabaseHelper.KEY_LISTING_ID)));
                } while (c.moveToNext());

                String idsS = ids.toString();
                Cursor cc = db.rawQuery(String.format(DatabaseHelper.SELECT_LISTINGS_BY_IDS, idsS.substring(1, idsS.length() - 1)), null);
                if (cc != null) {
                    callback.onDataReceived(createListings(cc));
                    cc.close();
                } else {
                    callback.onError();
                }
            }
            c.close();
        } else {
            callback.onError();
        }
        db.close();
    }

    private List<Listing> createListings(Cursor c) {
        List<Listing> listings = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Listing listing = new Listing(
                        c.getLong(c.getColumnIndex(DatabaseHelper.KEY_CATEGORY_ID)),
                        c.getLong(c.getColumnIndex(DatabaseHelper.KEY_ID)),
                        c.getString(c.getColumnIndex(DatabaseHelper.KEY_TITLE)),
                        new BigDecimal(c.getInt(c.getColumnIndex(DatabaseHelper.KEY_PRICE))),
                        c.getString(c.getColumnIndex(DatabaseHelper.KEY_CURRENCY))
                );
                listing.setDescription(c.getString(c.getColumnIndex(DatabaseHelper.KEY_DESCRIPTION)));
                listing.setUrl(c.getString(c.getColumnIndex(DatabaseHelper.KEY_URL)));
                listings.add(listing);
            } while (c.moveToNext());
        }
        return listings;
    }

    @Override
    public void requestListingImages(long listingId, Callback<List<Image>> callback) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(DatabaseHelper.SELECT_LISTING_IMAGES, new String[]{String.valueOf(listingId)});
        if (c != null) {
            List<Image> images = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    Image image = new Image(
                            c.getLong(c.getColumnIndex(DatabaseHelper.KEY_ID)),
                            c.getLong(c.getColumnIndex(DatabaseHelper.KEY_LISTING_ID)),
                            c.getInt(c.getColumnIndex(DatabaseHelper.KEY_FULL_HEIGHT)),
                            c.getInt(c.getColumnIndex(DatabaseHelper.KEY_FULL_WIDTH))
                    );
                    image.setUrl75x75(c.getString(c.getColumnIndex(DatabaseHelper.KEY_URL_75x75)));
                    image.setUrl170x135(c.getString(c.getColumnIndex(DatabaseHelper.KEY_URL_170x135)));
                    image.setUrl570xN(c.getString(c.getColumnIndex(DatabaseHelper.KEY_URL_570xN)));
                    image.setUrlFullxFull(c.getString(c.getColumnIndex(DatabaseHelper.KEY_URL_FULLxFULL)));
                    images.add(image);
                } while (c.moveToNext());
                callback.onDataReceived(images);
            } else {
                callback.onDataReceived(images);
            }
            c.close();
        } else {
            callback.onError();
        }
        db.close();
    }

    @Override
    public void requestListingsImages(List<Listing> listings, Callback<Void> callback) {
        for (final Listing listing : listings) {
            //it's doing synchronous
            requestListingImages(listing.getId(), new Callback<List<Image>>() {
                @Override
                public void onDataReceived(List<Image> data) {
                    listing.addImages(data);
                }

                @Override
                public void onError() {

                }
            });
        }
        callback.onDataReceived(null);
    }

    @Override
    public void requestCategories(Callback<List<Category>> callback) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(DatabaseHelper.SELECT_CATEGORIES, null);
        if (c != null) {
            List<Category> categories = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    categories.add(new Category(
                            c.getLong(c.getColumnIndex(DatabaseHelper.KEY_ID)),
                            c.getString(c.getColumnIndex(DatabaseHelper.KEY_NAME)),
                            c.getString(c.getColumnIndex(DatabaseHelper.KEY_TITLE))
                    ));
                } while (c.moveToNext());
                callback.onDataReceived(categories);
            } else {
                callback.onDataReceived(categories);
            }
            c.close();
        } else {
            callback.onError();
        }
        db.close();
    }

    @Override
    public void checkListingInSavedList(long listingId, Callback<Boolean> callback) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(DatabaseHelper.SELECT_SAVED_LISTING, new String[]{String.valueOf(listingId)});
        if (c != null) {
            callback.onDataReceived(c.moveToFirst());
            c.close();
        } else {
            callback.onError();
        }
        db.close();
    }

    @Override
    public void addListingToSavedList(long listingId) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.KEY_LISTING_ID, listingId);
        db.insertWithOnConflict(DatabaseHelper.TABLE_SAVED_LISTINGS, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    @Override
    public void removeListingFromSavedList(long listingId) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        db.delete(DatabaseHelper.TABLE_SAVED_LISTINGS, DatabaseHelper.KEY_LISTING_ID + "=?", new String[]{String.valueOf(listingId)});
        db.close();
    }

}