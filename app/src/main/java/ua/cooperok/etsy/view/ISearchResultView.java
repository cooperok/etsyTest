package ua.cooperok.etsy.view;

import java.util.List;

import ua.cooperok.etsy.data.model.Listing;

public interface ISearchResultView extends IDataLoadingView {

    void showListingDetailView(Listing listing);

    void showSearchResult(List<Listing> listings);

    void onEmptySearch();

    void onMoreListingsLoaded(List<Listing> listings);

}