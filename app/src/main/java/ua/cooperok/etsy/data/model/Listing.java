package ua.cooperok.etsy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Товар
 */
public class Listing implements Parcelable {

    @SerializedName("listing_id")
    @Expose
    private long mId;

    @SerializedName("category_id")
    @Expose
    private long mCategoryId;

    @SerializedName("title")
    @Expose
    private String mTitle;

    @SerializedName("description")
    @Expose
    private String mDescription;

    @SerializedName("price")
    @Expose
    private BigDecimal mPrice;

    @SerializedName("currency_code")
    @Expose
    private String mCurrency;

    @SerializedName("quantity")
    @Expose
    private int mQuantity;

    @SerializedName("url")
    @Expose
    private String mUrl;

    @SerializedName("creation_tsz")
    @Expose
    private long mCreationTsz;

    @SerializedName("ending_tsz")
    @Expose
    private long mEndingTsz;

    private List<Image> mImages;

    public Listing(Parcel in) {
        mId = in.readLong();
        mCategoryId = in.readLong();
        mTitle = in.readString();
        mDescription = in.readString();
        mPrice = new BigDecimal(in.readString());
        mCurrency = in.readString();
        mQuantity = in.readInt();
        mUrl = in.readString();
        mCreationTsz = in.readLong();
        mEndingTsz = in.readLong();
        mImages = new ArrayList<>();
        in.readTypedList(mImages, Image.CREATOR);
    }

    public Listing(long categoryId, long id, String title, BigDecimal price, String currency) {
        mCategoryId = categoryId;
        mId = id;
        mTitle = title;
        mPrice = price;
        mCurrency = currency;
    }

    public long getCategoryId() {
        return mCategoryId;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public long getId() {
        return mId;
    }

    public BigDecimal getPrice() {
        return mPrice;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public long getEndingTsz() {
        return mEndingTsz;
    }

    public void setEndingTsz(long endingTsz) {
        mEndingTsz = endingTsz;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getCreationTsz() {
        return mCreationTsz;
    }

    public void setCreationTsz(long creationTsz) {
        mCreationTsz = creationTsz;
    }

    public List<Image> getImages() {
        return mImages;
    }

    public void addImage(Image image) {
        mImages.add(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeLong(mCategoryId);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mPrice.toString());
        dest.writeString(mCurrency);
        dest.writeInt(mQuantity);
        dest.writeString(mUrl);
        dest.writeLong(mCreationTsz);
        dest.writeLong(mEndingTsz);
        dest.writeTypedList(mImages);
    }

    public static final Creator<Listing> CREATOR = new Creator<Listing>() {
        @Override
        public Listing createFromParcel(Parcel in) {
            return new Listing(in);
        }

        @Override
        public Listing[] newArray(int size) {
            return new Listing[size];
        }
    };

}