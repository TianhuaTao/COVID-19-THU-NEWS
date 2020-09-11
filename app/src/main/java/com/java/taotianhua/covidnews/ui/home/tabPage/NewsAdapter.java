package com.java.taotianhua.covidnews.ui.home.tabPage;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.java.taotianhua.covidnews.COVIDNewsApplication;
import com.java.taotianhua.covidnews.model.EventBrief;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.java.taotianhua.covidnews.R.*;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<EventBrief> mDataset;


    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public NewsListItemView cardView;

        public NewsViewHolder(NewsListItemView v, OnItemClickListener listener) {
            super(v);
            cardView = v;

            v.setOnClickListener(view -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
                view.setBackgroundColor(COVIDNewsApplication.getAppContext().getColor(color.colorRead));

            });
        }
    }


    public NewsAdapter(List<EventBrief> myDataset) {
        mDataset = myDataset;
//        mDataset.add(null);
    }

    public void setDataSet(List<EventBrief> newList) {
        mDataset.clear();
        if (newList != null) {
            mDataset.addAll(newList);
        }
        notifyDataSetChanged();
    }


    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {

        // create a new view
        NewsListItemView v = (NewsListItemView) LayoutInflater.from(parent.getContext())
                .inflate(layout.sample_news_list_item_view, parent, false);

        return new NewsViewHolder(v, mListener);


    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        EventBrief brief = mDataset.get(position);
        holder.cardView.setTitle(brief.getTitle());
        holder.cardView.setContent(brief.getContent());
        if (brief.getAlreadyRead()) {
            holder.cardView.setBackgroundColor(COVIDNewsApplication.getAppContext().getColor(color.colorRead));
        } else {
            holder.cardView.setBackgroundColor(Color.WHITE);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
