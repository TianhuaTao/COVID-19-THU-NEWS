package com.java.taotianhua.covidnews.ui.home.tabPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.taotianhua.covidnews.model.EventBrief;
import com.java.taotianhua.covidnews.R;
import com.java.taotianhua.covidnews.ui.home.detail.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;


public class HomeTabFragment extends Fragment {

    private HomeTabViewModel homeTabViewModel;
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    int position;
    String catalog;
    private boolean loading = false;
    public HomeTabFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
            catalog = getArguments().getString("catalog");

            System.out.println("Catalog "+position+": "+catalog);
        }

        mAdapter = new NewsAdapter(new ArrayList<>());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(homeTabViewModel==null){
            homeTabViewModel = new ViewModelProvider(this).get(HomeTabViewModel.class);
            homeTabViewModel.setCatalog(catalog);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i("HomeTabFragment "+position,"onViewCreated" );
        swipeRefreshLayout = getView().findViewById(R.id.news_refresh_layout);
        refreshListener = () -> {
            swipeRefreshLayout.setRefreshing(true);
            // call api to refresh
                Thread t =new Thread(()->{
                    homeTabViewModel.refresh( );
                    swipeRefreshLayout.setRefreshing(false);

                });
                t.start();
        };
        swipeRefreshLayout.setOnRefreshListener(refreshListener);

        recyclerView = (RecyclerView) getView().findViewById(R.id.news_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int pastVisiblesItems, visibleItemCount, totalItemCount;

                if (dy > 0) { //check for scroll down
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = true;
                            Log.v("HomeTabFragment", "Last Item !");
                            homeTabViewModel.loadMoreAtBottom();

                        }
                    }
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        homeTabViewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            // update UI
            Log.i("HomeTabViewModel "+position,"observing changes");
            loading = false;

            mAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int item_pos) {
                    List<EventBrief> dataList= homeTabViewModel.getEvents().getValue(); // get new data every time
                    System.out.println("Clicked "+item_pos);
                    Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                    intent.putExtra("id",dataList.get(item_pos).getId());
                    intent.putExtra("title",dataList.get(item_pos).getTitle());
                    homeTabViewModel.itemClicked(item_pos );
                    getContext().startActivity(intent);
                }
            });

            mAdapter.setDataSet(homeTabViewModel.getEvents().getValue());

        });
    }
}