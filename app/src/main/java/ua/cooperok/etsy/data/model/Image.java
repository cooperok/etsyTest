package ua.cooperok.etsy.data.model;

/**
 * Изображение товара, содержит в себе отдельные URL картинок, разных размеров
 */
public class Image {

    private long mId;

    private long mListingId;

    private int mFullHeight;

    private int mFullWidth;

    private String mUrl75x75;

    private String mUrl170x135;

    private String mUrl570xN;

    private String mUrlFullxFull;

    public Image(long id, long listingId, int width, int height) {
        mId = id;
        mListingId = listingId;
        mFullWidth = width;
        mFullHeight = height;
    }

    public long getId() {
        return mId;
    }

    public long getListingId() {
        return mListingId;
    }

    public int getFullHeight() {
        return mFullHeight;
    }

    public int getFullWidth() {
        return mFullWidth;
    }

    public String getUrl570xN() {
        return mUrl570xN;
    }

    public void setUrl570xN(String url) {
        mUrl570xN = url;
    }

    public String getUrl75x75() {
        return mUrl75x75;
    }

    public void setUrl75x75(String url) {
        mUrl75x75 = url;
    }

    public String getUrlFullxFull() {
        return mUrlFullxFull;
    }

    public void setUrlFullxFull(String url) {
        mUrlFullxFull = url;
    }

    public String getUrl170x135() {
        return mUrl170x135;
    }

    public void setUrl170x135(String url) {
        mUrl170x135 = url;
    }
}