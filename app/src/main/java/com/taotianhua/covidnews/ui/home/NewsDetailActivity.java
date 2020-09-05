package com.taotianhua.covidnews.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.network.Api;

import org.w3c.dom.Text;

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
            v_content.setText(event.getContent());
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