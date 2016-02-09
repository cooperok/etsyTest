package ua.cooperok.etsy.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import ua.cooperok.etsy.data.DataProvider;
import ua.cooperok.etsy.data.db.DatabaseDataProvider;
import ua.cooperok.etsy.data.db.DatabaseHelper;
import ua.cooperok.etsy.data.net.NetDataProvider;

@Module()
public class DataServiceModule {

    @Provides
    DataProvider getDataService(NetDataProvider netDataProvider, DatabaseDataProvider databaseDataProvider) {
        return new DataProvider(netDataProvider, databaseDataProvider);
    }

    @Provides
    NetDataProvider getNetDataProvider(Retrofit retrofit) {
        return new NetDataProvider(retrofit);
    }

    @Provides
    DatabaseHelper getDatabaseHelper(Context context) {
        return new DatabaseHelper(context);
    }

    @Provides
    DatabaseDataProvider getDatabaseDataProvider(DatabaseHelper databaseHelper) {
        return new DatabaseDataProvider(databaseHelper);
    }

}