package com.taotianhua.covidnews.model;

import com.taotianhua.covidnews.repository.HistoryManager;
import com.taotianhua.covidnews.repository.LocalStorage;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * EventBrief 是一个新闻的摘要，包括一个新闻的最简要的信息，主要用于在列表中显示
 * 通过获取 id，可以构建更完整的 Event 对象
 */
public class EventBrief implements Serializable {


    private String id;      /* 对应json中的_id，惟一标识*/
    private String time;
    private String content;
    private String title;
    private String type;

    private Boolean alreadyRead;    /* 通过构造的时候查询历史记录计算*/

    public Boolean getAlreadyRead() {
        return alreadyRead;
    }

    public void setAlreadyRead(Boolean alreadyRead) {
        this.alreadyRead = alreadyRead;
        HistoryManager.getInstance().addHistory(this.id);
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }


    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }




    public static EventBrief fromJson(JSONObject jsonObject) {
        EventBrief eventBrief = new EventBrief();
        try {
            eventBrief.id = jsonObject.getString("_id");
            eventBrief.time = jsonObject.getString("time");
            eventBrief.content = jsonObject.getString("content");
            eventBrief.title = jsonObject.getString("title");
            eventBrief.type = jsonObject.getString("type");
            eventBrief.alreadyRead = HistoryManager.getInstance().inHistory(eventBrief.id);
        } catch (JSONException e) {
            eventBrief = null;
        }
        return eventBrief;
    }

    @NotNull
    @Override
    public String toString() {
        return "EventBrief{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
