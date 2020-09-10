package com.java.taotianhua.covidnews.ui.epidemic;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.java.taotianhua.covidnews.ui.epidemic.cluster.ClusterFragment;
import com.java.taotianhua.covidnews.ui.epidemic.entity.EntityQueryFragment;
import com.java.taotianhua.covidnews.ui.epidemic.graph.GraphFragment;
import com.java.taotianhua.covidnews.ui.epidemic.graph.GraphViewModel;

import org.jetbrains.annotations.NotNull;

public class EpidemicPagerAdapter extends FragmentStatePagerAdapter {

    static final String[] pageTitles= {"疫情曲线","实体查询","事件聚类"};
    GraphViewModel viewModel;

    EntityQueryFragment entityQueryFragment;
    GraphFragment graphFragment;
    ClusterFragment clusterFragment;

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
        }else if(i == 1){ /* i == 1*/
            /* Entity */
            if (entityQueryFragment == null) {
                EntityQueryFragment fragment = new EntityQueryFragment();
                Bundle args = new Bundle();

                fragment.setArguments(args);
                entityQueryFragment = fragment;
            }
            return entityQueryFragment;

        }else { /* i == 2 */
            /* Cluster */
            if(clusterFragment == null){
                Log.i("MyApp", "Request cluster");
                ClusterFragment fragment = new ClusterFragment();
                Bundle args = new Bundle();
                fragment.setArguments(args);
                clusterFragment = fragment;
            }
            return clusterFragment;
        }


    }
}
