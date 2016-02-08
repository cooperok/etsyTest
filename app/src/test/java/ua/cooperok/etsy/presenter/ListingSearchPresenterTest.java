package ua.cooperok.etsy.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import ua.cooperok.etsy.data.Callback;
import ua.cooperok.etsy.data.DataProvider;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.presenter.impl.ListingSearchPresenter;
import ua.cooperok.etsy.view.IListingSearchView;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ListingSearchPresenterTest {

    @Mock
    DataProvider mDataProvider;

    @Mock
    IListingSearchView mView;

    @Captor
    ArgumentCaptor<Callback<List<Category>>> mCallbackCaptor;

    private ListingSearchPresenter mPresenter;

    @Before
    public void setUp() {
        mPresenter = new ListingSearchPresenter(mView, mDataProvider);
    }

    @Test
    public void testCategoriesLoad() {
        mPresenter.loadCategories();
        verify(mDataProvider).requestCategories(mCallbackCaptor.capture());
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "name", "title"));
        mCallbackCaptor.getValue().onDataReceived(categories);
        verify(mView).setCategories(categories);
    }

    @Test
    public void testCategoriesLoadEmpty() {
        mPresenter.loadCategories();
        verify(mDataProvider).requestCategories(mCallbackCaptor.capture());
        List<Category> categories = new ArrayList<>();
        mCallbackCaptor.getValue().onDataReceived(categories);
        verify(mView, never()).setCategories(categories);
        verify(mView).onEmptyCategories();
    }

    @Test
    public void testCategoriesLoadError() {
        mPresenter.loadCategories();
        verify(mDataProvider).requestCategories(mCallbackCaptor.capture());
        mCallbackCaptor.getValue().onError();
        verify(mView, never()).setCategories(null);
        verify(mView, never()).onEmptyCategories();
        verify(mView).onLoadError();
    }

    @Test
    public void testSearch() {
        Category category = new Category(1L, "name", "title");
        mPresenter.onCategorySelected(category);
        mPresenter.searchListings("search");
        verify(mView).showSearchResultView("search", category);
    }

}