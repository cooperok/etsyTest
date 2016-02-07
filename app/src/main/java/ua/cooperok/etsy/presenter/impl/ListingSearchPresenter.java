package ua.cooperok.etsy.presenter.impl;

import java.util.List;

import javax.inject.Inject;

import ua.cooperok.etsy.app.ApiHelper;
import ua.cooperok.etsy.app.EtsyApplication;
import ua.cooperok.etsy.dagger.components.DaggerDataServiceComponent;
import ua.cooperok.etsy.dagger.components.DaggerNetComponent;
import ua.cooperok.etsy.dagger.components.DataServiceComponent;
import ua.cooperok.etsy.dagger.components.NetComponent;
import ua.cooperok.etsy.dagger.module.DataServiceModule;
import ua.cooperok.etsy.dagger.module.NetModule;
import ua.cooperok.etsy.data.Callback;
import ua.cooperok.etsy.data.DataProvider;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.presenter.IListingSearchPresenter;
import ua.cooperok.etsy.view.IListingSearchView;

public class ListingSearchPresenter implements IListingSearchPresenter {

    private IListingSearchView mView;

    @Inject
    DataProvider mDataProvider;

    public ListingSearchPresenter(IListingSearchView view) {
        mView = view;

        NetComponent netComponent = DaggerNetComponent.builder()
                .appComponent(EtsyApplication.getInstance().getComponent())
                .netModule(new NetModule(ApiHelper.API_URL))
                .build();

        DataServiceComponent dataServiceComponent = DaggerDataServiceComponent.builder()
                .dataServiceModule(new DataServiceModule())
                .netComponent(netComponent)
                .build();

        dataServiceComponent.inject(this);
    }

    @Override
    public void loadCategories() {
        mDataProvider.requestCategories(new Callback<List<Category>>() {
            @Override
            public void onDataReceived(List<Category> data) {
                mView.setCategories(data);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void searchListings(String searchText) {

    }

    @Override
    public void onCategorySelected(Category category) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

}