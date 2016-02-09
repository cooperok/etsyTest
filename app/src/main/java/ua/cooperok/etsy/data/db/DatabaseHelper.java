package ua.cooperok.etsy.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "etsy";

    // Table Names
    public static final String TABLE_CATEGORIES = "categories";
    public static final String TABLE_LISTINGS = "listings";
    public static final String TABLE_LISTINGS_IMAGES = "listing_images";
    public static final String TABLE_SAVED_LISTINGS = "saved_listings";

    // Common column names
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";

    //Categories table fields
    public static final String KEY_NAME = "name";

    //Listing table fields
    public static final String KEY_CATEGORY_ID = "category_id";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PRICE = "price";
    public static final String KEY_CURRENCY = "currency";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_URL = "url";

    //Listing images categories
    public static final String KEY_LISTING_ID = "listing_id";
    public static final String KEY_FULL_HEIGHT = "full_height";
    public static final String KEY_FULL_WIDTH = "full_width";
    public static final String KEY_URL_75x75 = "url75x75";
    public static final String KEY_URL_170x135 = "url170x135";
    public static final String KEY_URL_570xN = "url570xN";
    public static final String KEY_URL_FULLxFULL = "url_fullxfull";

    /**
     * CREATE TABLE categories(id INTEGER PRIMARY KEY,title TEXT,name TEXT)
     */
    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TITLE + " TEXT,"
            + KEY_NAME + " TEXT)";

    /**
     * CREATE TABLE listings(id INTEGER PRIMARY KEY,category_id INTEGER,title TEXT,description TEXT,price INTEGER,currency TEXT,quantity INTEGER,url TEXT,saved_state INTEGER DEFAULT 0)
     */
    private static final String CREATE_TABLE_LISTINGS = "CREATE TABLE " + TABLE_LISTINGS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_CATEGORY_ID + " INTEGER,"
            + KEY_TITLE + " TEXT," + KEY_DESCRIPTION + " TEXT,"
            + KEY_PRICE + " INTEGER," + KEY_CURRENCY + " TEXT,"
            + KEY_QUANTITY + " INTEGER," + KEY_URL + " TEXT)";

    /**
     * CREATE TABLE listing_images(id INTEGER PRIMARY KEY,listing_id INTEGER,full_height INTEGER,full_width INTEGER,url75x75 TEXT,url170x135 TEXT,url570xN TEXT,url_fullxfull TEXT)
     */
    private static final String CREATE_TABLE_LISTINGS_IMAGES = "CREATE TABLE " + TABLE_LISTINGS_IMAGES + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_LISTING_ID + " INTEGER,"
            + KEY_FULL_HEIGHT + " INTEGER," + KEY_FULL_WIDTH + " INTEGER,"
            + KEY_URL_75x75 + " TEXT," + KEY_URL_170x135 + " TEXT,"
            + KEY_URL_570xN + " TEXT," + KEY_URL_FULLxFULL + " TEXT)";

    /**
     * CREATE TABLE saved_listings(id INTEGER PRIMARY KEY,listing_id INTEGER)
     */
    private static final String CREATE_TABLE_SAVED_LISTINGS = "CREATE TABLE " + TABLE_SAVED_LISTINGS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_LISTING_ID + " INTEGER)";

    /**
     * SELECT * FROM categories
     */
    public static final String SELECT_CATEGORIES = "SELECT * FROM " + TABLE_CATEGORIES;

    /**
     * SELECT * FROM listings WHERE id = ?
     */
    public static final String SELECT_LISTING = "SELECT * FROM " + TABLE_LISTINGS + " WHERE " + KEY_ID + " = ?";

    /**
     * SELECT * FROM listings_images WHERE listing_id = ?
     */
    public static final String SELECT_LISTING_IMAGES = "SELECT * FROM " + TABLE_LISTINGS_IMAGES + " WHERE " + KEY_LISTING_ID + " = ?";

    /**
     * SELECT * FROM listings WHERE category_id = ? AND title LIKE %search% OR description LIKE %search% LIMIT 0, 20
     */
    public static final String SELECT_LISTINGS = "SELECT * FROM " + TABLE_LISTINGS + " WHERE "
            + KEY_CATEGORY_ID + " = ? AND ("
            + KEY_TITLE + " LIKE '%%%1$s%%' OR "
            + KEY_DESCRIPTION + " LIKE '%%%1$s%%') "
            + "LIMIT %2$d, %3$d";

    /**
     * SELECT * FROM saved_listings LIMIT 0, 20
     */
    public static final String SELECT_SAVED_LISTINGS = "SELECT * FROM " + TABLE_SAVED_LISTINGS + " LIMIT %1$d, %2$d";

    /**
     * SELECT * FROM saved_listings WHERE listing_id = ?
     */
    public static final String SELECT_SAVED_LISTING = "SELECT * FROM " + TABLE_SAVED_LISTINGS + " WHERE " + KEY_LISTING_ID + " = ?";

    public static final String SELECT_LISTINGS_BY_IDS = "SELECT * FROM " + TABLE_LISTINGS + " WHERE " + KEY_ID + " IN(%s)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_LISTINGS);
        db.execSQL(CREATE_TABLE_LISTINGS_IMAGES);
        db.execSQL(CREATE_TABLE_SAVED_LISTINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}