package com.taotianhua.covidnews.ui.epidemic.cluster.type;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taotianhua.covidnews.model.EventBrief;
import com.taotianhua.covidnews.repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TypeViewModel extends ViewModel {
    private MutableLiveData<List<EventBrief>> events;
    List<EventBrief> mList = new ArrayList<>();
    public LiveData<List<EventBrief>> getEvents(Context context) {
        if(events ==null){
            events = new MutableLiveData<>();
            loadEventsAsync(context);
        }
        return events;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    private void loadEventsAsync(Context context){
        // Do an asynchronous operation to fetch events for this catalog
        new Thread(()->{
            Log.i("TypeViewModel", "loadEventsAsync");


            //TODO:改为通过type读取新闻，首先，需要实现从本地根据type获得对应所有event的id，然后根据id读取数据。
            List<EventBrief> briefs = Repository.getInstance().getCertainTypeEventBreif(context, type);
            if(briefs.size()>0) {
                mList = briefs;
                events.postValue(mList);
            }

        }).start();
    }


    public void itemClicked( int pos){
        // TODO: a litter detour, maybe simpler?
        events.getValue().get(pos).setAlreadyRead(true);
    }


}