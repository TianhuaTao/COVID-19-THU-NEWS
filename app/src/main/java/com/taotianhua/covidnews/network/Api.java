package com.taotianhua.covidnews.network;

import android.util.JsonReader;
import android.util.Pair;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Api {
   private static final OkHttpClient client = new OkHttpClient();

    public static  String getJson(String url){
        final Request request = new Request.Builder().url(url).build();
        String  json_str = null;
        try (Response response = client.newCall(request).execute()) {
            json_str = response.body().string();
        }catch (IOException e){
            e.printStackTrace();
        }
        return json_str;
    }

    public static   String getEventListJson()  {
        return getJson( UrlConst.genUrlListPagination("all",1,20));
    }

    public static   String  getNewsDetailJson(String id)  {
        return getJson(  UrlConst.genUrlEventDetail(id));
    }


  }

