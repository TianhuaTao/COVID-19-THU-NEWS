package com.taotianhua.covidnews.ui.epidemic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EpidemicViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EpidemicViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}