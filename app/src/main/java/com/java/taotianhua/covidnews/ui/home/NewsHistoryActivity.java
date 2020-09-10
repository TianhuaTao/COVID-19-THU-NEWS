package com.java.taotianhua.covidnews.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.java.taotianhua.covidnews.model.Event;
import com.java.taotianhua.covidnews.model.EventBrief;
import com.java.taotianhua.covidnews.repository.HistoryManager;
import com.java.taotianhua.covidnews.repository.Repository;
import com.java.taotianhua.covidnews.R;
import com.java.taotianhua.covidnews.ui.home.detail.NewsDetailActivity;
import com.java.taotianhua.covidnews.ui.home.tabPage.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsHistoryActivity extends AppCompatActivity {

    //    List<Event> eventList;
    List<EventBrief> eventBriefList;

    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_history);

        configActivity();
    }

    private void configActivity() {

        List<String> ids = HistoryManager.getInstance().getHistory();
        eventBriefList = new ArrayList<>();
        Thread t = new Thread(() -> {
            for (String id :
                    ids) {
                Event event = Repository.getInstance().getNewsDetail(id);
//            eventList.add(event);
                eventBriefList.add(event.getBrief());
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException ignored) {

        }

        setTitle("历史记录 (" + eventBriefList.size() + "条)");
        recyclerView = (RecyclerView) findViewById(R.id.history_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new NewsAdapter(eventBriefList);  // init with empty list
        mAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int item_pos) {
                System.out.println("Clicked " + item_pos);
                Intent intent = new Intent(NewsHistoryActivity.this, NewsDetailActivity.class);
                intent.putExtra("id", eventBriefList.get(item_pos).getId());
                intent.putExtra("title", eventBriefList.get(item_pos).getTitle());

                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

}