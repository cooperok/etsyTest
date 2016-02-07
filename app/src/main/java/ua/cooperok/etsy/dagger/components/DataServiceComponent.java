package ua.cooperok.etsy.dagger.components;

import dagger.Component;
import ua.cooperok.etsy.dagger.DataScope;
import ua.cooperok.etsy.dagger.module.DataServiceModule;
import ua.cooperok.etsy.presenter.impl.ListingSearchPresenter;

@DataScope
@Component(
        dependencies = NetComponent.class,
        modules = DataServiceModule.class
)
public interface DataServiceComponent {

    void inject(ListingSearchPresenter presenter);

}