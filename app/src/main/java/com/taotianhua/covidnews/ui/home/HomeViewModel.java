package com.taotianhua.covidnews.ui.home;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taotianhua.covidnews.model.EventBrief;
import com.taotianhua.covidnews.repository.Repository;
import com.taotianhua.covidnews.ui.home.tabPage.HomeTabFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * HomeFragment 的 ViewModel
 * 功能为管理分类的增删
 * 总的分类有4个，在 Repository 中定义，但是显示几个由 HomeViewModel 决定
 */
public class HomeViewModel extends ViewModel {
    List<Fragment> tabsFragment;
    List<Boolean> selected;

    public void setHomePagerAdapter(HomePagerAdapter homePagerAdapter) {
        this.homePagerAdapter = homePagerAdapter;
    }

    HomePagerAdapter homePagerAdapter;

    public  void setSelected(int position, Boolean isSelected){
        this.selected.set(position, isSelected);

        homePagerAdapter.notifyDataSetChanged();
    }

    public Boolean isSelected(int real_position){
        return selected.get(real_position);
    }


    public HomeViewModel() {

        tabsFragment = new ArrayList<>(
                Collections.nCopies(Repository.getInstance().getCatalog().size(), null));
        selected = new ArrayList<>(
                Collections.nCopies(Repository.getInstance().getCatalog().size(), true));
    }

    public int getCatalogCount() {


        return (int) selected
                .stream()
                .filter(isSelected -> isSelected).count();
    }

    public String getCatalogName(int position) {

        int real_index = 0;
        int count = 0;
        for (int i = 0; i < tabsFragment.size(); i++) {
            if (selected.get(i)) {
                count++;
                if (count == position + 1) {
                    real_index = i;
                    break;
                }
            }
        }
        return Repository.getInstance().getCatalog().get(real_index);
    }

    public Fragment getSelectedFragmentAtIndex(int index) {
        int real_index = 0;
        int count = 0;
        for (int i = 0; i < tabsFragment.size(); i++) {
            if (selected.get(i)) {
                count++;
                if (count == index + 1) {
                    real_index = i;
                    break;
                }
            }
        }
        if (tabsFragment.get(real_index) == null) {
            HomeTabFragment fragment = new HomeTabFragment();
            Bundle args = new Bundle();
            args.putInt("position", real_index);
            args.putString("catalog", Repository.getInstance().getCatalog().get(real_index));
            fragment.setArguments(args);
            tabsFragment.set(real_index, fragment);
        }

        return tabsFragment.get(real_index);
    }

}