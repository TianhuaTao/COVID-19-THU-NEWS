package com.java.taotianhua.covidnews.ui.epidemic.entity.search.detail;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.java.taotianhua.covidnews.model.Entity;
import com.java.taotianhua.covidnews.R;


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
