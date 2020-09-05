package com.taotianhua.covidnews.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taotianhua.covidnews.network.Api;
import com.taotianhua.covidnews.model.Event;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsDetailViewModel extends ViewModel {



    private MutableLiveData<Event> mEvent;
    public LiveData<Event> getEvent(String newsID) {
        if(mEvent ==null){
            mEvent = new MutableLiveData<>();
            loadDetailAsync(newsID);
        }
        return mEvent;
    }

    private void loadDetailAsync(String id){
        // Do an asynchronous operation to fetch event.
        new Thread(()->{
            String listJson = Api.getNewsDetailJson(id);
            try {
                JSONObject jObject = new JSONObject(listJson);
                JSONObject data= jObject.getJSONObject("data");
                Event e = Event.fromJson(data);
                mEvent.postValue(e);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
