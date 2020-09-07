package com.taotianhua.covidnews.model;

import java.io.Serializable;
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
}

