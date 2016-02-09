package ua.cooperok.etsy.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import ua.cooperok.etsy.presenter.IBasePresenter;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.listing_detail_toolbar));

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.listing_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void updateView(View view) {
        mTitle.setText(Html.fromHtml(mListing.getTitle()));
        mDescription.setText(Html.fromHtml(mListing.getDescription()));
        String price = NumberFormat.getInstance().format(mListing.getPrice()) + " " + mListing.getCurrency();
        mPrice.setText(price);
        mQuantity.setText(String.format(getString(R.string.listing_quantity_string), mListing.getQuantity()));
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
    protected IBasePresenter getPresenter() {
        return mPresenter;
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
        if (saved) {

        } else {

        }
    }

}