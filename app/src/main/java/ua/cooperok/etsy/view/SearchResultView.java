package ua.cooperok.etsy.view;

import java.util.List;

import ua.cooperok.etsy.data.model.Listing;

public interface SearchResultView extends DataLoadingView {

    void showListingDetailView(Listing listing);

    void showSearchResult(List<Listing> listings);

    void onEmptySearch();

    void onMoreListingsLoaded(List<Listing> listings);

}