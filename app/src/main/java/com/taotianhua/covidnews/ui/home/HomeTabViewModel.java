package com.taotianhua.covidnews.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taotianhua.covidnews.model.EventBrief;
import com.taotianhua.covidnews.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class HomeTabViewModel extends ViewModel {

    private MutableLiveData<List<EventBrief>> events;
    List<EventBrief> mList = new ArrayList<>();
    public LiveData<List<EventBrief>> getEvents() {
        if(events ==null){
            events = new MutableLiveData<>();
            loadEventsAsync();
        }
        return events;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    private String catalog;


    private void loadEventsAsync( ){
        // Do an asynchronous operation to fetch events for this catalog
        new Thread(()->{
            // TODO: filter catalog
//            List<EventBrief> briefs = Repository.getInstance().loadMoreEventBriefs(catalog, mList.size(),20);
//            mList.addAll(briefs);

            List<EventBrief> briefs = Repository.getInstance().refresh(catalog,20);
            mList = briefs;
            events.postValue(mList);
        }).start();
    }

    public void loadMoreAtBottom(){
        new Thread(()->{

            List<EventBrief> briefs = Repository.getInstance().loadMoreEventBriefs(catalog, mList.size(),20);
            mList.addAll(briefs);
            events.postValue(mList);
        }).start();
    }


    public void refresh()  {

        // 拉长时间，不然看上去就好像没有更新一样
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        loadEventsAsync( );
    }

    public void itemClicked( int pos){

        events.getValue().get(pos).setAlreadyRead(true);
    }
}
