package com.taotianhua.covidnews.ui.epidemic.cluster;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.taotianhua.covidnews.ui.epidemic.cluster.type.TypeFragment;

public class ClusterPagerAdapter extends FragmentStatePagerAdapter {
    static final String[] pageTitles= {"类型1","类型2","类型3","类型4","类型5","类型6","类型7","类型8",};

    TypeFragment []typeFragment = {null, null, null, null, null, null, null, null};


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
