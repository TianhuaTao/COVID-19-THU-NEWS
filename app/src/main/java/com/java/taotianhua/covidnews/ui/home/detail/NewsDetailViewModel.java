package com.java.taotianhua.covidnews.ui.home.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.java.taotianhua.covidnews.model.Event;
import com.java.taotianhua.covidnews.repository.Repository;


public class NewsDetailViewModel extends ViewModel {
    private MutableLiveData<Event> mEvent;

    public LiveData<Event> getEvent(String newsID) {
        if (mEvent == null) {
            mEvent = new MutableLiveData<>();
            loadDetailAsync(newsID);
        }
        return mEvent;
    }

    private void loadDetailAsync(String id) {
        // Do an asynchronous operation to fetch event.
        new Thread(() -> {
            Event event = Repository.getInstance().getNewsDetail(id);  // time consuming
            mEvent.postValue(event);
        }).start();
    }
}
