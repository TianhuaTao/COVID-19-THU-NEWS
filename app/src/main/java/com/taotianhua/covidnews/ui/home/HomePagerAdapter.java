package com.taotianhua.covidnews.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.taotianhua.covidnews.ui.home.tabPage.HomeTabFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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