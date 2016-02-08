package ua.cooperok.etsy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Listing image, contains different URL's for different image size
 */
public class Image implements Parcelable {

    @SerializedName("listing_image_id")
    @Expose
    private long mId;

    @SerializedName("listing_id")
    @Expose
    private long mListingId;

    @SerializedName("full_height")
    @Expose
    private int mFullHeight;

    @SerializedName("full_width")
    @Expose
    private int mFullWidth;

    @SerializedName("url_75x75")
    @Expose
    private String mUrl75x75;

    @SerializedName("url_170x135")
    @Expose
    private String mUrl170x135;

    @SerializedName("url_570xN")
    @Expose
    private String mUrl570xN;

    @SerializedName("url_fullxfull")
    @Expose
    private String mUrlFullxFull;

    public Image(Parcel in) {
        mId = in.readLong();
        mListingId = in.readLong();
        mFullHeight = in.readInt();
        mFullWidth = in.readInt();
        mUrl75x75 = in.readString();
        mUrl170x135 = in.readString();
        mUrl570xN = in.readString();
        mUrlFullxFull = in.readString();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeLong(mListingId);
        dest.writeInt(mFullHeight);
        dest.writeInt(mFullWidth);
        dest.writeString(mUrl75x75);
        dest.writeString(mUrl170x135);
        dest.writeString(mUrl570xN);
        dest.writeString(mUrlFullxFull);
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

}