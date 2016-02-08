package ua.cooperok.etsy.dagger.components;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import dagger.Component;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import ua.cooperok.etsy.dagger.NetScope;
import ua.cooperok.etsy.dagger.module.NetModule;

@NetScope
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