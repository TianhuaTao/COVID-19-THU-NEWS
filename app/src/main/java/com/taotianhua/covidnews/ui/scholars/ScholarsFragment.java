package com.taotianhua.covidnews.ui.scholars;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.model.EventBrief;
import com.taotianhua.covidnews.model.Scholar;
import com.taotianhua.covidnews.repository.Repository;
import com.taotianhua.covidnews.ui.home.NewsSearchResultActivity;
import com.taotianhua.covidnews.ui.home.detail.NewsDetailActivity;
import com.taotianhua.covidnews.ui.home.tabPage.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScholarsFragment extends Fragment {

    private ScholarsViewModel mViewModel;
    private RecyclerView recyclerView;
    List<Scholar> mList = new ArrayList<>();

    private ScholarAdapter mAdapter;
    ProgressBar progressBar;
    private MutableLiveData<List<Scholar>> scholars;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scholars, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ScholarsViewModel.class);

    }

    private LiveData<List<Scholar>> getScholars() {
        if (scholars == null) {
            scholars = new MutableLiveData<>();
            loadScholarsAsync();
        }
        return scholars;
    }

    private void loadScholarsAsync() {
        // Do an asynchronous operation to fetch events for this catalog

        new Thread(() -> {
            Log.i("ScholarsFragment", "loadScholarsAsync");

            mList = Repository.getInstance().loadAllScholars();
            scholars.postValue(mList);
            progressBar.post(() -> {
                progressBar.setVisibility(View.GONE);
            });
        }).start();
    }

    private LinearLayoutManager layoutManager;
    private boolean loading = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.scholars_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ScholarAdapter(new ArrayList<>());  // init with empty list

        recyclerView.setAdapter(mAdapter);

        progressBar = view.findViewById(R.id.search_progress);


        getScholars().observe(getViewLifecycleOwner(), results -> {
            Log.i("ScholarsFragment", "observing changes");
            loading = false;
            List<Scholar> dataList = getScholars().getValue();

            mAdapter.setOnItemClickListener(new ScholarAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int item_pos) {
                    System.out.println("Clicked " + item_pos);
                    Intent intent = new Intent(ScholarsFragment.this.getContext(), ScholarDetailActivity.class);
                    intent.putExtra("index", item_pos);
                    startActivity(intent);
                }
            });

            mAdapter.setDataSet(dataList);
        });
    }
}