package ua.cooperok.etsy.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.Bind;
import ua.cooperok.etsy.R;
import ua.cooperok.etsy.dagger.components.AppComponent;
import ua.cooperok.etsy.dagger.components.DaggerViewComponent;
import ua.cooperok.etsy.dagger.components.ViewComponent;
import ua.cooperok.etsy.dagger.module.ViewModule;
import ua.cooperok.etsy.presenter.IBasePresenter;
import ua.cooperok.etsy.presenter.IMainTabsPresentrer;
import ua.cooperok.etsy.view.IMainTabsView;
import ua.cooperok.etsy.view.adapter.MainFragmentPagerAdapter;

public class MainFragment extends BaseFragment implements IMainTabsView {

    @Inject
    IMainTabsPresentrer mPresenter;

    @Bind(R.id.sliding_tabs)
    TabLayout mTabLayout;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(SearchFragment.getInstance());
        adapter.addFragment(SavedListingsFragment.getInstance());

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void setUpComponent(AppComponent component) {
        ViewComponent viewComponent = DaggerViewComponent.builder()
                .viewModule(new ViewModule(this))
                .appComponent(component)
                .build();
        viewComponent.inject(this);
    }

    @Override
    public void showSearch() {

    }

    @Override
    public void showSavedListings() {

    }
}