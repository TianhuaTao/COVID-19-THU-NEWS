package com.taotianhua.covidnews.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.model.Entity;
import com.taotianhua.covidnews.model.EventBrief;
import com.taotianhua.covidnews.repository.Repository;
import com.taotianhua.covidnews.ui.epidemic.entity.search.EntityAdapter;
import com.taotianhua.covidnews.ui.epidemic.entity.search.SearchEntityActivity;
import com.taotianhua.covidnews.ui.epidemic.entity.search.detail.EntityDetailActivity;
import com.taotianhua.covidnews.ui.home.detail.NewsDetailActivity;
import com.taotianhua.covidnews.ui.home.tabPage.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsSearchResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    List<EventBrief> mList = new ArrayList<>();
String query ;
    private MutableLiveData<List<EventBrief>> events;
    private boolean loading = false;
ProgressBar progressBar;
    private LiveData<List<EventBrief>> getEvents() {
        if(events ==null){
            events = new MutableLiveData<>();
            loadSearchResultsAsync();
        }
        return events;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_search_result);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            this.query  = intent.getStringExtra(SearchManager.QUERY);

            setTitle("Search for "+"\""+query+"\"");
            configActivity();
            doMySearch(query);
        }

    }
    private void doMySearch(String query){
        getEvents().observe(this,events->{
            // Update UI
            Log.i("NewsSearchResultActivity","observing changes");
            loading = false;
            List<EventBrief> dataList= getEvents().getValue();

            mAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int item_pos) {
                    System.out.println("Clicked "+item_pos);
                    Intent intent = new Intent(NewsSearchResultActivity.this, NewsDetailActivity.class);
                    intent.putExtra("id",dataList.get(item_pos).getId());
                    intent.putExtra("title",dataList.get(item_pos).getTitle());

                    startActivity(intent);
                }
            });

            mAdapter.setDataSet(dataList);
        });

    }

    private void configActivity(){
        recyclerView = (RecyclerView) findViewById(R.id.news_search_result_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new NewsAdapter(new ArrayList<>());  // init with empty list

        recyclerView.setAdapter(mAdapter);

        progressBar = findViewById(R.id.search_progress);

    }
    private void loadSearchResultsAsync( ){
        // Do an asynchronous operation to fetch events for this catalog

        new Thread(()->{
            Log.i("NewsSearchResultActivity", "loadSearchResultsAsync");

                mList = Repository.getInstance().loadNewsSearchResult(this.query,100);
                events.postValue(mList);
                progressBar.post(()->{
                    progressBar.setVisibility(View.GONE);
                });
        }).start();
    }
}