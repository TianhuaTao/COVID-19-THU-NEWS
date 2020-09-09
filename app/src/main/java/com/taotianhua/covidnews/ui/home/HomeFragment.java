package com.taotianhua.covidnews.ui.home;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.taotianhua.covidnews.R;


public class HomeFragment extends Fragment {

    ViewPager viewPager;
    HomePagerAdapter homePagerAdapter;

    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager(), homeViewModel);
        homeViewModel.setHomePagerAdapter(homePagerAdapter);
        viewPager = view.findViewById(R.id.home_pager);
        viewPager.setAdapter(homePagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.top_home_menu,menu);


        SearchManager searchManager =
                (SearchManager) getActivity(). getSystemService(Context.SEARCH_SERVICE);

        SearchableInfo searchableInfo = searchManager.getSearchableInfo(
                new ComponentName(getActivity(),
                        NewsSearchResultActivity.class));


        SearchView searchView = (SearchView) menu.findItem(R.id.home_search_view).getActionView();
        searchView.setSearchableInfo(searchableInfo);
        searchView.setIconifiedByDefault(false);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.settings_menu_item) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("catalog_dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            DialogFragment dialogFragment = new CatalogDialogFragment(this.homeViewModel);
            dialogFragment.show(ft, "catalog_dialog");
            return true;
        }
        return false;
    }
}