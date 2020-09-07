package com.taotianhua.covidnews.ui.epidemic.entity.search.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.model.Entity;

import java.util.List;

class RelationsAdapter extends RecyclerView.Adapter<RelationsAdapter.RelationViewHolder>{

    List<Entity.COVID.Entity_Relation> mRelations;
    RelationsAdapter(List<Entity.COVID.Entity_Relation> relations){
        mRelations = relations;
    }
    @NonNull
    @Override
    public RelationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RelationView v = (RelationView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.sample_relation_view, parent, false);

        return new RelationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RelationViewHolder holder, int position) {
        Entity.COVID.Entity_Relation r = mRelations.get(position);
        holder.relationView.setRelation(r);
    }

    @Override
    public int getItemCount() {
        return mRelations.size();
    }

    public static class RelationViewHolder extends RecyclerView.ViewHolder{
        public RelationView relationView;

        public RelationViewHolder(RelationView v){
            super(v);
            relationView = v;
        }
    }
}