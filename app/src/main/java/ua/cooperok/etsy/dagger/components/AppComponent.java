package ua.cooperok.etsy.dagger.components;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ua.cooperok.etsy.dagger.module.AppModule;

@Singleton
@Component(
        modules = {
                AppModule.class
        }
)
public interface AppComponent {

    Application getApp();

    Context getContext();

}