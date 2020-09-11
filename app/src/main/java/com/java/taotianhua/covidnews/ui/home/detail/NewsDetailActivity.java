package com.java.taotianhua.covidnews.ui.home.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.java.taotianhua.covidnews.model.Event;
import com.java.taotianhua.covidnews.repository.HistoryManager;
import com.java.taotianhua.covidnews.R;

import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity {

    private NewsDetailViewModel newsDetailViewModel;
    private String id;

    private TextView viewTitle;
    private TextView viewInfo;
    private TextView viewContent;
    private TextView viewLabel;
    Event mEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        newsDetailViewModel = new ViewModelProvider(this).get(NewsDetailViewModel.class);
        viewTitle = (TextView) findViewById(R.id.detail_title);
        viewContent = (TextView) findViewById(R.id.detail_content);
        viewInfo = (TextView) findViewById(R.id.detail_info);
        viewLabel = (TextView) findViewById(R.id.detail_label);
        getIncomingIntent();


        newsDetailViewModel.getEvent(id).observe(this, event -> {
            if (event == null)
                return;
            mEvent = event;
            viewTitle.setText(event.getTitle());
            String content = event.getContent();
            if (content == null || content.length() == 0) {
                viewContent.setText("该新闻内容为空");
            } else {
                viewContent.setText(content);
            }
            String sourceLine = "来源: " + ((event.getSource() == null || event.getSource().isEmpty()) ? "网络" : event.getSource());
            String timeLine = "时间: " + ((event.getTime() == null || event.getTime().isEmpty()) ? "未知" : event.getTime());

            viewInfo.setText(sourceLine + "\n" + timeLine);

            ArrayList<String> labels = event.getLabels();
            String labelStr = "#";
            for (int i = 0; i < labels.size(); ++i) {
                labelStr += labels.get(i) + " #";
            }
            labelStr = labelStr.substring(0, labelStr.length() - 1); //去掉最后一个#
            viewLabel.setText(labelStr);

            HistoryManager.getInstance().addHistory(event.getId());
        });


    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title")) {
            id = getIntent().getStringExtra("id");
            String title = getIntent().getStringExtra("title");

            viewTitle.setText(title);
            viewInfo.setText("");       // clear template

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_detail_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Intent intent = new Intent(this, ShareNewsActivity.class);
                String text_to_share = "[COVID-19 News]";
                if (mEvent != null) {
                    text_to_share = mEvent.getWeiboText();
                }
                intent.putExtra("text", text_to_share);
                startActivity(intent);
                break;
        }

        return true;
    }
}