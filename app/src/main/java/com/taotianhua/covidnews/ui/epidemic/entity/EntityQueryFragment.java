package com.taotianhua.covidnews.ui.epidemic.entity;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taotianhua.covidnews.R;

public class EntityQueryFragment extends Fragment {

    private EntityQueryViewModel mViewModel;

    public static EntityQueryFragment newInstance() {
        return new EntityQueryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.entity_query_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EntityQueryViewModel.class);
        // TODO: Use the ViewModel
    }

}