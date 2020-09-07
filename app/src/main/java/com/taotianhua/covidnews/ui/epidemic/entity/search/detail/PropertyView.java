package com.taotianhua.covidnews.ui.epidemic.entity.search.detail;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.taotianhua.covidnews.R;

import java.util.Map;


public class PropertyView extends CardView {


    public PropertyView(@NonNull Context context) {
        super(context);
    }

    public PropertyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PropertyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setmProperties(String name,
                               String desc) {
        this.name = name;
        this.desc = desc;

        TextView textView1 = findViewById(R.id.property_name);
//        ImageView i = findViewById(R.id.arrow);
        TextView textView2 = findViewById(R.id.property_text);

        textView1.setText(name);
        textView2.setText(desc);
    }

    private String name;
    private String desc;


}
