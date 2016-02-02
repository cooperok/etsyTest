package ua.cooperok.etsy.view.activity;

import ua.cooperok.etsy.R;
import ua.cooperok.etsy.dagger.components.AppComponent;

public class MainActivity extends BaseActivity {

    @Override
    protected void setUpComponent(AppComponent component) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

}