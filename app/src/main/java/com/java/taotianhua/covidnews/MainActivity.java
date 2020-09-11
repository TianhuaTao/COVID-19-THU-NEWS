package com.java.taotianhua.covidnews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.java.taotianhua.covidnews.repository.HistoryManager;
import com.java.taotianhua.covidnews.ui.user.AboutActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_epidemic,
                R.id.navigation_scholars,
                R.id.navigation_user
        )
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void onClearHistory(View view) {
        Log.i("", "ClearHistory clicked");
        HistoryManager.getInstance().clearHistory();
        Toast.makeText(this,
                "清除历史记录",
                Toast.LENGTH_LONG).show();
    }

    public void onAbout(View view) {
        Log.i("", "About clicked");
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void onClearCache(View view) {
        Log.i("", "ClearCache clicked");
        Toast.makeText(this,
                "暂不支持清理缓存",
                Toast.LENGTH_LONG).show();
    }


}