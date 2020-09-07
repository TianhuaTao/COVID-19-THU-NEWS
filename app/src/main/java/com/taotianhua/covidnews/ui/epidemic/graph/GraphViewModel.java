package com.taotianhua.covidnews.ui.epidemic.graph;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taotianhua.covidnews.model.EpidemicData;
import com.taotianhua.covidnews.model.EventBrief;
import com.taotianhua.covidnews.network.Api;
import com.taotianhua.covidnews.repository.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GraphViewModel extends ViewModel {

    /**
     * return a certain region's data
     */

    private MutableLiveData<EpidemicData> graphData;

    public GraphViewModel() {
    }

    public LiveData<EpidemicData> getEpidemicData(String country, String province, String county){
        if(graphData == null){
            graphData = new MutableLiveData<>();
        }
        String region = country+"|"+province+"|"+county;
        loadRegionDataAsync(region);
        return graphData;
    }

    public LiveData<EpidemicData> getEpidemicData(String country, String province){
        if(graphData == null){
            graphData = new MutableLiveData<>();
        }
        String region = country+"|"+province;
        loadRegionDataAsync(region);
        return graphData;
    }

    public LiveData<EpidemicData> getEpidemicData(String country){
        if(graphData == null){
            graphData = new MutableLiveData<>();
        }
        String region = country;
        loadRegionDataAsync(region);
        return graphData;
    }

    private void loadRegionDataAsync(String region){
        //TODO: return a EpidemicData be region key.
        new Thread(()->{
            EpidemicData epidemicData = Repository.getInstance().getEpidemicData(region);
            graphData.postValue(epidemicData);
        }).start();
    }
}