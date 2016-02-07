package ua.cooperok.etsy.app;

import android.app.Application;

import ua.cooperok.etsy.dagger.components.AppComponent;
import ua.cooperok.etsy.dagger.components.DaggerAppComponent;
import ua.cooperok.etsy.dagger.module.AppModule;
import ua.cooperok.etsy.log.Logger;

public class EtsyApplication extends Application {

    private AppComponent mAppComponent;

    private static EtsyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        setupGraph();
        Logger.buildLogger(getApplicationContext());
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