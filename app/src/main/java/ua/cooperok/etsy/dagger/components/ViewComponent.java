package ua.cooperok.etsy.dagger.components;

import dagger.Component;
import ua.cooperok.etsy.dagger.ActivityScope;
import ua.cooperok.etsy.dagger.module.ViewModule;
import ua.cooperok.etsy.view.activity.MainActivity;
import ua.cooperok.etsy.view.fragment.ListingDetailFragment;
import ua.cooperok.etsy.view.fragment.MainFragment;
import ua.cooperok.etsy.view.fragment.SavedListingsFragment;
import ua.cooperok.etsy.view.fragment.SearchFragment;
import ua.cooperok.etsy.view.fragment.SearchResultsFragment;

@ActivityScope
@Component(
        dependencies = DataServiceComponent.class,
        modules = ViewModule.class
)
public interface ViewComponent {
    void inject(MainActivity view);

    void inject(MainFragment view);

    void inject(SearchFragment view);

    void inject(SavedListingsFragment view);

    void inject(SearchResultsFragment view);

    void inject(ListingDetailFragment view);
}