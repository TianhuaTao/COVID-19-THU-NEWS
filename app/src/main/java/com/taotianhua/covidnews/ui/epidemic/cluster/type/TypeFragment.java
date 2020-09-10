package com.taotianhua.covidnews.ui.epidemic.cluster.type;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taotianhua.covidnews.R;

public class TypeFragment extends Fragment {

    private TypeViewModel mViewModel;
    private View mView;

    public static TypeFragment newInstance() {
        return new TypeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.type_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mView = view;
        handleArguments();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TypeViewModel.class);
        // TODO: Use the ViewModel
    }

    private void handleArguments(){
        Bundle args = this.getArguments();
        TextView tv = mView.findViewById(R.id.type_title);
        try{
            String s = "这是聚类" + args.getString("type") +"的页面";
            tv.setText(s);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            Log.i("MyApp", "Args not found");
        }
    }

}