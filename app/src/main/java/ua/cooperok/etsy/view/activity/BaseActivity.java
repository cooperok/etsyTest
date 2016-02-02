package ua.cooperok.etsy.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ua.cooperok.etsy.app.EtsyApplication;
import ua.cooperok.etsy.dagger.components.AppComponent;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        setUpComponent(EtsyApplication.getInstance().getComponent());
    }

    abstract protected void setUpComponent(AppComponent component);

    abstract protected int getLayout();

}