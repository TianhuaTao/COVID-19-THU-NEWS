package com.java.taotianhua.covidnews.ui.epidemic.entity.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.java.taotianhua.covidnews.model.Entity;
import com.java.taotianhua.covidnews.R;

public class EntityListItemView extends CardView {
Entity mEntity;
TextView nameView;
TextView descriptionView;


    public EntityListItemView(Context context) {
        super(context);
//        init(null, 0);
    }

    public EntityListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init(attrs, 0);
    }

    public EntityListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        init(attrs, defStyle);
    }


    public void setEntity(Entity entity) {
        mEntity = entity;

        // TODO: set more fields
        nameView = findViewById(R.id.entity_name_view);
        descriptionView = findViewById(R.id.entity_description_view);
        nameView.setText(mEntity.getLabel());
        String desc = mEntity.getAbstractInfo().getDescription();
        if(desc.isEmpty()){
            desc = "点击查看详情";
        }
        descriptionView.setText(desc);

        if(entity.getHot()>0.33){
            ImageView  v=findViewById(R.id.imageView0);
            v.setVisibility(VISIBLE);
        }
        if(entity.getHot()>0.66){
            ImageView  v=findViewById(R.id.imageView1);
            v.setVisibility(VISIBLE);
        }

        // empty image view
        ImageView v= findViewById(R.id.entity_image_view);
        v.setImageDrawable(null);
        v.setVisibility(GONE);

        // fetch picture in background and update UI
        new Thread(()->{
            Bitmap bm = entity.fetchImage();
            if(bm!=null){
                v.post(()->{
                    v.setVisibility(VISIBLE);
                    v.setImageBitmap(bm);
                });
            }
        }).start();

    }
}
