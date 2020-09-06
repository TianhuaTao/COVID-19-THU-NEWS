package com.taotianhua.covidnews.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taotianhua.covidnews.model.EventBrief;
import com.taotianhua.covidnews.repository.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * HomeFragment 的 ViewModel
 * 功能为管理分类的增删
 * 总的分类有4个，在 Repository 中定义，但是显示几个由 HomeViewModel 决定
 */
public class HomeViewModel extends ViewModel {

    // TODO: 增加修改分类数目的功能
    // TODO: 修改对应的 Fragment
    public HomeViewModel() {
    }

    public int  getCatalogCount(){
        // TODO: 增加修改分类数目的功能
        return Repository.getInstance().getCatalog().size();
    }

    public String getCatalogName(int position){
        // TODO: 增加修改分类数目的功能
        return  Repository.getInstance().getCatalog().get(position);
    }

}