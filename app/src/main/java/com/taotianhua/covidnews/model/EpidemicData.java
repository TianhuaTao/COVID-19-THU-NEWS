package com.taotianhua.covidnews.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EpidemicData implements Serializable{


    /**
     * region : China
     * begin : YYYY-mm-dd
     * confirmed : [1,2]
     * cured : [1,2]
     * dead : [1,2]
     */

    private String region;
    private String begin;
    private List<Integer> confirmed;
    private List<Integer> cured;
    private List<Integer> dead;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public List<Integer> getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(List<Integer> confirmed) {
        this.confirmed = confirmed;
    }

    public List<Integer> getCured() {
        return cured;
    }

    public void setCured(List<Integer> cured) {
        this.cured = cured;
    }

    public List<Integer> getDead() {
        return dead;
    }

    public void setDead(List<Integer> dead) {
        this.dead = dead;
    }

    public static EpidemicData fromJson(JSONObject regionData){
        EpidemicData epidemicData = new EpidemicData();
        try {
            epidemicData.setBegin(regionData.getString("begin"));
            JSONArray dataEntry = regionData.getJSONArray("data");
            List<Integer> confirmedList = new ArrayList<>();
            List<Integer> curedList = new ArrayList<>();
            List<Integer> deadList = new ArrayList<>();
            for (int i = 0; i < dataEntry.length(); ++i) {
                JSONArray entry = dataEntry.getJSONArray(i);
                confirmedList.add(entry.getInt(0));
                curedList.add(entry.getInt(2));
                deadList.add(entry.getInt(3));
            }
            epidemicData.setConfirmed(confirmedList);
            epidemicData.setCured(curedList);
            epidemicData.setDead(deadList);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return epidemicData;
    }
}

