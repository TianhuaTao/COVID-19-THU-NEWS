package com.java.taotianhua.covidnews.ui.epidemic.entity.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.taotianhua.covidnews.model.Entity;
import com.java.taotianhua.covidnews.R;

import java.util.List;

public class EntityAdapter extends RecyclerView.Adapter<EntityAdapter.EntityHolder>{
    private List<Entity> mDataset;

    @NonNull
    @Override
    public EntityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EntityListItemView v = (EntityListItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample_entity_list_item_view,parent, false);
        return new EntityHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EntityHolder holder, int position) {
        Entity entity = mDataset.get(position);
        holder.cardView.setEntity(entity);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class EntityHolder extends RecyclerView.ViewHolder{
        public EntityListItemView cardView;

        public EntityHolder(EntityListItemView v, OnItemClickListener listener){
            super(v);
            cardView = v;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public EntityAdapter(List<Entity> myDataset) {
        mDataset = myDataset;

    }
    public void setDataSet(List<Entity> newList) {
        // TODO: give notice if the list empty, not just a blank page
        mDataset.clear();
        mDataset.addAll(newList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private EntityAdapter.OnItemClickListener mListener;

    public void setOnItemClickListener(EntityAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}
