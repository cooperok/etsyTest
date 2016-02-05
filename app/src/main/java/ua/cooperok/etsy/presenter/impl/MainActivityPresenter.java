package ua.cooperok.etsy.presenter.impl;

import ua.cooperok.etsy.presenter.IMainActivityPresenter;
import ua.cooperok.etsy.view.IMainActivityView;

public class MainActivityPresenter implements IMainActivityPresenter {

    private IMainActivityView mView;

    public MainActivityPresenter(IMainActivityView view) {
        mView = view;
    }

    @Override
    public void onBackPressed() {
        mView.popFragmentFromStack();
    }

    @Override
    public void onResume() {
        mView.showMainTabsView();
    }

    @Override
    public void onPause() {
    }
}