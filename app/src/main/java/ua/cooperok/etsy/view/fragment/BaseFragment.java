package ua.cooperok.etsy.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ua.cooperok.etsy.app.EtsyApplication;
import ua.cooperok.etsy.dagger.components.AppComponent;
import ua.cooperok.etsy.dagger.components.DataServiceComponent;
import ua.cooperok.etsy.presenter.IBasePresenter;

public abstract class BaseFragment extends Fragment {

    private View mRootView;

    private String mTitle;

    @Override
    public void onResume() {
        super.onResume();
        IBasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        IBasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mRootView);
            setUpComponent(EtsyApplication.getInstance().getDataServiceComponent());
            updateView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        if (mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        super.onDestroyView();
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    protected abstract void updateView(View view);

    protected abstract int getLayoutId();

    protected abstract IBasePresenter getPresenter();

    protected abstract void setUpComponent(DataServiceComponent component);

}