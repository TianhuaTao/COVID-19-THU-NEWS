package com.java.taotianhua.covidnews.ui.epidemic.entity;

import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.java.taotianhua.covidnews.ui.epidemic.entity.search.SearchEntityActivity;
import com.java.taotianhua.covidnews.R;

public class EntityQueryFragment extends Fragment {

    private EntityQueryViewModel mViewModel;
    private View rootView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.entity_query_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EntityQueryViewModel.class);
        // TODO: Use the ViewModel
    }

    SearchView searchView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = view.findViewById(R.id.entityQuerySearchView);
        // Assumes current activity is the searchable activity
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(new ComponentName(this.getActivity(), SearchEntityActivity.class));
        searchView.setSearchableInfo(searchableInfo);
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        rootView = getActivity().findViewById(R.id.entity_query_scroll_view);
    }


}