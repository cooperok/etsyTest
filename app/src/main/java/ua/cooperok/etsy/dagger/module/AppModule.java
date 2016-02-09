package ua.cooperok.etsy.dagger.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application mApp;

    public AppModule(Application app) {
        mApp = app;
    }

    @Singleton
    @Provides
    Application getApplication() {
        return mApp;
    }

    @Singleton
    @Provides
    Context getContext() {
        return mApp.getApplicationContext();
    }

}