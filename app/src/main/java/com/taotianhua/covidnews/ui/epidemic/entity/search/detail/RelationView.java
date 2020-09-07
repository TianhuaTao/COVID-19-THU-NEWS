package com.taotianhua.covidnews.ui.epidemic.entity.search.detail;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.model.Entity;


public class RelationView extends CardView {


    public RelationView(Context context) {
        super(context);
        init(null, 0);
    }

    public RelationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RelationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

    }


    public void setRelation(Entity.COVID.Entity_Relation mRelation) {
        this.mRelation = mRelation;
        TextView textView1 = findViewById(R.id.relation_type);
//        ImageView i = findViewById(R.id.arrow);
        TextView textView2 = findViewById(R.id.related_entity);

        textView1.setText(mRelation.getType());
        textView2.setText(mRelation.getLabel());
    }

    private Entity.COVID.Entity_Relation mRelation;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


}
