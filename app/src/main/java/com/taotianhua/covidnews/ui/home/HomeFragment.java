package com.taotianhua.covidnews.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.model.EventBrief;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
               new ViewModelProvider(this).get(HomeViewModel.class);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) getView().findViewById(R.id.news_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        homeViewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            // update UI
            List<EventBrief> dataList= homeViewModel.getEvents().getValue();

            mAdapter = new NewsAdapter(dataList);

            mAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    System.out.println("Clicked "+position);
                    Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                    intent.putExtra("id",dataList.get(position).getId());
                    intent.putExtra("title",dataList.get(position).getTitle());
                    getContext().startActivity(intent);
                }
            });
            recyclerView.setAdapter(mAdapter);
        });




    }


}