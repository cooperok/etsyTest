package ua.cooperok.etsy.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ua.cooperok.etsy.presenter.impl.MainActivityPresenter;
import ua.cooperok.etsy.view.activity.MainActivity;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    @Mock
    MainActivity mMainActivityView;

    IMainActivityPresenter mMainActivityPresenter;

    @Before
    public void setUp() {
        mMainActivityPresenter = new MainActivityPresenter(mMainActivityView);
    }

    @Test
    public void testShowMainTabsOnCreate() {
        verify(mMainActivityView, never()).showMainTabsView();
        mMainActivityPresenter.onCreate();
        verify(mMainActivityView).showMainTabsView();
    }

}