package ua.cooperok.etsy.view.fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import ua.cooperok.etsy.R;
import ua.cooperok.etsy.dagger.components.DaggerViewComponent;
import ua.cooperok.etsy.dagger.components.DataServiceComponent;
import ua.cooperok.etsy.dagger.components.ViewComponent;
import ua.cooperok.etsy.dagger.module.ViewModule;
import ua.cooperok.etsy.data.model.Listing;
import ua.cooperok.etsy.presenter.IBasePresenter;
import ua.cooperok.etsy.presenter.ISavedListingsPresenter;
import ua.cooperok.etsy.view.ISavedListingsView;
import ua.cooperok.etsy.view.MarginDecoration;
import ua.cooperok.etsy.view.activity.MainActivity;
import ua.cooperok.etsy.view.adapter.BaseRecyclerAdapter;
import ua.cooperok.etsy.view.adapter.ListingsAdapter;

public class SavedListingsFragment extends ListingsLoadingFragment implements ISavedListingsView, ListingsAdapter.OnListingClickListener {

    @Inject
    ISavedListingsPresenter mPresenter;

    private ListingsAdapter mAdapter;

    public static SavedListingsFragment getInstance(String title) {
        SavedListingsFragment fragment = new SavedListingsFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected void updateView(View view) {
        mAdapter = new ListingsAdapter();
        mAdapter.setOnListingClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new MarginDecoration(getResources().getDimensionPixelOffset(R.dimen.listings_recycler_items_margin)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
    protected BaseRecyclerAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setListings(List<Listing> listings) {
        mListings = listings;
        mAdapter.clear();
        mAdapter.addAll(listings);
    }

    @Override
    protected void requestListings() {
        mPresenter.loadSavedListings();
    }

    @Override
    public void showListingDetailView(Listing listing) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_out_right, R.anim.slide_in_right)
                .replace(MainActivity.CONTAINER_ID, ListingDetailFragment.getInstance(listing))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEmptyResult() {
        onEmpty();
    }

    @Override
    public void onMoreListingsLoaded(List<Listing> listings) {
        mListings.addAll(listings);
        mAdapter.addAll(listings);
    }

    @Override
    public void showPreload() {
        showProgress();
    }

    @Override
    public void hidePreload() {
        hideProgress();
    }

    @Override
    public void onLoadError() {
        onError();
    }

    @Override
    public void onListingClicked(Listing listing) {
        mPresenter.onListingClick(listing);
    }
}