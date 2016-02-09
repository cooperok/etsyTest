package ua.cooperok.etsy.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import javax.inject.Inject;

import butterknife.Bind;
import ua.cooperok.etsy.R;
import ua.cooperok.etsy.dagger.components.DaggerViewComponent;
import ua.cooperok.etsy.dagger.components.DataServiceComponent;
import ua.cooperok.etsy.dagger.components.ViewComponent;
import ua.cooperok.etsy.dagger.module.ViewModule;
import ua.cooperok.etsy.data.model.Image;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.presenter.IListingDetailPresenter;
import ua.cooperok.etsy.view.IListingDetailView;

public class ListingDetailFragment extends BaseFragment implements IListingDetailView {

    private static final String LISTING_KEY = "listing";

    @Inject
    IListingDetailPresenter mPresenter;

    private Listing mListing;

    @Bind(R.id.listing_detail_description)
    TextView mDescription;

    @Bind(R.id.listing_detail_title)
    TextView mTitle;

    @Bind(R.id.listing_detail_price)
    TextView mPrice;

    @Bind(R.id.listing_detail_quantity)
    TextView mQuantity;

    @Bind(R.id.listing_detail_image)
    ImageView mImage;

    @Bind(R.id.listing_show_in_browser)
    TextView mShowInBrowser;

    @Bind(R.id.listing_detail_save)
    ImageView mSave;

    private boolean mListingState;

    public static ListingDetailFragment getInstance(Listing listing) {
        ListingDetailFragment fragment = new ListingDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(LISTING_KEY, listing);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(LISTING_KEY)) {
            mListing = getArguments().getParcelable(LISTING_KEY);
        } else {
            throw new IllegalArgumentException("Must be created through newInstance");
        }
    }

    @Override
    protected void updateView(View view) {
        mPresenter.checkListingState(mListing);
        mTitle.setText(Html.fromHtml(mListing.getTitle()));
        mDescription.setText(Html.fromHtml(mListing.getDescription()));
        String price = NumberFormat.getInstance().format(mListing.getPrice()) + " " + mListing.getCurrency();
        mPrice.setText(price);
        mQuantity.setText(String.format(getString(R.string.listing_quantity_string), mListing.getQuantity()));
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListingState) {
                    mPresenter.onRemoveFromSavedClick(mListing);
                } else {
                    mPresenter.onSaveListingClick(mListing);
                }
            }
        });
        mShowInBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mListing.getUrl()));
                startActivity(i);
            }
        });
        if (mListing.getMainImage() != null) {
            loadImage();
        } else {
            mListing.setOnImagesInfoLoadListener(new Listing.OnImagesInfoLoadedListener() {
                @Override
                public void onImagesInfoLoaded() {
                    loadImage();
                }
            });
            mPresenter.loadListingInfo(mListing);
        }
    }

    private void loadImage() {
        Image image = mListing.getMainImage();
        if (image != null) {
            Picasso.with(mImage.getContext()).load(image.getUrl570xN()).into(mImage);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.listing_detail_view;
    }

    @Override
    protected void setUpComponent(DataServiceComponent component) {
        ViewComponent viewComponent = DaggerViewComponent.builder()
                .dataServiceComponent(component)
                .viewModule(new ViewModule(this))
                .build();

        viewComponent.inject(this);
    }

    @Override
    public void setListing(Listing listing) {

    }

    @Override
    public void showPreload() {
    }

    @Override
    public void hidePreload() {
    }

    @Override
    public void onLoadError() {

    }

    @Override
    public void onListingSavedStateChanged(boolean saved) {
        mListingState = saved;
        mSave.setVisibility(View.VISIBLE);
        if (saved) {
            mSave.setImageResource(android.R.drawable.star_on);
        } else {
            mSave.setImageResource(android.R.drawable.star_off);
        }
    }

}