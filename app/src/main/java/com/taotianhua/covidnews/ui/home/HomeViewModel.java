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

public class HomeViewModel extends ViewModel {

    public HomeViewModel() {

    }

    public int  getCatalogCount(){
        return Repository.getInstance().getCatalog().size();
    }

    public String getCatalogName(int position){
        return  Repository.getInstance().getCatalog().get(position);
    }


}