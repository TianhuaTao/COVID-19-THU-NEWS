package com.java.taotianhua.covidnews.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Api {
    private static final OkHttpClient client = new OkHttpClient();

    private static String getJson(String url) {
        final Request request = new Request.Builder().url(url).build();
        String json_str = null;
        try (Response response = client.newCall(request).execute()) {
            if(response.body()==null){
                json_str = "";
            }else {
                json_str = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json_str;
    }


    public static String getEventListJson(String type, int page, int size) {
        Log.i("Api", "getEventListJson");
        return getJson(UrlConst.genUrlListPagination(type, page, size));
    }

    public static String getNewsDetailJson(String id) {
        Log.i("Api", "getNewsDetailJson");

        return getJson(UrlConst.genUrlEventDetail(id));
    }

    public static String getAllEventsJson() {
        Log.i("Api", "getAllEventsJson");

        return getJson(UrlConst.URL_EVENTS);
    }

    public static String queryEntityJson(String query) {
        Log.i("Api", "queryEntityJson");

        return getJson(UrlConst.genUrlEntityQuery(query));
    }

    public static String getAllScholarsJson() {
        Log.i("Api", "getAllScholarsJson");

        return getJson(UrlConst.URL_SCHOLARS);
    }

    public static Bitmap getBitmapWithUrl(String url) {
        final Request request = new Request.Builder().url(url).build();
        Bitmap bitmap = null;
        try (Response response = client.newCall(request).execute()) {
            InputStream inputStream = response.body().byteStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException | NullPointerException e  ) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static String getEpidemicDataJson() {
        return getJson(UrlConst.genUrlEpidemic());
    }


}

