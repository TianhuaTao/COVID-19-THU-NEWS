package com.taotianhua.covidnews.ui.home.tabPage;

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

import com.taotianhua.covidnews.R;

/**
 * TODO: document your custom view class.
 */
public class NewsListItemView extends CardView {




//    private TextPaint mTextPaint;



    public void setTitle(String title){
        TextView textView = (TextView) findViewById(R.id.titleText);
        textView.setText(title);
    }
    public void setContent(String content){
        TextView textView = (TextView)  findViewById(R.id.contentText);
        if(content==null || content.length()==0){
            textView.setText("点击查看更多");
        }else {
            textView.setText(content);
        }
    }


    public NewsListItemView(Context context) {
        super(context);
//        init(null, 0);
    }

    public NewsListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init(attrs, 0);
    }

    public NewsListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        init(attrs, defStyle);
    }

//    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
//        final TypedArray a = getContext().obtainStyledAttributes(
//                attrs, R.styleable.NewsListItemView, defStyle, 0);



//        if (a.hasValue(R.styleable.NewsListItemView_exampleDrawable)) {
//            mExampleDrawable = a.getDrawable(
//                    R.styleable.NewsListItemView_exampleDrawable);
//            mExampleDrawable.setCallback(this);
//        }

//        a.recycle();

        // Set up a default TextPaint object
//        mTextPaint = new TextPaint();
//        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
//        invalidateTextPaintAndMeasurements();
//    }

//    private void invalidateTextPaintAndMeasurements() {


//        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        mTextHeight = fontMetrics.bottom;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // allocations per draw cycle.
//        int paddingLeft = getPaddingLeft();
//        int paddingTop = getPaddingTop();
//        int paddingRight = getPaddingRight();
//        int paddingBottom = getPaddingBottom();
//
//        int contentWidth = getWidth() - paddingLeft - paddingRight;
//        int contentHeight = getHeight() - paddingTop - paddingBottom;



//        // Draw the example drawable on top of the text.
//        if (mExampleDrawable != null) {
//            mExampleDrawable.setBounds(paddingLeft, paddingTop,
//                    paddingLeft + contentWidth, paddingTop + contentHeight);
//            mExampleDrawable.draw(canvas);
//        }
    }


   }
