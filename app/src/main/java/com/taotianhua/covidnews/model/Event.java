package com.taotianhua.covidnews.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Event  {
    private String id;

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    private String time;
    private String content;
    private String source;
    private String title;
    private String type;
    private ArrayList<String> urls = new ArrayList<>();

    public static Event fromJson(JSONObject jsonObject)  {
        Event event = new Event();
        try{
            event.id=  jsonObject.getString("_id");
            event.time=  jsonObject.getString("time");
            event.content= jsonObject.getString("content");
            event.title=  jsonObject.getString("title");
            event.type= jsonObject.getString("type");

            JSONArray urlArray = jsonObject.getJSONArray("urls");
            for (int i = 0; i < urlArray.length(); i++) {
                event.urls.add(urlArray.getString(i));
            }
        }catch (JSONException e){
            event = null;
        }

        return event;
    }

}
