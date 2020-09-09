package com.taotianhua.covidnews.ui.scholars;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.model.Scholar;
import com.taotianhua.covidnews.repository.Repository;

/**
 * TODO: document your custom view class.
 */
public class ScholarListItemView extends CardView {

    Scholar scholar;

    public ScholarListItemView(Context context) {
        super(context);
        init(null, 0);
    }

    public ScholarListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ScholarListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

    }


    public void setScholar(Scholar scholar) {
        this.scholar = scholar;
        ImageView imageView;
        TextView textView;
        imageView = findViewById(R.id.scholar_photo);
        new Thread(()->{
            Bitmap bm = Repository.getInstance().getBitmapWithUrl(scholar.getAvatar_url());
            imageView.post(()->{
                imageView.setImageBitmap(bm);
            });
        }).start();

        // set photo
        textView = findViewById(R.id.scholar_name_view);
        textView.setText(scholar.getCompleteName());

        textView = findViewById(R.id.scholar_position_view);
        textView.setText(scholar.getProfile().getPosition());

        textView = findViewById(R.id.scholar_affiliation_view);
        textView.setText(scholar.getProfile().getProperAffiliation());

        if(scholar.isIs_passedaway()){
            textView = findViewById(R.id.scholar_list_pass_away);
            textView.setVisibility(VISIBLE);
        }

    }
}
