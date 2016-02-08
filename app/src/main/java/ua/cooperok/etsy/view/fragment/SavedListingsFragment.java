package ua.cooperok.etsy.view.fragment;

import android.view.View;

import java.util.List;

import javax.inject.Inject;

import ua.cooperok.etsy.R;
import ua.cooperok.etsy.dagger.components.AppComponent;
import ua.cooperok.etsy.dagger.components.DaggerViewComponent;
import ua.cooperok.etsy.dagger.components.DataServiceComponent;
import ua.cooperok.etsy.dagger.components.ViewComponent;
import ua.cooperok.etsy.dagger.module.ViewModule;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.presenter.IBasePresenter;
import ua.cooperok.etsy.presenter.ISavedListingsPresenter;
import ua.cooperok.etsy.view.ISavedListingsView;

public class SavedListingsFragment extends BaseFragment implements ISavedListingsView {

    @Inject
    ISavedListingsPresenter mPresenter;

    public static SavedListingsFragment getInstance(String title) {
        SavedListingsFragment fragment = new SavedListingsFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected void updateView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_saved_listings;
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
    public void setListings(List<Listing> listings) {

    }

    @Override
    public void showListingDetailView(Listing listing) {

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

    }
}