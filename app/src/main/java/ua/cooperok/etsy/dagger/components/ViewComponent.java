package ua.cooperok.etsy.dagger.components;

import dagger.Component;
import ua.cooperok.etsy.dagger.ActivityScope;
import ua.cooperok.etsy.dagger.module.ViewModule;
import ua.cooperok.etsy.view.IListingDetailView;
import ua.cooperok.etsy.view.ISearchResultView;
import ua.cooperok.etsy.view.activity.MainActivity;
import ua.cooperok.etsy.view.fragment.MainFragment;
import ua.cooperok.etsy.view.fragment.SavedListingsFragment;
import ua.cooperok.etsy.view.fragment.SearchFragment;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = ViewModule.class
)
public interface ViewComponent {
    void inject(MainActivity view);
    void inject(MainFragment view);
    void inject(SearchFragment view);
    void inject(SavedListingsFragment view);
    void inject(ISearchResultView view);
    void inject(IListingDetailView view);
}