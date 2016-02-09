package ua.cooperok.etsy.app;

import android.app.Application;
import android.content.Context;

import ua.cooperok.etsy.dagger.components.AppComponent;
import ua.cooperok.etsy.dagger.components.DaggerAppComponent;
import ua.cooperok.etsy.dagger.components.DaggerDataServiceComponent;
import ua.cooperok.etsy.dagger.components.DaggerNetComponent;
import ua.cooperok.etsy.dagger.components.DataServiceComponent;
import ua.cooperok.etsy.dagger.components.NetComponent;
import ua.cooperok.etsy.dagger.module.AppModule;
import ua.cooperok.etsy.dagger.module.DataServiceModule;
import ua.cooperok.etsy.dagger.module.NetModule;
import ua.cooperok.etsy.log.Logger;

public class EtsyApplication extends Application {

    private AppComponent mAppComponent;

    private DataServiceComponent mDataServiceComponent;

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

        NetComponent netComponent = DaggerNetComponent.builder()
                .appComponent(mAppComponent)
                .netModule(new NetModule(ApiHelper.API_URL))
                .build();

        mDataServiceComponent = DaggerDataServiceComponent.builder()
                .netComponent(netComponent)
                .dataServiceModule(new DataServiceModule())
                .build();
    }

    public AppComponent getComponent() {
        return mAppComponent;
    }

    public DataServiceComponent getDataServiceComponent() {
        return mDataServiceComponent;
    }

    public static EtsyApplication getInstance() {
        return sInstance;
    }

    public static Context getContext() {
        return sInstance.getApplicationContext();
    }

}