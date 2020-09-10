package com.java.taotianhua.covidnews.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 完整的 Event，包括各种信息，可以直接从 JSON 构造
 * 用于 News 的详细页面
 */
public class Event implements Serializable {
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

    public ArrayList<String> getLabels() {
        return labels;
    }

    private String time;
    private String content;
    private String source;
    private String title;
    private String type;
    private ArrayList<String> urls = new ArrayList<>();
    private ArrayList<String> labels = new ArrayList<>();

    public static Event fromJson(JSONObject jsonObject) {
        Event event = new Event();
        try {
            event.id = jsonObject.getString("_id");
            event.time = jsonObject.getString("time");
            event.content = jsonObject.getString("content");
            event.title = jsonObject.getString("title");
            event.type = jsonObject.getString("type");
            event.source = getOrNull(jsonObject, "source");
            JSONArray urlArray = jsonObject.getJSONArray("urls");
            for (int i = 0; i < urlArray.length(); i++) {
                event.urls.add(urlArray.getString(i));
            }
            JSONArray labelsArray = jsonObject.getJSONArray("entities");
            for (int i = 0; i < labelsArray.length(); ++i) {
                event.labels.add(labelsArray.getJSONObject(i).getString("label"));
            }
//            if (event.type.equals("event")) {
//                Log.i("MyApp", "contain =" + jsonObject.has("related_events"));
//            }
        } catch (JSONException e) {
            event = null;
        }

        return event;
    }


    public EventBrief getBrief(){
        EventBrief brief = new EventBrief();
        brief.setContent(this.content);
        brief.setId(this.id);
        brief.setTime(this.time);
        brief.setType(this.type);
        brief.setTitle(this.title);
        return brief;
    }
    /**
     * Helper function to parse JSON
     *
     * @param jsonObject
     * @param name
     * @param <T>
     * @return
     */
    private static <T> T getOrNull(JSONObject jsonObject, String name) {
        T t = null;
        if (jsonObject.has(name)) {
            try {
                t = (T) jsonObject.get(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return t;

    }

    public String getWeiboText() {
        return "[" + getTitle() + "]" +
                content +
                "(更多内容来自 COVID-News)";
    }
}
