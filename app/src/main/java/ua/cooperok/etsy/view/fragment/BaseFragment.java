package ua.cooperok.etsy.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ua.cooperok.etsy.app.EtsyApplication;
import ua.cooperok.etsy.dagger.components.AppComponent;
import ua.cooperok.etsy.presenter.IBasePresenter;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

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
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        setUpComponent(EtsyApplication.getInstance().getComponent());
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract IBasePresenter getPresenter();

    protected abstract void setUpComponent(AppComponent component);

}