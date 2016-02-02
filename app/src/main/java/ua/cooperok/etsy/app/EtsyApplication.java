package ua.cooperok.etsy.app;

import android.app.Application;

import ua.cooperok.etsy.dagger.components.AppComponent;
import ua.cooperok.etsy.dagger.components.DaggerAppComponent;
import ua.cooperok.etsy.dagger.module.AppModule;

public class EtsyApplication extends Application {

    private AppComponent mAppComponent;

    private static EtsyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        setupGraph();
    }

    private void setupGraph() {
        AppModule appModule = new AppModule(this);

        mAppComponent = DaggerAppComponent.builder()
                .appModule(appModule)
                .build();
    }

    public AppComponent getComponent() {
        return mAppComponent;
    }

    public static EtsyApplication getInstance() {
        return sInstance;
    }

}