package com.taotianhua.covidnews.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    HomeViewModel viewModel;
    List<Fragment> tabsFragment;

    public HomePagerAdapter(FragmentManager fm, HomeViewModel viewModel) {
        super(fm);
        this.viewModel = viewModel;
        tabsFragment = new ArrayList<>(Collections.nCopies(this.viewModel.getCatalogCount(), null));
    }

    @NotNull
    @Override
    public Fragment getItem(int i) {
        if (tabsFragment.get(i) == null) {
            HomeTabFragment fragment = new HomeTabFragment();
            Bundle args = new Bundle();
            args.putInt("position", i);
            args.putString("catalog", viewModel.getCatalogName(i));
            fragment.setArguments(args);
            tabsFragment.set(i, fragment);
        }

        return tabsFragment.get(i);
    }

    @Override
    public int getCount() {
        return viewModel.getCatalogCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return viewModel.getCatalogName(position);
    }
}