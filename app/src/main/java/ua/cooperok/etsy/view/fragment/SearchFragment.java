package ua.cooperok.etsy.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import ua.cooperok.etsy.R;
import ua.cooperok.etsy.dagger.components.AppComponent;
import ua.cooperok.etsy.dagger.components.DaggerViewComponent;
import ua.cooperok.etsy.dagger.components.ViewComponent;
import ua.cooperok.etsy.dagger.module.ViewModule;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.presenter.IBasePresenter;
import ua.cooperok.etsy.presenter.IListingSearchPresenter;
import ua.cooperok.etsy.view.IListingSearchView;

public class SearchFragment extends BaseFragment implements IListingSearchView {

    @Inject
    IListingSearchPresenter mPresenter;

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void setUpComponent(AppComponent component) {
        ViewComponent viewComponent = DaggerViewComponent.builder()
                .appComponent(component)
                .viewModule(new ViewModule(this)).build();

        viewComponent.inject(this);
    }

    @Override
    public void setCategories(List<Category> categories) {

    }

    @Override
    public void showSearchResultView(List<Listing> listings) {

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
}