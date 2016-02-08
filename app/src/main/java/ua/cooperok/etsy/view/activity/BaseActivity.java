package ua.cooperok.etsy.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import ua.cooperok.etsy.app.EtsyApplication;
import ua.cooperok.etsy.dagger.components.AppComponent;
import ua.cooperok.etsy.dagger.components.DataServiceComponent;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        setUpComponent(EtsyApplication.getInstance().getDataServiceComponent());
        ButterKnife.bind(this);
    }

    abstract protected void setUpComponent(DataServiceComponent component);

    abstract protected int getLayout();

}