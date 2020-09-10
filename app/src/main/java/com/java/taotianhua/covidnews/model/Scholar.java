package com.java.taotianhua.covidnews.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

// TODO: use more fields
public class Scholar implements Serializable {
    String avatar_url;
    String id;
    String name;

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getName_zh() {
        return name_zh;
    }

    public String[] getTags() {
        return tags;
    }

    public int[] getTags_score() {
        return tags_score;
    }

    public boolean isIs_passedaway() {
        return is_passedaway;
    }

    public Profile getProfile() {
        return profile;
    }

    public Indices getIndices() {
        return indices;
    }

    String name_zh;

    public String getCompleteName() {
        if (!name_zh.isEmpty()) {
            return name_zh;
        } else {
            return name;
        }
    }

    String[] tags;
    int tags_score[];
    boolean is_passedaway;

    Profile profile;

    public static class Profile implements Serializable {
        String affiliation_zh;
        String affiliation;
        String bio;

        public String getAffiliation_zh() {
            return affiliation_zh;
        }

        public String getAffiliation() {
            return affiliation;
        }

        public String getBio() {
            return bio;
        }

        public String getEdu() {
            return edu;
        }

        public String getEmail() {
            return email;
        }

        public String getPosition() {
            return position;
        }

        String edu;
        String email;
        String position;

        public String getProperAffiliation() {
            if (affiliation_zh != null && !affiliation_zh.isEmpty()) {
                return affiliation_zh;
            } else {
                return affiliation;
            }
        }

        public static Profile fromJson(JSONObject jsonObject) {
            Profile profile = new Profile();
            profile.affiliation = getOrNull(jsonObject, "affiliation");
            profile.affiliation_zh = getOrNull(jsonObject, "affiliation_zh");
            profile.bio = getOrNull(jsonObject, "bio");
            profile.bio = profile.bio.replace("<br>", "\n");
            profile.edu = getOrNull(jsonObject, "edu");
            profile.email = getOrNull(jsonObject, "email");
            profile.position = getOrNull(jsonObject, "position");

            return profile;
        }
    }

    public static class Indices implements Serializable {
        double activity;
        int citations;
        double diversity;
        int gindex;
        int hindex;
        double newStar;
        int pubs;

        public double getRisingStar() {
            return risingStar;
        }

        double risingStar;

        public double getActivity() {
            return activity;
        }

        public int getCitations() {
            return citations;
        }

        public double getDiversity() {
            return diversity;
        }

        public int getGindex() {
            return gindex;
        }

        public int getHindex() {
            return hindex;
        }

        public double getNewStar() {
            return newStar;
        }

        public int getPubs() {
            return pubs;
        }

        public double getSociability() {
            return sociability;
        }

        double sociability;


        public static Indices fromJson(JSONObject jsonObject) {
            Indices indices = new Indices();
            try {
                indices.activity = jsonObject.getDouble("activity");
                indices.citations = jsonObject.getInt("citations");
                indices.diversity = jsonObject.getDouble("citations");
                indices.gindex = jsonObject.getInt("gindex");
                indices.hindex = jsonObject.getInt("hindex");
                indices.pubs = jsonObject.getInt("pubs");
                indices.newStar = jsonObject.getDouble("newStar");
                indices.risingStar = jsonObject.getDouble("risingStar");
                indices.sociability = jsonObject.getDouble("sociability");

            } catch (JSONException e) {
                indices = null;
            }

            return indices;
        }
    }

    Indices indices;

    public static Scholar fromJson(JSONObject jsonObject) {
        Scholar scholar = new Scholar();
        try {
            scholar.id = getOrNull(jsonObject, "id");
            scholar.name = jsonObject.getString("name");
            scholar.name_zh = jsonObject.getString("name_zh");
            scholar.avatar_url = jsonObject.getString("avatar");
            scholar.is_passedaway = jsonObject.getBoolean("is_passedaway");

            scholar.profile = Profile.fromJson(jsonObject.getJSONObject("profile"));
            scholar.indices = Indices.fromJson(jsonObject.getJSONObject("indices"));
        } catch (JSONException e) {
            scholar = null;
        }

        return scholar;
    }


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
}
