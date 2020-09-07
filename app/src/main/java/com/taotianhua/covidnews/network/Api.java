package com.taotianhua.covidnews.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.JsonReader;
import android.util.Log;
import android.util.Pair;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Api {
   private static final OkHttpClient client = new OkHttpClient();

    private static  String getJson(String url){
        final Request request = new Request.Builder().url(url).build();
        String  json_str = null;
        try (Response response = client.newCall(request).execute()) {
            json_str = response.body().string();
        }catch (IOException e){
            e.printStackTrace();
        }
        return json_str;
    }

//    public static  String getEventListJson()  {
//        Log.i("Api","getEventListJson");
//        return getJson( UrlConst.genUrlListPagination("all",1,20));
//    }
    public static  String getEventListJson(String type, int page, int size)  {
        Log.i("Api","getEventListJson");
        return getJson( UrlConst.genUrlListPagination(type,page,size));
    }
    public static   String  getNewsDetailJson(String id)  {
        Log.i("Api","getNewsDetailJson");

        return getJson(  UrlConst.genUrlEventDetail(id));
    }

    public static   String  getAllEventsJson()  {
        Log.i("Api","getAllEventsJson");

        return getJson(  UrlConst.URL_EVENTS);
    }

    public static   String  queryEntityJson(String query)  {
        Log.i("Api","queryEntityJson");

        return getJson(  UrlConst.genUrlEntityQuery(query));
    }

    public static Bitmap getBitmapWithUrl(String url){
        final Request request = new Request.Builder().url(url).build();
        Bitmap bitmap = null;
        try (Response response = client.newCall(request).execute()) {
            InputStream inputStream = response.body().byteStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }

        return bitmap;
    }
    
    public static   String  getEpidemicDataJson(){
        return getJson(UrlConst.genUrlEpidemic());
    }
  }

