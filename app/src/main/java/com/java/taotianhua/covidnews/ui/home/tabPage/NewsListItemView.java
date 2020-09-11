package com.java.taotianhua.covidnews.ui.home.tabPage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.java.taotianhua.covidnews.R;


public class NewsListItemView extends CardView {

    public void setTitle(String title) {
        TextView textView = (TextView) findViewById(R.id.titleText);
        textView.setText(title);
    }

    public void setContent(String content) {
        TextView textView = (TextView) findViewById(R.id.contentText);
        if (content == null || content.length() == 0) {
            textView.setText("点击查看更多");
        } else {
            textView.setText(content);
        }
    }


    public NewsListItemView(Context context) {
        super(context);
    }

    public NewsListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewsListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
