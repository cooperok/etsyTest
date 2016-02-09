package ua.cooperok.etsy.dagger.module;

import dagger.Module;
import dagger.Provides;
import ua.cooperok.etsy.data.DataProvider;
import ua.cooperok.etsy.presenter.IListingDetailPresenter;
import ua.cooperok.etsy.presenter.IListingSearchPresenter;
import ua.cooperok.etsy.presenter.IMainActivityPresenter;
import ua.cooperok.etsy.presenter.IMainTabsPresenter;
import ua.cooperok.etsy.presenter.ISavedListingsPresenter;
import ua.cooperok.etsy.presenter.ISearchResultPresenter;
import ua.cooperok.etsy.presenter.impl.ListDetailPresenter;
import ua.cooperok.etsy.presenter.impl.ListingSearchPresenter;
import ua.cooperok.etsy.presenter.impl.MainActivityPresenter;
import ua.cooperok.etsy.presenter.impl.MainTabsPresenter;
import ua.cooperok.etsy.presenter.impl.SavedListingPresenter;
import ua.cooperok.etsy.presenter.impl.SearchResultPresenter;
import ua.cooperok.etsy.view.IListingDetailView;
import ua.cooperok.etsy.view.IListingSearchView;
import ua.cooperok.etsy.view.IMainActivityView;
import ua.cooperok.etsy.view.IMainTabsView;
import ua.cooperok.etsy.view.ISavedListingsView;
import ua.cooperok.etsy.view.ISearchResultView;

@Module
/**
 * One module for all views
 */
public class ViewModule {

    private IMainActivityView mMainActivityView;

    private IMainTabsView mMainTabsView;

    private IListingSearchView mListingSearchView;

    private ISavedListingsView mSavedListingsView;

    private ISearchResultView mSearchResultView;

    private IListingDetailView mListingDetailView;

    public ViewModule(IMainActivityView view) {
        this.mMainActivityView = view;
    }

    public ViewModule(IListingDetailView mListingDetailView) {
        this.mListingDetailView = mListingDetailView;
    }

    public ViewModule(IListingSearchView mListingSearchView) {
        this.mListingSearchView = mListingSearchView;
    }

    public ViewModule(IMainTabsView mMainTabsView) {
        this.mMainTabsView = mMainTabsView;
    }

    public ViewModule(ISavedListingsView mSavedListingsView) {
        this.mSavedListingsView = mSavedListingsView;
    }

    public ViewModule(ISearchResultView mSearchResultView) {
        this.mSearchResultView = mSearchResultView;
    }

    @Provides
    public IMainActivityView getMainActivityView() {
        return mMainActivityView;
    }

    @Provides
    public IMainTabsView getMainTabsViewView() {
        return mMainTabsView;
    }

    @Provides
    public IListingSearchView getListingSearchView() {
        return mListingSearchView;
    }

    @Provides
    public ISavedListingsView getSavedListingsView() {
        return mSavedListingsView;
    }

    @Provides
    public ISearchResultView getSearchResultView() {
        return mSearchResultView;
    }

    @Provides
    public IListingDetailView getListingDetailView() {
        return mListingDetailView;
    }

    @Provides
    public IMainActivityPresenter getMainActivityPresenter(IMainActivityView view) {
        return new MainActivityPresenter(view);
    }

    @Provides
    IMainTabsPresenter getMainTabsPresenter(IMainTabsView view) {
        return new MainTabsPresenter(view);
    }

    @Provides
    IListingSearchPresenter getListingSearchPresenter(IListingSearchView view, DataProvider dataProvider) {
        return new ListingSearchPresenter(view, dataProvider);
    }

    @Provides
    ISavedListingsPresenter getSavedListingPresenter(ISavedListingsView view, DataProvider dataProvider) {
        return new SavedListingPresenter(view, dataProvider);
    }

    @Provides
    ISearchResultPresenter getSearchResultPresenter(ISearchResultView view, DataProvider dataProvider) {
        return new SearchResultPresenter(view, dataProvider);
    }

    @Provides
    IListingDetailPresenter getListingDetailPresenter(IListingDetailView view, DataProvider dataProvider) {
        return new ListDetailPresenter(view, dataProvider);
    }

}