package ua.cooperok.etsy.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ua.cooperok.etsy.data.Callback;
import ua.cooperok.etsy.data.DataProvider;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.presenter.impl.SearchResultPresenter;
import ua.cooperok.etsy.view.ISearchResultView;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SearchResultsPresenterTest {

    @Mock
    DataProvider mDataProvider;

    @Mock
    ISearchResultView mView;

    @Captor
    ArgumentCaptor<Callback<List<Listing>>> mCallbackCaptor;

    private SearchResultPresenter mPresenter;

    private Category mCategory;

    @Before
    public void setUp() {
        mPresenter = new SearchResultPresenter(mView, mDataProvider);
        mCategory = new Category(1L, "name", "title");
    }

    @Test
    public void testListingsLoad() {
        mPresenter.setLimit(20);
        mPresenter.searchListings(mCategory, "search");
        verify(mDataProvider).requestListings(eq(mCategory), eq("search"), eq(0), eq(20), mCallbackCaptor.capture());
        List<Listing> listings = new ArrayList<>();
        listings.add(new Listing(1L, 2L, "title", BigDecimal.ONE, "USD"));
        mCallbackCaptor.getValue().onDataReceived(listings);
        verify(mView).showSearchResult(listings);
        verify(mView, never()).onEmptySearch();
        verify(mView, never()).onMoreListingsLoaded(listings);
    }

    @Test
    public void testListingsLoadEmpty() {
        mPresenter.setLimit(10);
        mPresenter.searchListings(mCategory, "search");
        verify(mDataProvider).requestListings(eq(mCategory), eq("search"), eq(0), eq(10), mCallbackCaptor.capture());
        List<Listing> listings = new ArrayList<>();
        mCallbackCaptor.getValue().onDataReceived(listings);
        verify(mView, never()).showSearchResult(listings);
        verify(mView).onEmptySearch();
    }

    @Test
    public void testListingsLoadError() {
        mPresenter.setLimit(10);
        mPresenter.searchListings(mCategory, "search");
        verify(mDataProvider).requestListings(eq(mCategory), eq("search"), eq(0), eq(10), mCallbackCaptor.capture());
        mCallbackCaptor.getValue().onError();
        verify(mView, never()).showSearchResult(null);
        verify(mView, never()).onEmptySearch();
        verify(mView).onLoadError();
    }

    @Test
    public void testLoadMoreListings() {
        List<Listing> listings1 = new ArrayList<>();
        listings1.add(new Listing(1L, 2L, "title", BigDecimal.ONE, "USD"));

        List<Listing> listings2 = new ArrayList<>();
        listings2.add(new Listing(4L, 3L, "title", BigDecimal.TEN, "USD"));

        mPresenter.setLimit(10);
        mPresenter.searchListings(mCategory, "search");
        verify(mDataProvider).requestListings(eq(mCategory), eq("search"), eq(0), eq(10), mCallbackCaptor.capture());
        mCallbackCaptor.getValue().onDataReceived(listings1);

        mPresenter.loadMoreListings();
        verify(mDataProvider).requestListings(eq(mCategory), eq("search"), eq(10), eq(10), mCallbackCaptor.capture());
        mCallbackCaptor.getValue().onDataReceived(listings2);
        verify(mView, never()).showSearchResult(listings2);
        verify(mView).onMoreListingsLoaded(listings2);
    }

    @Test
    public void testOnListingClick() {
        Listing listing = new Listing(1L, 2L, "title", BigDecimal.ONE, "USD");
        mPresenter.onListingClick(listing);
        verify(mView).showListingDetailView(listing);
    }

}