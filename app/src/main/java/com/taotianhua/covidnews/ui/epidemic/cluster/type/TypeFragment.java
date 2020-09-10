package com.taotianhua.covidnews.ui.epidemic.cluster.type;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.model.EventBrief;
import com.taotianhua.covidnews.ui.home.detail.NewsDetailActivity;
import com.taotianhua.covidnews.ui.home.tabPage.HomeTabViewModel;
import com.taotianhua.covidnews.ui.home.tabPage.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class TypeFragment extends Fragment {

    private TypeViewModel typeViewModel;
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    int type;
    public TypeFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new NewsAdapter(new ArrayList<>());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            type = getArguments().getInt("type");

            Log.i("TypeCode", "Type = " + String.valueOf(type));
        }

        if(typeViewModel==null){
            typeViewModel = new ViewModelProvider(this).get(TypeViewModel.class);
            typeViewModel.setType(String.valueOf(type)); //type是tab的position，也对应不同的类型，a little tricky
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.type_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = (RecyclerView) view.findViewById(R.id.type_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter);

        typeViewModel.getEvents(getContext()).observe(getViewLifecycleOwner(), events -> {
            // update UI
//            Log.i("HomeTabViewModel "+position,"observing changes");
//            loading = false;
            List<EventBrief> dataList= typeViewModel.getEvents(getContext()).getValue();

            mAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int item_pos) {
                    System.out.println("Clicked "+item_pos);
                    Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                    intent.putExtra("id",dataList.get(item_pos).getId());
                    intent.putExtra("title",dataList.get(item_pos).getTitle());
                    typeViewModel.itemClicked(item_pos );
                    getContext().startActivity(intent);
                }
            });

            mAdapter.setDataSet(dataList);

        });
    }

}