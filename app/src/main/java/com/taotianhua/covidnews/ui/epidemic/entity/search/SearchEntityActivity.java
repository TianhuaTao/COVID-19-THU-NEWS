package com.taotianhua.covidnews.ui.epidemic.entity.search;

import androidx.appcompat.app.AppCompatActivity;
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
import com.taotianhua.covidnews.ui.epidemic.entity.search.detail.EntityDetailActivity;
import com.taotianhua.covidnews.ui.home.detail.NewsDetailActivity;
import com.taotianhua.covidnews.ui.home.tabPage.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchEntityActivity extends AppCompatActivity {

private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private EntityAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private MutableLiveData<List<Entity>> entitiesData;
    String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_entity);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
             query = intent.getStringExtra(SearchManager.QUERY);
            setTitle("搜索 "+"\""+query+"\"");
            configActivity();
            doMySearch(query);
        }
    }

    private void doMySearch(String query){
        new Thread(()->{
            List<Entity> results=  Repository.getInstance().queryEntity(query);
            entitiesData.postValue(results);
        }).start();

    }

    private void configActivity(){
        progressBar = findViewById(R.id.entity_search_progress_bar);
        mAdapter = new EntityAdapter(new ArrayList<>());    // first empty


        recyclerView = findViewById(R.id.search_entity_result_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        entitiesData = new MutableLiveData<>();
        entitiesData.observe(this, entities -> {
            Log.i("SearchEntityActivity","entities data changed");
            List<Entity> dataList= entitiesData.getValue();


            mAdapter.setOnItemClickListener(new  EntityAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int item_pos) {
                    System.out.println("Clicked Entity "+item_pos);
                    Intent intent = new Intent(SearchEntityActivity.this, EntityDetailActivity.class);
                    intent.putExtra("entity",dataList.get(item_pos));
                    startActivity(intent);
                }
            });


            mAdapter.setDataSet(dataList);
            progressBar.setVisibility(View.INVISIBLE);

            int count = dataList==null? 0: dataList.size();
            setTitle("搜索 "+"\""+this.query+"\""+" ("+count+"个结果)");
        });

    }

}