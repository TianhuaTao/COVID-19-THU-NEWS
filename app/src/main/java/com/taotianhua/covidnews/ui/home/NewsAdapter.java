package com.taotianhua.covidnews.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taotianhua.covidnews.COVIDNewsApplication;
import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.model.EventBrief;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.taotianhua.covidnews.R.*;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<EventBrief> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public NewsListItemView cardView;

        public NewsViewHolder(NewsListItemView v, OnItemClickListener listener) {
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
                    view.setBackgroundColor(COVIDNewsApplication.getAppContext().getColor(color.colorRead));

                }
            });
        }
    }

    private class LoadingHolder extends RecyclerView.ViewHolder {

        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsAdapter(List<EventBrief> myDataset) {
        mDataset = myDataset;
//        mDataset.add(null);
    }

    public void setDataSet(List<EventBrief> newList) {
        mDataset.clear();
        mDataset.addAll(newList);
        notifyDataSetChanged();
    }

    final int VIEW_TYPE_LOADING = 0;
    final int VIEW_TYPE_ITEM = 1;

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {

        // create a new view
        NewsListItemView v = (NewsListItemView) LayoutInflater.from(parent.getContext())
                .inflate(layout.sample_news_list_item_view, parent, false);

        NewsViewHolder vh = new NewsViewHolder(v, mListener);
        return vh;


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
