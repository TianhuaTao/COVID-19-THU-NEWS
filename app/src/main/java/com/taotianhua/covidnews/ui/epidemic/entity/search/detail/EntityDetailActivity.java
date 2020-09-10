package com.taotianhua.covidnews.ui.epidemic.entity.search.detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.model.Entity;
import com.taotianhua.covidnews.ui.epidemic.entity.search.EntityAdapter;
import com.taotianhua.covidnews.ui.epidemic.entity.search.EntityListItemView;

import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.View.inflate;

public class EntityDetailActivity extends AppCompatActivity {


    private Entity mEntity;
    TextView labelView;
    TextView descriptionView;
    ImageView imageView;

    RecyclerView realationsRecyclerView;

    RecyclerView propertiesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_detail);

        getIncomingIntent();
        configUI();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("entity")) {
            mEntity = (Entity) getIntent().getSerializableExtra("entity");
        }
    }

    private void configUI() {

        setTitle(mEntity.getLabel());
        labelView = findViewById(R.id.entity_detail_label);
        descriptionView = findViewById(R.id.entity_detail_description_text);
        imageView = findViewById(R.id.entity_detail_image_view);

        labelView.setText(mEntity.getLabel());

        String desc = mEntity.getAbstractInfo().getDescription();
        if (desc.isEmpty())
            descriptionView.setText("暂无信息");
        else
            descriptionView.setText(desc);


        imageView.setImageDrawable(null);
        imageView.setVisibility(GONE);
        // fetch picture in background and update UI
        new Thread(() -> {
            Bitmap bm = mEntity.fetchImage();
            if (bm != null) {
                imageView.post(() -> {
                    imageView.setVisibility(VISIBLE);
                    imageView.setImageBitmap(bm);
                });
            }
        }).start();


        // relations
        realationsRecyclerView = findViewById(R.id.relations_recycler);
        realationsRecyclerView.setHasFixedSize(true);
//       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this){
//           @Override
//           public boolean canScrollVertically() {
//               return false;
//           }
//       };
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);


        realationsRecyclerView.setLayoutManager(layoutManager);
        List<Entity.COVID.Entity_Relation> r = mEntity.getAbstractInfo().getCovid().getRelations();
        if (!r.isEmpty()) {
            TextView v = findViewById(R.id.entity_detail_relation_text);
            v.setVisibility(GONE);
        }

        RelationsAdapter adapter = new RelationsAdapter(r);
        realationsRecyclerView.setAdapter(adapter);

        // properties
        propertiesRecyclerView = findViewById(R.id.properties_recycler);
        propertiesRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        propertiesRecyclerView.setLayoutManager(layoutManager);

        Map<String, String> p = mEntity.getAbstractInfo().getCovid().getProperties();
        if (!p.isEmpty()) {
            TextView v = findViewById(R.id.entity_detail_property_text);
            v.setVisibility(GONE);
        }
        PropertiesAdapter adapter2 = new PropertiesAdapter(p);
        propertiesRecyclerView.setAdapter(adapter2);
    }


}


