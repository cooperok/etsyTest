package ua.cooperok.etsy.view.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.cooperok.etsy.R;
import ua.cooperok.etsy.data.model.Listing;

public class ListingsAdapter extends BaseRecyclerAdapter<Listing, ListingsAdapter.ListingViewHolder> {

    private String mQuantityString;

    private String mPriceString;

    @Override
    public ListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Getting all resources on first call
        if (mQuantityString == null) {
            mQuantityString = parent.getContext().getString(R.string.listing_quantity_string);
            mPriceString = parent.getContext().getString(R.string.listing_price_string);
        }
        return new ListingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_listing_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final ListingViewHolder holder, int position) {
        final Listing listing = getItem(position);

        holder.title.setText(Html.fromHtml(listing.getTitle()));
        holder.description.setText(Html.fromHtml(listing.getDescription()));
        holder.price.setText(String.format(mPriceString, NumberFormat.getInstance(new Locale("en-US", "US")).format(listing.getPrice()), listing.getCurrency()));
        holder.quantity.setText(String.format(mQuantityString, listing.getQuantity()));
    }

    static class ListingViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.listing_image)
        ImageView image;

        @Bind(R.id.listing_title)
        TextView title;

        @Bind(R.id.listing_description)
        TextView description;

        @Bind(R.id.listing_price)
        TextView price;

        @Bind(R.id.listing_quantity)
        TextView quantity;

        public ListingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}