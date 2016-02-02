package ua.cooperok.etsy.dagger.components;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import dagger.Component;
import retrofit.Retrofit;
import ua.cooperok.etsy.dagger.ActivityScope;
import ua.cooperok.etsy.dagger.module.NetModule;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = NetModule.class
)
public interface NetComponent extends AppComponent {

    Retrofit getRetrofit();

    OkHttpClient getOkHttpClient();

    SharedPreferences getSharedPreferences();

    Cache getCache();

    Gson getGson();

}