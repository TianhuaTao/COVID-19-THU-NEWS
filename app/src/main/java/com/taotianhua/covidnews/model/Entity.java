package com.taotianhua.covidnews.model;

import android.graphics.Bitmap;
import android.os.Parcelable;

import com.taotianhua.covidnews.repository.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Entity implements Serializable {

    public static class AbstractInfo implements Serializable{
        private String enwiki;
        private String baidu;
        private String zhwiki;

        public COVID getCovid() {
            return covid;
        }

        COVID covid;
        public static AbstractInfo fromJson(JSONObject jsonObject){
            AbstractInfo abstractInfo = new AbstractInfo();
            try{
                abstractInfo.enwiki = jsonObject.getString("enwiki");
                abstractInfo.baidu=  jsonObject.getString("baidu");
                abstractInfo.zhwiki=  jsonObject.getString("zhwiki");
                abstractInfo.covid = COVID.fromJson(jsonObject.getJSONObject("COVID"));
            }catch (JSONException e){
                abstractInfo = null;
            }

            return abstractInfo;
        }

        public String getDescription(){
            if(!zhwiki.isEmpty())return zhwiki;
            if(!enwiki.isEmpty())return enwiki;
            if(!baidu.isEmpty())return baidu;
            return  "";
        }
    }

    public static class COVID  implements Serializable{
        public static class  Entity_Relation  implements Serializable{
            public String getType() {
                return type;
            }

            public String getUrl() {
                return url;
            }

            public String getLabel() {
                return label;
            }

            public Boolean getForward() {
                return forward;
            }

            String type;
            String url;
            String label;
            Boolean forward;
            public static Entity_Relation fromJson(JSONObject jsonObject){
                Entity_Relation entity_relation = new Entity_Relation();
                try{
                    entity_relation.type = jsonObject.getString("relation");
                    entity_relation.url=  jsonObject.getString("url");
                    entity_relation.label=  jsonObject.getString("label");
                    entity_relation.forward = jsonObject.getBoolean("forward");
                }catch (JSONException e){
                    entity_relation = null;
                }

                return entity_relation;

            }
        }

        Map<String,String > properties;
        List<Entity_Relation> relations;

        public static COVID fromJson(JSONObject jsonObject){
            COVID covid = new COVID();
            try{
                // properties
                covid.properties = new LinkedHashMap<>();
                for (Iterator<String> it = jsonObject.getJSONObject("properties").keys(); it.hasNext(); ) {
                    String key = it.next();
                    covid.properties.put(key, jsonObject.getString(key));
                }

                // relations
                covid.relations = new ArrayList<>();
                JSONArray relationsArray =  jsonObject.getJSONArray("relations");
                for (int i = 0; i < relationsArray.length(); i++) {
                    Entity_Relation r = Entity_Relation.fromJson( relationsArray.getJSONObject(i)) ;
                    covid.relations.add(r);
                }

            }catch (JSONException e){
                covid = null;
            }

            return covid;


        }


    }

    public double getHot() {
        return hot;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    public String getImg_url() {
        return img_url;
    }


//    private Bitmap image;     /* make it serializable */

//    /* set by Repository */
//    public void setImage(Bitmap value){
//        image = value;
//    }

    /* Slow */
    public Bitmap fetchImage(){
        return Repository.getInstance().getBitmapWithUrl(this.img_url);
    }

    public AbstractInfo getAbstractInfo() {
        return abstractInfo;
    }

    private double hot;
    private String label;
    private String url;
    private String img_url;
    private AbstractInfo abstractInfo;


    public static Entity fromJson(JSONObject jsonObject)  {
        Entity entity = new Entity();
        try{
            entity.hot = jsonObject.getDouble("hot");
            entity.label=  jsonObject.getString("label");
            entity.url=  jsonObject.getString("url");
            entity.img_url=  jsonObject.getString("img");
            entity.abstractInfo=  AbstractInfo.fromJson(jsonObject.getJSONObject("abstractInfo"));

        }catch (JSONException e){
            entity = null;
        }

        return entity;
    }

    private static <T> T getOrNull(JSONObject jsonObject,String name){
        T t = null;
        if(jsonObject.has(name)){
            try {
                t = (T) jsonObject.get(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return t;

    }
}
