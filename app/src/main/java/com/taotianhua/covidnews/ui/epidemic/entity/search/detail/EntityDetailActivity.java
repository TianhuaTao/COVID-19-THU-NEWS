package com.taotianhua.covidnews.ui.epidemic.entity.search.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.model.Entity;

public class EntityDetailActivity extends AppCompatActivity {


    private Entity mEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_detail);

        getIncomingIntent();
        configUI();
    }

    private void getIncomingIntent() {
        if ( getIntent().hasExtra("entity")) {
             mEntity = (Entity) getIntent().getSerializableExtra("entity");
        }
    }

    private void configUI(){
        setTitle(mEntity.getLabel());
    }
}