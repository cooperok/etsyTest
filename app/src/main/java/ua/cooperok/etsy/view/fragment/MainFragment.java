package ua.cooperok.etsy.view.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import javax.inject.Inject;

import butterknife.Bind;
import ua.cooperok.etsy.R;
import ua.cooperok.etsy.dagger.components.DaggerViewComponent;
import ua.cooperok.etsy.dagger.components.DataServiceComponent;
import ua.cooperok.etsy.dagger.components.ViewComponent;
import ua.cooperok.etsy.dagger.module.ViewModule;
import ua.cooperok.etsy.presenter.IBasePresenter;
import ua.cooperok.etsy.presenter.IMainTabsPresenter;
import ua.cooperok.etsy.view.IMainTabsView;
import ua.cooperok.etsy.view.adapter.MainFragmentPagerAdapter;

public class MainFragment extends BaseFragment implements IMainTabsView {

    @Inject
    IMainTabsPresenter mPresenter;

    @Bind(R.id.sliding_tabs)
    TabLayout mTabLayout;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    public static MainFragment getInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setRetainInstance(true);
//    }

    protected void updateView(View view) {
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getChildFragmentManager());
        adapter.addFragment(SearchFragment.getInstance(getString(R.string.search_tab)));
        adapter.addFragment(SavedListingsFragment.getInstance(getString(R.string.saved_list_tab)));

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
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
    protected void setUpComponent(DataServiceComponent component) {
        ViewComponent viewComponent = DaggerViewComponent.builder()
                .dataServiceComponent(component)
                .viewModule(new ViewModule(this))
                .build();

        viewComponent.inject(this);
    }

    @Override
    public void showSearch() {
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void showSavedListings() {
        mViewPager.setCurrentItem(1);
    }
}