package com.taotianhua.covidnews.ui.home.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.taotianhua.covidnews.repository.HistoryManager;
import com.taotianhua.covidnews.R;

public class NewsDetailActivity extends AppCompatActivity {

    private NewsDetailViewModel newsDetailViewModel;
    private String id;

    private TextView viewTitle;
    private TextView viewInfo;
    private TextView viewContent;
//    private LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
//        layout = findViewById(R.id.news_detail_linear_layout);
//        layout.setMovementMethod(new ScrollingMovementMethod());

        newsDetailViewModel = new ViewModelProvider(this).get(NewsDetailViewModel.class);
        viewTitle = (TextView) findViewById(R.id.detail_title);
        viewContent = (TextView) findViewById(R.id.detail_content);
        viewInfo = (TextView) findViewById(R.id.detail_info);
        getIncomingIntent();


        newsDetailViewModel.getEvent(id).observe(this, event -> {
            if (event == null)
                return;
            viewTitle.setText(event.getTitle());
            String content = event.getContent();
            if (content == null || content.length() == 0) {
                viewContent.setText("该新闻内容为空");
            } else {
                viewContent.setText(content);
            }
            String sourceLine = event.getSource() == null ? "" : "来源: " + event.getSource();
            String timeLine = event.getTime() == null ? "" : "时间: " + event.getTime();

            viewInfo.setText(sourceLine + "\n" + timeLine);
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


}