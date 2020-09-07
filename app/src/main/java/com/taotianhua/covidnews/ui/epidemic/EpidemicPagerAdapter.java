package com.taotianhua.covidnews.ui.epidemic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.taotianhua.covidnews.ui.epidemic.entity.EntityQueryFragment;
import com.taotianhua.covidnews.ui.epidemic.graph.GraphFragment;
import com.taotianhua.covidnews.ui.epidemic.graph.GraphViewModel;

import org.jetbrains.annotations.NotNull;

public class EpidemicPagerAdapter extends FragmentStatePagerAdapter {

    static final String[] pageTitles= {"Graph","Entity"};
    GraphViewModel viewModel;

    EntityQueryFragment entityQueryFragment;
    GraphFragment graphFragment;

    public EpidemicPagerAdapter(FragmentManager fm, GraphViewModel viewModel) {
        super(fm);
        this.viewModel = viewModel;
    }
    @Override
    public int getCount() {
        return pageTitles.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
    @NotNull
    @Override
    public Fragment getItem(int i) {
        if(i==0){
            /* Graph */
            if (graphFragment == null) {
                GraphFragment fragment = new GraphFragment();
                Bundle args = new Bundle();

                fragment.setArguments(args);
                graphFragment = fragment;
            }
            return graphFragment;
        }else { /* i == 1*/
            /* Entity */
            if (entityQueryFragment == null) {
                EntityQueryFragment fragment = new EntityQueryFragment();
                Bundle args = new Bundle();

                fragment.setArguments(args);
                entityQueryFragment = fragment;
            }
            return entityQueryFragment;

        }


    }
}
