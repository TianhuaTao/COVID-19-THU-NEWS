package com.java.taotianhua.covidnews.ui.epidemic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.java.taotianhua.covidnews.ui.epidemic.graph.GraphViewModel;
import com.java.taotianhua.covidnews.R;

public class EpidemicFragment extends Fragment {
    ViewPager viewPager;
    EpidemicPagerAdapter epidemicPagerAdapter;


    private GraphViewModel epidemicViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        epidemicViewModel = new
                ViewModelProvider(this).get(GraphViewModel.class);


        return inflater.inflate(R.layout.fragment_epidemic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        epidemicPagerAdapter = new EpidemicPagerAdapter(getChildFragmentManager(),epidemicViewModel);
        viewPager = view.findViewById(R.id.epidemic_pager);
        viewPager.setAdapter(epidemicPagerAdapter);

        TabLayout tabLayout =view.findViewById(R.id.epidemic_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}