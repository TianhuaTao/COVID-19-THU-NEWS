package com.taotianhua.covidnews.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.taotianhua.covidnews.R;


public class CatalogDialogFragment extends DialogFragment {


    public CatalogDialogFragment(HomeViewModel homeViewModel) {
    this.viewModel = homeViewModel;
    }

    ToggleButton newsButton;
    ToggleButton paperButton;
    ToggleButton eventButton;
    ToggleButton pointsButton;

    HomeViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_catalog_dialog, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        newsButton = view.findViewById(R.id.newsButton);
        paperButton =view.findViewById(R.id.paperButton);
        eventButton = view.findViewById(R.id.eventButton);
        pointsButton = view.findViewById(R.id.pointsButton);

        newsButton.setChecked(viewModel.isSelected(0));
        paperButton.setChecked(viewModel.isSelected(1));
        eventButton.setChecked(viewModel.isSelected(2));
        pointsButton.setChecked(viewModel.isSelected(3));

        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setSelected(0, newsButton.isChecked());
            }
        });

        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setSelected(1, paperButton.isChecked());

            }
        });
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setSelected(2, eventButton.isChecked());

            }
        });
        pointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setSelected(3, pointsButton.isChecked());

            }
        });
    }
}