package com.taotianhua.covidnews.ui.epidemic.cluster;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.google.android.material.tabs.TabLayout;
import com.taotianhua.covidnews.R;

public class ClusterFragment extends Fragment {

    private ViewPager viewPager;
    private ClusterViewModel mViewModel;
    private ClusterPagerAdapter clusterPagerAdapter;

    public static ClusterFragment newInstance() {
        return new ClusterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cluster_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        clusterPagerAdapter  = new ClusterPagerAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.cluster_pager);
        viewPager.setAdapter(clusterPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.cluster_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ClusterViewModel.class);
        // TODO: Use the ViewModel

    }

}