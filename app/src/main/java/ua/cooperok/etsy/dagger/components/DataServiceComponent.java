package ua.cooperok.etsy.dagger.components;

import javax.inject.Singleton;

import dagger.Component;
import ua.cooperok.etsy.dagger.DataScope;
import ua.cooperok.etsy.dagger.module.DataServiceModule;
import ua.cooperok.etsy.data.DataProvider;
import ua.cooperok.etsy.data.db.DatabaseHelper;

@DataScope
@Component(
        dependencies = NetComponent.class,
        modules = DataServiceModule.class
)
public interface DataServiceComponent {

    DataProvider getDataProvider();

    DatabaseHelper getDatabaseHelper();

}