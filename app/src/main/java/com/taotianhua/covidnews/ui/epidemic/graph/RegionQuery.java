package com.taotianhua.covidnews.ui.epidemic.graph;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.taotianhua.covidnews.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 用来获得国家列表、某个国家对应的省份或是某个省份对应的城市。
 */
public class RegionQuery {
    private static String []countryList = {"Afghanistan","Albania","Algeria","American Samoa","Andorra","Angola","Antigua and Barb.","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bhutan","Bolivia","Bosnia and Herz.","Botswana","Brazil","Brunei","Bulgaria","Burkina Faso","Burundi","Cabo Verde","Cambodia","Cameroon","Canada","Cayman Is.","Central African Rep.","Chad","Chile","China","Colombia","Congo","Costa Rica","Croatia","Cuba","Cyprus","Czechia","Côte d'Ivoire","Dem. Rep. Congo","Denmark","Djibouti","Dominica","Dominican Rep.","Ecuador","Egypt","El Salvador","Eq. Guinea","Eritrea","Estonia","Ethiopia","Faeroe Is.","Fiji","Finland","Fr. Polynesia","France","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guam","Guatemala","Guernsey","Guinea","Guinea-Bissau","Guyana","Haiti","Honduras","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Isle of Man","Israel","Italy","Jamaica","Japan","Jersey","Jordan","Kazakhstan","Kenya","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Mauritania","Mauritius","Mexico","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Myanmar","N. Mariana Is.","Namibia","Nepal","Netherlands","New Zealand","Nicaragua","Niger","Nigeria","Norway","Oman","Pakistan","Palestine","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Puerto Rico","Qatar","Romania","Russia","Rwanda","S. Sudan","Saint Barthelemy","Saint Lucia","San Marino","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Sint Maarten","Slovakia","Slovenia","Somalia","South Africa","South Korea","Spain","Sri Lanka","St-Martin","St. Kitts and Nevis","St. Vin. and Gren.","Sudan","Suriname","Sweden","Switzerland","Syria","São Tomé and Principe","Tajikistan","Tanzania","Thailand","Timor-Leste","Togo","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","U.S. Virgin Is.","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States of America","Uruguay","Uzbekistan","Vatican","Venezuela","Vietnam","World","Yemen","Zambia","Zimbabwe","eSwatini"};

    /**
     * 得到json文件中的内容
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

    /**
     * 返回所有国家的字符串列表
     * @return
     */
    public static String [] getCountryList(){return countryList;}

    /**
     * 返回某个country对应的province列表。
     * @param country
     * @return
     */
    public static String [] getProvinceList(Context context, String country){
        JSONObject countryJsonObj;
        try {
            countryJsonObj = new JSONObject(getJson(context, context.getString(R.string.Country_Province)));
            Log.i("RegionQuery", countryJsonObj.toString());
        }
        catch (JSONException e){
            Log.i("RegionQuery", "不应该有此构建错误");
            return null; //导致崩溃
        }
        try {
            if(!countryJsonObj.has(country)){
                String []ret = {""};
                return ret;
            }
            JSONArray provinceJsonArr = countryJsonObj.getJSONArray(country);
            String []ret = new String[provinceJsonArr.length()+1];
            ret[0]="";
            for(int i=0; i< provinceJsonArr.length(); ++i) ret[i+1] = provinceJsonArr.get(i).toString();
            return ret;
        }
        catch (JSONException e){
            Log.i("RegionQuery", "In getProvinceList 没有查询到对应省份，返回空列表 country:" + country);
            String []ret = {};
            return ret;
        }
    }

    /**
     * 返回province对应的所有county
     * @param province
     * @return
     */
    public static String [] getCountyList(Context context, String province){
        JSONObject provinceJsonObj;
        try {
            provinceJsonObj = new JSONObject(getJson(context, context.getString(R.string.Province_County)));
        }
        catch (JSONException e){
            Log.i("RegionQuery", "不应该有此构建错误");
            return null; //导致崩溃
        }
        try {
            if(!provinceJsonObj.has(province)){
                String []ret = {""};
                return ret;
            }
            JSONArray countyJsonArr = provinceJsonObj.getJSONArray(province);
            String []ret = new String[countyJsonArr.length()+1];
            ret[0] = "";
            for(int i=0; i < countyJsonArr.length(); ++i) ret[i+1] = countyJsonArr.getString(i);
            return ret;
        }
        catch (JSONException e){
            Log.i("RegionQuery", "In getCountyList 没有查询到对应省份，返回空列表 province:" + province);
            String []ret = {};
            return ret;
        }

    }
}
