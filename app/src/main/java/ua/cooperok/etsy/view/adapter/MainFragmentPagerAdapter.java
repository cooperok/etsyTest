package ua.cooperok.etsy.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;

import ua.cooperok.etsy.view.fragment.BaseFragment;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private LinkedList<BaseFragment> mFragments;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new LinkedList<>();
    }

    public void addFragment(BaseFragment fragment) {
        mFragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }
}