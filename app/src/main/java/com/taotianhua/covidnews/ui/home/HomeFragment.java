package com.taotianhua.covidnews.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.taotianhua.covidnews.R;


public class HomeFragment extends Fragment {

    ViewPager viewPager;
    HomePagerAdapter homePagerAdapter;

    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager(), homeViewModel);
        viewPager = view.findViewById(R.id.home_pager);
        viewPager.setAdapter(homePagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }


}