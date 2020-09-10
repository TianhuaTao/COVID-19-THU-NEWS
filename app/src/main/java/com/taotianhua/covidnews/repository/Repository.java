package com.taotianhua.covidnews.repository;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import com.taotianhua.covidnews.model.Entity;
import com.taotianhua.covidnews.model.EpidemicData;
import com.taotianhua.covidnews.model.Event;
import com.taotianhua.covidnews.model.EventBrief;
import com.taotianhua.covidnews.model.Scholar;
import com.taotianhua.covidnews.network.Api;
import com.taotianhua.covidnews.network.UrlConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Repository 负责提供 Event 或者 EventBrief
 * Repository 提供的数据，可能是本地缓存的，也可能是从服务器获取的
 * 但是提供一个统一的结构，使用 Repository 的用户不需要关心具体的数据从哪里来
 * Repository 提供的接口都是耗时的（阻塞），因此应该使用新的线程来调用
 * Repository 的接口不应该提供 JSON 和 字符串 形式的数据，解析的工作应该内部完成
 * <p>
 * 应该假设 Repository 的用户只需要知道 Event 和 EventBrief
 */
public class Repository {

    // TODO: 删除所有返回 JSON 和 字符串 数据的接口

    public Repository() {
        configCatalog();
        catalogMap = new LinkedHashMap<>();

        // initialize each catalog with an empty list
        for (String c : catalog) {
            catalogMap.put(c, new ArrayList<>());
        }
    }

    /*
     * 单例
     */
    private static Repository instance;

    public static Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }


    /* 定义了所有的分类 */
    public List<String> getCatalog() {
        return catalog;
    }

    private List<String> catalog = new ArrayList<>();

    private void configCatalog() {
        catalog.add("News");
        catalog.add("Paper");
        catalog.add("Event");
        catalog.add("Points");
    }

    /**
     * 每个分类都用一个 List 保存当前分类下的新闻（对应一个 tab 中的列表内容）
     * 理论上 List 中的内容不是持久的，仅仅是一个 runtime 的内容
     * 但是 storeLocalCache() 可以把某个分类的 list 保存下来，这样下次
     * 启动应用的时候，即使没有网络，也可以获得上次程序运行的时候列表
     * （参考没有网的时候打开微博）
     */
    private Map<String, List<EventBrief>> catalogMap;


    /**
     * 根据 id 获取 Event
     *
     * @param id
     * @return 出错时为 null
     */
    public Event getNewsDetail(String id) {
        Log.i("Repository", "getNewsDetail");
        Event event = null;
        String listJson;
        if (LocalStorage.exist("json", id)) {
            listJson = LocalStorage.load("json", id);
            if (listJson == null || listJson.length() == 0) {
                listJson = Api.getNewsDetailJson(id);
                LocalStorage.store("json", id, listJson);
            }

        } else {
            listJson = Api.getNewsDetailJson(id);
            if (listJson != null)
                LocalStorage.store("json", id, listJson);
        }

        if (listJson == null) return null;

        try {
            JSONObject jObject = new JSONObject(listJson);
            JSONObject data = jObject.getJSONObject("data");
            event = Event.fromJson(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return event;
    }

    /**
     * 根据 id 获取 EventBrief
     *
     * @param id
     * @return 出错时为 null
     */
    public EventBrief getEventBriefById(String id) {
        Log.i("Repository", "getEventBriefById");
        EventBrief eventBrief = null;
        String listJson;
        if (LocalStorage.exist("json", id)) {
            listJson = LocalStorage.load("json", id);
            if (listJson == null || listJson.length() == 0) {
                listJson = Api.getNewsDetailJson(id);
                LocalStorage.store("json", id, listJson);
            }

        } else {
            listJson = Api.getNewsDetailJson(id);
            if (listJson != null)
                LocalStorage.store("json", id, listJson);
        }

        if (listJson == null) return null;

        try {
            JSONObject jObject = new JSONObject(listJson);
            JSONObject data = jObject.getJSONObject("data");
            eventBrief = EventBrief.fromJson(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eventBrief;
    }

    /**
     * @param catalog
     * @param count
     * @return
     */
    private int fetchMoreEventBriefsFromRemote(String catalog, int count) {
        // TODO: FIX FATAL BUG -- 由于服务器的内容会更新，基于分页计算的新闻index会错误，导致重复取到相同的新闻
        List<EventBrief> fetched = catalogMap.get(catalog);
        if (fetched == null) {
            Log.e("Repository", "Catalog " + catalog + " does not exist");
            return 0;
        }
        int newCount = 0;
        while (count > 0) {
            // TODO: FIX FATAL BUG -- 这里的位置计算好像不对，导致一定会重复取到相同的新闻（取的前面几条）
            final int sizePerPage = 30;
            int start = fetched.size();
            int page = start / sizePerPage + 1;

            String listJson = Api.getEventListJson(catalog.toLowerCase(), page, sizePerPage);
            if (listJson == null) {
                return 0;
            }
            try {
                JSONObject jObject = new JSONObject(listJson);
                JSONArray data = jObject.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    EventBrief brief = EventBrief.fromJson(data.getJSONObject(i));
                    fetched.add(brief);
                    count--;
                    newCount++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (fetched.size() == start) {
                break;  // no new events
            }
        }
        storeLocalCache(catalog, fetched);
        return newCount;
    }

    /**
     * 把 catalog 分类下的新闻刷新，可以获取新的服务器内容
     * （类似微博的拉到顶部更新）
     * 会把原本的 catalog 下的 list 中内容清空掉
     *
     * @param catalog
     * @param number  建议的获取的新event数量，实际忽略
     * @return 新 event 列表
     */
    public List<EventBrief> refresh(String catalog, int number) {
        // TODO 改用新的带有 tflag 的服务器 API，只获取最新的新闻，并且不需要clear已有的新闻列表
        List<EventBrief> fetched = catalogMap.get(catalog);
        if (fetched == null) {
            Log.e("Repository", "Catalog " + catalog + " does not exist");
            return null;
        }
        fetched.clear();
        return loadMoreEventBriefs(catalog, 0, number);

    }

    /**
     * 从第 start 条 EventBrief 之后再读取 number 条 catalog 类型的 EventBrief
     * <p>
     * 注意到页面列表上显示的 event 数量总是小于 Repo 中存有的 event 数量，当页面上显示到了
     * 最后一条（第n条），就会从第 n+1 条开始请求 number 条内容
     * Repo 中缓存的内容或许可以拿出 number 条内容，也或许不能，如果不够就要从服务器获取
     *
     * @param catalog
     * @param start   若页面上有n条，则start等于 n+1
     * @param number
     * @return
     */
    public List<EventBrief> loadMoreEventBriefs(String catalog, int start, int number) {
        List<EventBrief> fetched = catalogMap.get(catalog);
        if (fetched == null) {
            Log.e("Repository", "Catalog " + catalog + " does not exist");
            return null;
        }

        List<EventBrief> retList = new ArrayList<>();

        while (number > 0) {
            if (start + number > fetched.size()) {
                int newFetch = fetchMoreEventBriefsFromRemote(catalog, 100);    // 从服务器取到本地缓存
                if (newFetch == 0) {
                    Log.e("Repository", "cannot fetch new event");
                    break;
                }
            }
            if (start < fetched.size()) {
                retList.addAll(fetched.subList(start, Math.min(start + number, fetched.size())));
                start = start + retList.size();
                number = number - retList.size();
            }

        }
        return retList;

    }


    public List<EventBrief> loadLocalCache(String catalog, int num) {
        return LocalStorage.loadLocalCache(catalog, num);

    }


    public void storeLocalCache(String catalog, List<EventBrief> list) {
        LocalStorage.storeLocalCache(catalog, list);
    }


    public List<Entity> queryEntity(String query) {
        List<Entity> entityList = new ArrayList<>();
        String json = Api.queryEntityJson(query);
//        System.out.println(json);

        if (json == null) {
            return entityList;
        }
        try {
            JSONObject jObject = new JSONObject(json);
            JSONArray data = jObject.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                Entity entity = Entity.fromJson(data.getJSONObject(i));
//                // Set image
//                if(entity!=null){
//                    String imageUrl = entity.getImg_url();
//                    entity.setImage(this.getBitmapWithUrl(imageUrl));
//                }
                entityList.add(entity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return entityList;
    }

    Map<String, Bitmap> bitmapCache = new HashMap<>();

    public Bitmap getBitmapWithUrl(String url) {
        if (url == null ||
                url.equals("null")  /* I hate the json hack */
        ) return null;
        Bitmap bm;
        if (bitmapCache.containsKey(url)) {
            bm = bitmapCache.get(url);
        } else {
            bm = Api.getBitmapWithUrl(url);
            bitmapCache.put(url, bm);
        }
        return bm;
    }
    /* ******************************Epidemic Data interface *********************************** */

    /**
     * 根据region读取json中对应地区的数据并构建Epidemic Data。需要联网。
     *
     * @param region
     * @return
     */

    String epidemicDataJsonStr;

    public EpidemicData getEpidemicData(String region) {
        Log.i("Repository", "getEpidemicData");

        if (epidemicDataJsonStr == null || epidemicDataJsonStr.isEmpty())
            epidemicDataJsonStr = Api.getEpidemicDataJson();
        if (epidemicDataJsonStr == null) return null;

        EpidemicData epidemicData = null;
        try {
            JSONObject regionData = new JSONObject(epidemicDataJsonStr).getJSONObject(region);
            epidemicData = EpidemicData.fromJson(regionData);
            epidemicData.setRegion(region);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return epidemicData;
    }

    public List<EventBrief> loadNewsSearchResult(String query, int limit) {
        ArrayList<EventBrief> list = new ArrayList<>();
        List<EventBrief> results = new ArrayList<>();

        // TODO: 使用缓存，可设置缓存3分钟过期
        String allEventsJson = Api.getAllEventsJson();

        if (allEventsJson == null) {
            return results;
        }
        try {
            JSONObject jObject = new JSONObject(allEventsJson);
            JSONArray data = jObject.getJSONArray("datas");

            for (int i = data.length() - 1; i >= 0; i--) {
                EventBrief brief = EventBrief.fromJson(data.getJSONObject(i));
                list.add(brief);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("Repository", "Got all eventbriefs");
        results = list.parallelStream()
                .filter(eventBrief -> eventBrief.getTitle().toLowerCase().contains(query.toLowerCase()))
                .limit(limit)
                .collect(Collectors.toList());
        Log.i("Repository", "filter done");
        return results;
    }


    ArrayList<Scholar> scholarsList;

    public List<Scholar> loadAllScholars() {
        Log.i("Repository", "loadAllScholars");
        if (scholarsList != null && scholarsList.size() > 5)     // initialized and succeeded
            return scholarsList;
        scholarsList = new ArrayList<>();

        String scholarsJson = Api.getAllScholarsJson();

        if (scholarsJson == null) {
            return scholarsList;
        }
        try {
            JSONObject jObject = new JSONObject(scholarsJson);
            JSONArray data = jObject.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                Scholar scholar = Scholar.fromJson(data.getJSONObject(i));
                scholarsList.add(scholar);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("Repository", "loadAllScholars finished");
        return scholarsList;
    }
    /* ***************************************************************************************** */

    public static List<EventBrief> getCertainTypeEventBreif(Context context, String type){
        List<EventBrief> list = new ArrayList<>();
        try {
            JSONObject jObject = new JSONObject(getJson(context, "label.json"));
            JSONArray allEventId = jObject.getJSONArray(type);

            for(int i = 0; i < allEventId.length(); ++i){
                String id = allEventId.getString(i);
                list.add(Repository.getInstance().getEventBriefById(id));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 从assets中获得Json
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager =context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    /* ******************************* Old interface ******************************************* */

//    public String getNewsDetailJson(String id) {
//        Log.i("Repository", "getNewsDetailJson");
//        if (LocalStorage.exist("json", id)) {
//            String content = LocalStorage.load("json", id);
//            if (content == null || content.length() == 0) {
//                content = Api.getNewsDetailJson(id);
//                LocalStorage.store("json", id, content);
//            }
//            return content;
//
//        } else {
//            String content = Api.getNewsDetailJson(id);
//            if (content != null)
//                LocalStorage.store("json", id, content);
//            return content;
//        }
//    }

//    private List<EventBrief> allEventBriefs;
//    private LocalDateTime lastUpdated = null;

//    private boolean expired() {
//        if (lastUpdated == null) return true;
//
//        LocalDateTime now = LocalDateTime.now();
//
//        Duration duration = Duration.between(lastUpdated, now);
//        long diff = Math.abs(duration.toMinutes());
//
//        return diff > 5;
//    }

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
