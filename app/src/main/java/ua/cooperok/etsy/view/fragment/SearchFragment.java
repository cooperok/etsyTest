package ua.cooperok.etsy.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import ua.cooperok.etsy.R;
import ua.cooperok.etsy.dagger.components.DaggerViewComponent;
import ua.cooperok.etsy.dagger.components.DataServiceComponent;
import ua.cooperok.etsy.dagger.components.ViewComponent;
import ua.cooperok.etsy.dagger.module.ViewModule;
import ua.cooperok.etsy.data.model.Category;
import ua.cooperok.etsy.presenter.IListingSearchPresenter;
import ua.cooperok.etsy.view.IListingSearchView;
import ua.cooperok.etsy.view.activity.MainActivity;

public class SearchFragment extends BaseFragment implements IListingSearchView {

    private static final String KEY_CATEGORIES = "categories";

    @Inject
    IListingSearchPresenter mPresenter;

    @Bind(R.id.categories_spinner)
    Spinner mSpinner;

    @Bind(R.id.search_btn)
    Button mSearchButton;

    @Bind(R.id.search_edit_text)
    EditText mEdit;

    private List<Category> mCategories;

    public static SearchFragment getInstance(String title) {
        SearchFragment fragment = new SearchFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected void updateView(View view) {
        mSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mPresenter.onCategorySelected((Category) parent.getAdapter().getItem(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.searchListings(mEdit.getText().toString());
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //If there is no any saved data then loading them
        if (savedInstanceState == null) {
            mPresenter.loadCategories();
        } else {
            //trying to restore data and if there is nothing loading them
            mCategories = savedInstanceState.getParcelableArrayList(KEY_CATEGORIES);
            if (mCategories != null) {
                setCategories(mCategories);
            } else {
                mPresenter.loadCategories();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCategories != null) {
            outState.putParcelableArrayList(KEY_CATEGORIES, (ArrayList<? extends Parcelable>) mCategories);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void setUpComponent(DataServiceComponent component) {
        ViewComponent viewComponent = DaggerViewComponent.builder()
                .dataServiceComponent(component)
                .viewModule(new ViewModule(this))
                .build();

        viewComponent.inject(this);
    }

    @Override
    public void setCategories(List<Category> categories) {
        mCategories = categories;
        if (categories != null) {
            SpinnerAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.categories_spinner_item, categories);
            mSpinner.setAdapter(adapter);
            mSpinner.setSelection(0);
        }
    }

    @Override
    public void onEmptyCategories() {
        Toast.makeText(getContext(), R.string.returned_empty_categories_list, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSearchResultView(String keywords, Category category) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_out_right, R.anim.slide_in_right)
                .replace(MainActivity.CONTAINER_ID, SearchResultsFragment.getInstance(keywords, category))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showPreload() {

    }

    @Override
    public void hidePreload() {

    }

    @Override
    public void onLoadError() {
        Toast.makeText(getContext(), R.string.load_error, Toast.LENGTH_LONG).show();
    }

}