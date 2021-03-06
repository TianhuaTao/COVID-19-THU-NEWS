package com.java.taotianhua.covidnews.ui.epidemic.cluster;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.java.taotianhua.covidnews.ui.epidemic.cluster.image.ImageFragment;
import com.java.taotianhua.covidnews.ui.epidemic.cluster.type.TypeFragment;

public class ClusterPagerAdapter extends FragmentStatePagerAdapter {
    static final String[] pageTitles= {"科研进展","药物研究","免疫相关","新冠宿主","学院科研","各界讨论","病理研究","检疫新闻","聚类图示",};

    TypeFragment []typeFragment = {null, null, null, null, null, null, null, null};
    ImageFragment imageFragment;


    public ClusterPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount(){
        return pageTitles.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 8){
            if(imageFragment == null) imageFragment = new ImageFragment();
            return imageFragment;
        }
        if(typeFragment[position] == null) {
            TypeFragment fragment = new TypeFragment();
            Bundle args = new Bundle();
            args.putInt("type", position);
            fragment.setArguments(args);
            typeFragment[position] = fragment;
        }
        return typeFragment[position];
    }
}
