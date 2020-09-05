package com.taotianhua.covidnews.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taotianhua.covidnews.network.Api;
import com.taotianhua.covidnews.model.EventBrief;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<EventBrief>> events;

    public HomeViewModel() {
    }


    public LiveData<List<EventBrief>> getEvents() {
        if(events ==null){
            events = new MutableLiveData<>();
            loadEventsAsync();
        }
        return events;
    }

    private void loadEventsAsync(){
        // Do an asynchronous operation to fetch events.
        new Thread(()->{
            String listJson = Api.getEventListJson();
            try {
                JSONObject jObject = new JSONObject(listJson);
                JSONArray data= jObject.getJSONArray("data");
                List<EventBrief> briefs = new ArrayList<>();
                for (int i = 0; i < data.length(); i++) {
                    EventBrief brief = EventBrief.fromJson(data.getJSONObject(i));
                    briefs.add(brief);
                }
                events.postValue(briefs);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }
}