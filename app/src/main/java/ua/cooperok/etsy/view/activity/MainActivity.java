package ua.cooperok.etsy.view.activity;

import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import ua.cooperok.etsy.R;
import ua.cooperok.etsy.dagger.components.AppComponent;
import ua.cooperok.etsy.dagger.components.DaggerViewComponent;
import ua.cooperok.etsy.dagger.components.ViewComponent;
import ua.cooperok.etsy.dagger.module.ViewModule;
import ua.cooperok.etsy.presenter.IMainActivityPresenter;
import ua.cooperok.etsy.view.IMainActivityView;
import ua.cooperok.etsy.view.fragment.MainFragment;

public class MainActivity extends BaseActivity implements IMainActivityView {

    public static final int CONTAINER_ID = R.id.content_fragment;

    @Inject
    IMainActivityPresenter mPresenter;

    @Override
    protected void onResume() {
        mPresenter.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mPresenter.onPause();
        super.onPause();
    }

    @Override
    protected void setUpComponent(AppComponent component) {
        ViewComponent viewComponent = DaggerViewComponent.builder()
                .appComponent(component)
                .viewModule(new ViewModule(this))
                .build();

        viewComponent.inject(this);
    }

    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void popFragmentFromStack() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
        if (fm.getBackStackEntryCount() == 1) {
            super.onBackPressed();
        }
    }

    @Override
    public void showMainTabsView() {
        getSupportFragmentManager().beginTransaction()
                .replace(CONTAINER_ID, MainFragment.getInstance())
                .commit();
    }
}