package com.taotianhua.covidnews.ui.home.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.taotianhua.covidnews.R;

public class NewsDetailActivity extends AppCompatActivity {

    private NewsDetailViewModel newsDetailViewModel;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsDetailViewModel = new ViewModelProvider(this).get(NewsDetailViewModel.class);
        getIncomingIntent();
        newsDetailViewModel.getEvent(id).observe(this,event -> {
            TextView v_title = (TextView) findViewById(R.id.detail_title);
            TextView v_content= (TextView) findViewById(R.id.detail_content);
            v_title.setText(event.getTitle());
            String content = event.getContent();
            if(content==null || content.length()==0){
                v_content.setText("该新闻内容为空");
            }else {
                v_content.setText(content);
            }
            v_content.setMovementMethod(new ScrollingMovementMethod());
        });

    }

    private  void getIncomingIntent(){
        if(getIntent().hasExtra("id")&& getIntent().hasExtra("title")){
            id = getIntent().getStringExtra("id");
            String title = getIntent().getStringExtra("title");

            TextView v_title = (TextView) findViewById(R.id.detail_title);

            v_title.setText(title);

        }
    }


}