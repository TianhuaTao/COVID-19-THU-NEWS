package com.taotianhua.covidnews.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.model.EventBrief;

import java.util.List;

public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{
    private List<EventBrief> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public NewsListItemView cardView;
        public NewsViewHolder(NewsListItemView v,OnItemClickListener listener) {
            super(v);
            cardView = v;

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsAdapter(List<EventBrief> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        NewsListItemView v = (NewsListItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample_news_list_item_view, parent, false);

        NewsViewHolder vh = new NewsViewHolder(v,mListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        holder.cardView.setTitle(mDataset.get(position).getTitle());
        holder.cardView.setContent(mDataset.get(position).getContent());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public interface OnItemClickListener{
     void onItemClick(int position);
    }
private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
