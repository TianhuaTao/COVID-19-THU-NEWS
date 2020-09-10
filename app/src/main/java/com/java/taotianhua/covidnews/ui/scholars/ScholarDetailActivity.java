package com.java.taotianhua.covidnews.ui.scholars;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.taotianhua.covidnews.R;
import com.java.taotianhua.covidnews.model.Scholar;
import com.java.taotianhua.covidnews.repository.Repository;

public class ScholarDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar_detail);
        getIncomingIntent();

    }

    private Scholar mScholar;


    private void getIncomingIntent() {
        if (getIntent().hasExtra("index")) {
            int index = getIntent().getIntExtra("index", -1);
            if (index == -1) {
                // Error
                return;
            }
            mScholar = Repository.getInstance().loadAllScholars().get(index);
            configUI();
        }

    }

    private void configUI() {
        ImageView imageView = findViewById(R.id.scholar_detail_photo);
        new Thread(() -> {
            Bitmap bm = Repository.getInstance().getBitmapWithUrl(mScholar.getAvatar_url());
            imageView.post(() -> {
                imageView.setImageBitmap(bm);
            });
        }).start();

        TextView textView = findViewById(R.id.scholar_detail_name);
        textView.setText(mScholar.getCompleteName());

        textView = findViewById(R.id.scholar_detail_position);
        textView.setText(mScholar.getProfile().getPosition());

        textView = findViewById(R.id.scholar_detail_aff);
        textView.setText(mScholar.getProfile().getProperAffiliation());

        textView = findViewById(R.id.bio_text);
        textView.setText(mScholar.getProfile().getBio());

        // set index

        textView = findViewById(R.id.index_a_text);
        textView.setText(Double.toString(mScholar.getIndices().getActivity()));
        textView = findViewById(R.id.index_h_text);
        textView.setText(Integer.toString(mScholar.getIndices().getHindex()));
        textView = findViewById(R.id.index_s_text);
        textView.setText(Double.toString(mScholar.getIndices().getNewStar()));
        textView = findViewById(R.id.index_c_text);
        textView.setText(Integer.toString(mScholar.getIndices().getCitations()));
        textView = findViewById(R.id.index_p_text);
        textView.setText(Integer.toString(mScholar.getIndices().getPubs()));

        if (mScholar.isIs_passedaway()) {
            textView = findViewById(R.id.passAway_view);
            textView.setVisibility(View.VISIBLE);
        }
    }
}