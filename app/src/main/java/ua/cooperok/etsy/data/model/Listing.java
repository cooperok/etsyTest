package ua.cooperok.etsy.data.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Товар
 */
public class Listing {

    private long mId;

    private long mCategoryId;

    private String mTitle;

    private String mDescription;

    private BigDecimal mPrice;

    private String mCurrency;

    private int mQuantity;

    private String mUrl;

    private long mCreationTsz;

    private long mEndingTsz;

    private List<Image> mImages;

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
}