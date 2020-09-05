package com.taotianhua.covidnews.ui.epidemic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.taotianhua.covidnews.R;

public class EpidemicFragment extends Fragment {

    private EpidemicViewModel epidemicViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        epidemicViewModel =
                ViewModelProviders.of(this).get(EpidemicViewModel.class);
        View root = inflater.inflate(R.layout.fragment_epidemic, container, false);


        return root;
    }
}