package com.taotianhua.covidnews.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventBrief {
    private String id;
    private String time;
    private String content;
    private String source;
    private String title;
    private String type;
    private List<String> urls = new ArrayList<>();

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

    public List<String> getUrls() {
        return urls;
    }



    public static EventBrief fromJson(JSONObject jsonObject)  {
        EventBrief eventBrief = new EventBrief();
        try{
            eventBrief.id=  jsonObject.getString("_id");
            eventBrief.time=  jsonObject.getString("time");
            eventBrief.content= jsonObject.getString("content");
            eventBrief.title=  jsonObject.getString("title");
            eventBrief.type= jsonObject.getString("type");

            JSONArray urlArray = jsonObject.getJSONArray("urls");
            for (int i = 0; i < urlArray.length(); i++) {
                eventBrief.urls.add(urlArray.getString(i));
            }
        }catch (JSONException e){
            eventBrief = null;
        }

        return eventBrief;
    }

    @Override
    public String toString() {
        return "EventBrief{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", source='" + source + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", urls=" + urls +
                '}';
    }
}
