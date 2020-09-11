package com.java.taotianhua.covidnews.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.NotNull;

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    HomeViewModel viewModel;


    public HomePagerAdapter(FragmentManager fm, HomeViewModel viewModel) {
        super(fm);
        this.viewModel = viewModel;
    }

    @NotNull
    @Override
    public Fragment getItem(int i) {
        return viewModel.getSelectedFragmentAtIndex(i);
    }

    @Override
    public int getCount() {
        return viewModel.getCatalogCount();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return viewModel.getCatalogName(position);
    }
}