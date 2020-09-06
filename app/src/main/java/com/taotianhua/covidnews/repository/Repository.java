package com.taotianhua.covidnews.repository;

import android.util.Log;

import com.taotianhua.covidnews.model.Event;
import com.taotianhua.covidnews.model.EventBrief;
import com.taotianhua.covidnews.network.Api;
import com.taotianhua.covidnews.network.UrlConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Repository {
    public Repository(){
        configCatalog();
        catalogMap = new LinkedHashMap<>();

        // initialize each catalog with an empty list
        for (String c:catalog) {
            catalogMap.put(c,new ArrayList<>());
        }
    }

    private static  Repository instance;
    public static Repository getInstance(){
        if (instance==null)
            instance = new Repository();
        return instance;
    }

    public List<String> getCatalog() {
        return catalog;
    }

    private List<String> catalog = new ArrayList<>();

    private void configCatalog(){
        catalog.add("News");
        catalog.add("Paper");
        catalog.add("Event");
        catalog.add("Points");
    }

    public    String  getNewsDetailJson(String id)  {
        Log.i("Repository","getNewsDetailJson");
        if(LocalStorage.exist(id)){
            String content = LocalStorage.load(id);
            if(content==null|| content.length()==0){
                content =  Api.getNewsDetailJson(id);
                LocalStorage.store(id, content);
            }
            return content;

        }else {
            String content = Api.getNewsDetailJson(id);
            LocalStorage.store(id, content);
            return content;
        }
    }

//    public   String getEventListJson()  {
//        Log.i("Repository","getEventListJson");
//        return Api.getEventListJson();
//    }


    private Map<String, List<EventBrief>> catalogMap;

    private int fetchMoreEventBriefsFromRemote(String catalog, int count){
        List<EventBrief> fetched = catalogMap.get(catalog);
        if(fetched == null){
            Log.e("Repository","Catalog "+catalog+" does not exist");
            return 0;
        }
        int newCount = 0;
        while (count>0){
            final int sizePerPage = 30;
            int start = fetched.size();
            int page = start/sizePerPage+1;

            String listJson = Api.getEventListJson(catalog.toLowerCase(),page,sizePerPage);
            try {
                JSONObject jObject = new JSONObject(listJson);
                JSONArray data= jObject.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    EventBrief brief = EventBrief.fromJson(data.getJSONObject(i));
                    fetched.add(brief);
                    count--;
                    newCount++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(fetched.size()==start){
                break;  // no new events
            }
        }
        return  newCount;
    }


    public List<EventBrief> refresh(String catalog, int number){
        List<EventBrief> fetched = catalogMap.get(catalog);
        if(fetched == null){
            Log.e("Repository","Catalog "+catalog+" does not exist");
            return null;
        }
        fetched.clear();
        return loadMoreEventBriefs(catalog,0, number);

    }

    // 从第 start 条 EventBrief 之后再读取 number 条 catalog 类型的 EventBrief
    public List<EventBrief> loadMoreEventBriefs(String catalog,int start, int number){
        List<EventBrief> fetched = catalogMap.get(catalog);
        if(fetched == null){
            Log.e("Repository","Catalog "+catalog+" does not exist");
            return null;
        }

        List<EventBrief> retList =new ArrayList<> ();

        while (number>0){
            if(start+number>fetched.size()){
                fetchMoreEventBriefsFromRemote(catalog, 100);
            }
            if(start< fetched.size()){
                retList.addAll (fetched.subList(start, Math.min(start+number, fetched.size()) ));
                start = start+retList.size();
                number = number-retList.size();
            }

        }
return  retList;

    }


    private  List<EventBrief> allEventBriefs;
    private LocalDateTime lastUpdated = null;

    private boolean expired(){
        if(lastUpdated==null) return true;

        LocalDateTime  now = LocalDateTime .now();

        Duration duration = Duration.between(lastUpdated, now);
        long diff = Math.abs(duration.toMinutes());

        return diff > 5;
    }

//    public  void refreshAllEvents(){
//        String listJson = Api.getAllEventsJson();
//        List<EventBrief> briefs = new ArrayList<>();
//        try {
//            JSONObject jObject = new JSONObject(listJson);
//            JSONArray data= jObject.getJSONArray("datas");
//
//            for (int i = 0; i < data.length(); i++) {
//                EventBrief brief = EventBrief.fromJson(data.getJSONObject(i));
//                briefs.add(brief);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        allEventBriefs = briefs;
//        lastUpdated = LocalDateTime .now();
//        System.out.println(getAllCatalogs());
//    }



//    public  List<EventBrief>  getAllEvents(){
//        if(allEventBriefs==null || expired()){
//            refreshAllEvents();
//        }
//        return allEventBriefs.stream().limit(200).collect(Collectors.toList());
//    }
//
//
//    public  List<String>  getAllCatalogs(){
//        if(allEventBriefs==null || expired()){
//            return null;
//        }
//        return allEventBriefs.stream()
//                .map(EventBrief::getType)
//                .distinct()
//                .collect(Collectors.toList());
//    }
}
