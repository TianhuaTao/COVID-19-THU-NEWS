package com.taotianhua.covidnews.ui.home.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.taotianhua.covidnews.repository.HistoryManager;
import com.taotianhua.covidnews.R;

import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity {

    private NewsDetailViewModel newsDetailViewModel;
    private String id;

    private TextView viewTitle;
    private TextView viewInfo;
    private TextView viewContent;
    private TextView viewLabel;
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
        viewLabel = (TextView) findViewById(R.id.detail_label);
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
            String sourceLine = "来源: "+((event.getSource() == null|| event.getSource().isEmpty() )?  "网络": event.getSource());
            String timeLine =  "时间: " +((event.getTime() == null || event.getTime().isEmpty())? "未知" : event.getTime());

            viewInfo.setText(sourceLine + "\n" + timeLine);

            ArrayList<String> labels = event.getLabels();
            String labelStr = "#";
            for(int i = 0; i < labels.size(); ++i){
                labelStr += labels.get(i) + " #";
            }
            labelStr = labelStr.substring(0, labelStr.length()-1); //去掉最后一个#
            viewLabel.setText(labelStr);
//            viewLabel.setTextColor(Color.argb(1,40,155,226));


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