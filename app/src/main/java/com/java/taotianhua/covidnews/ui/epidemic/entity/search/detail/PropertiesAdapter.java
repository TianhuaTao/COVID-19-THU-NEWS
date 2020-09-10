package com.java.taotianhua.covidnews.ui.epidemic.entity.search.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.taotianhua.covidnews.R;

import java.util.Map;

public class PropertiesAdapter extends RecyclerView.Adapter<PropertiesAdapter.PropertyViewHolder> {

    Map<String, String > properties;

    PropertiesAdapter( Map<String, String > properties){
        this.properties = properties;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PropertyView v = (PropertyView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample_property_view,parent,false);
        return new PropertyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        Map.Entry<String,String > e = ( Map.Entry<String,String >)  properties.entrySet().toArray()[position];
        String key = e.getKey();
        String val = e.getValue();
        holder.propertyView.setmProperties(key,val);
    }


    public static class PropertyViewHolder extends RecyclerView.ViewHolder{
        public PropertyView propertyView;

        public PropertyViewHolder(PropertyView v){
            super(v);
            propertyView = v;
        }
    }
    @Override
    public int getItemCount() {
        return properties.size();
    }
}
