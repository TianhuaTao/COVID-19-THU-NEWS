package com.taotianhua.covidnews.ui.scholars;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taotianhua.covidnews.COVIDNewsApplication;
import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.model.EventBrief;
import com.taotianhua.covidnews.model.Scholar;


import java.util.List;

public class ScholarAdapter extends RecyclerView.Adapter<ScholarAdapter.ScholarViewHolder> {
    private List<Scholar> mDataset;

    public ScholarAdapter(List<Scholar> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public ScholarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        ScholarListItemView v = (ScholarListItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample_scholar_list_item_view, parent, false);

        ScholarAdapter.ScholarViewHolder vh = new ScholarAdapter.ScholarViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScholarViewHolder holder, int position) {
        Scholar scholar = mDataset.get(position);
        holder.cardView.setScholar(scholar);
    }

    public void setDataSet(List<Scholar> scholarList) {
        mDataset.clear();
        mDataset.addAll(scholarList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ScholarViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ScholarListItemView cardView;

        public ScholarViewHolder(ScholarListItemView v, OnItemClickListener listener) {
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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private ScholarAdapter.OnItemClickListener mListener;

    public void setOnItemClickListener(ScholarAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
