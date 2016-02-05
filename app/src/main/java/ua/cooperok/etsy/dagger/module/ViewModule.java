package ua.cooperok.etsy.dagger.module;

import dagger.Module;
import dagger.Provides;
import ua.cooperok.etsy.presenter.IListingDetailPresenter;
import ua.cooperok.etsy.presenter.IListingSearchPresenter;
import ua.cooperok.etsy.presenter.IMainActivityPresenter;
import ua.cooperok.etsy.presenter.IMainTabsPresentrer;
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
 * Один модуль на все view
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
    IMainTabsPresentrer getMainTabsPresenter(IMainTabsView view) {
        return new MainTabsPresenter(view);
    }

    @Provides
    IListingSearchPresenter getListingSearchPresenter(IListingSearchView view) {
        return new ListingSearchPresenter(view);
    }

    @Provides
    ISavedListingsPresenter getSavedListingPresenter(ISavedListingsView view) {
        return new SavedListingPresenter(view);
    }

    @Provides
    ISearchResultPresenter getSearchResultPresenter(ISearchResultView view) {
        return new SearchResultPresenter(view);
    }

    @Provides
    IListingDetailPresenter getListingDetailPresenter(IListingDetailView view) {
        return new ListDetailPresenter(view);
    }

}