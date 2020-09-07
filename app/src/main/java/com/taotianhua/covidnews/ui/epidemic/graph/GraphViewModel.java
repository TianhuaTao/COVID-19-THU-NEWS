package com.taotianhua.covidnews.ui.epidemic.graph;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taotianhua.covidnews.model.EpidemicData;
import com.taotianhua.covidnews.model.EventBrief;
import com.taotianhua.covidnews.network.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GraphViewModel extends ViewModel {

    /**
     * return a certain region's data
     */

    private MutableLiveData<EpidemicData> graphData;

    public GraphViewModel() {
    }

    public LiveData<EpidemicData> getEpidemicData(String country, String province, String county){
        if(graphData == null){
            graphData = new MutableLiveData<>();
        }
        String region = country+"|"+province+"|"+county;
        loadRegionDataAsync(region);
        return graphData;
    }

    public LiveData<EpidemicData> getEpidemicData(String country, String province){
        if(graphData == null){
            graphData = new MutableLiveData<>();
        }
        String region = country+"|"+province;
        loadRegionDataAsync(region);
        return graphData;
    }

    public LiveData<EpidemicData> getEpidemicData(String country){
        if(graphData == null){
            graphData = new MutableLiveData<>();
        }
        String region = country;
        loadRegionDataAsync(region);
        return graphData;
    }

    private void loadRegionDataAsync(String region){
        //TODO: return a EpidemicData be region key.

        //FIXME:this is for test
        EpidemicData d = new EpidemicData();
        d.setRegion("China");
        d.setBegin("2020-01-22");
        Integer []c = {628,902,1377,2076,2857,4630,6098,8149,9812,11901,14490,17341,20530,24435,28138,31264,34674,37289,40262,42747,44766,59907,63950,66581,68595,70645,72533,74284,74680,75571,76396,77048,77269,77785,78195,78631,78962,79394,79972,80175,80303,80424,80581,80734,80815,80868,80905,80932,80969,80981,80995,81029,81062,81099,81135,81203,81265,81308,81386,81457,81566,81691,81850,81962,82079,82218,82342,82451,82547,82619,82725,82804,82875,82930,83015,83071,83162,83249,83305,83386,83482,83597,83696,83745,83797,84149,84156,84201,84237,84271,84289,84302,84311,84327,84338,84341,84367,84369,84373,84385,84388,84393,84400,84404,84406,84409,84416,84416,84431,84450,84451,84458,84464,84469,84478,84484,84494,84503,84505,84507,84520,84522,84525,84536,84543,84544,84547,84547,84565,84570,84589,84597,84604,84603,84614,84620,84629,84629,84635,84641,84652,84659,84671,84731,84778,84823,84867,84903,84940,84970,84999,85018,85041,85099,85119,85148,85172,85190,85204,85227,85232,85263,85278,85287,85306,85320,85345,85366,85399,85445,85487,85522,85568,85623,85677,85697,85775,85857,85937,86068,86152,86226,86361,86500,86660,86839,87028,87245,87457,87680,87956,88122,88301,88459,88574,88682,88804,88937,89057,89149,89270,89383,89444,89526,89625,89695,89761,89859,89926,89980,90013,90053,90103,90141,90182,90205,90239,90271,90301,90323,90351,90370,90402,90422,90442,90475,90498,90517,};
        List<Integer> confirmed = new ArrayList<Integer>(228);
        Collections.addAll(confirmed,c);
        Integer []cur = {30,36,41,52,61,75,119,141,226,285,450,538,726,1030,1379,1759,2387,2906,3553,4303,5072,6224,7323,8498,9763,11287,13005,14940,16731,18688,21075,23191,25009,27663,30078,32916,36315,39321,42165,44847,47441,49998,52305,53956,55547,57400,58813,60186,61656,62912,64216,65678,67038,67930,68820,69781,70564,71299,71299,71885,72390,72848,73791,74196,74737,75122,75600,75942,76225,76239,76465,76786,76984,77210,77348,77467,77586,77711,77838,77938,78020,78145,78262,78389,78508,78600,77720,77835,77895,77978,78042,78337,78236,78362,78450,78558,78664,78729,78785,78845,78905,78939,79016,79127,79204,79268,79324,79401,79488,79533,79566,79596,79621,79644,79668,79682,79701,79708,79715,79724,79736,79740,79746,79757,79767,79774,79786,79791,79802,79806,79812,79824,79826,79831,79842,79848,79854,79854,79865,79883,79888,79896,79903,79906,79913,79922,79926,79944,79949,79963,79969,79970,79983,79991,79999,80007,80015,80026,80044,80054,80068,80087,80102,80117,80144,80157,80168,80192,80240,80268,80293,80314,80345,80376,80407,80445,80476,80508,80535,80579,80605,80650,80685,80738,80782,80850,80899,80906,80957,81034,81122,81227,81348,81459,81554,81676,81860,82033,82230,82411,82566,82688,82883,83083,83254,83407,83559,83650,83737,83858,84031,84122,84254,84381,84446,84516,84626,84715,84799,84883,84948,84985,85058,85122,85169,85211,85257,85314,};
        List<Integer> cured = new ArrayList<Integer>(228);
        Collections.addAll(cured,cur);
        Integer []de = {17,26,41,56,82,107,134,172,214,261,307,364,429,496,567,639,727,814,910,1018,1117,1369,1491,1525,1668,1774,1873,2010,2122,2239,2348,2445,2596,2666,2718,2747,2791,2838,2873,2915,2948,2984,3016,3045,3073,3101,3124,3140,3162,3173,3181,3194,3204,3218,3231,3242,3250,3253,3255,3261,3267,3276,3287,3293,3298,3301,3306,3311,3314,3315,3321,3331,3335,3338,3340,3340,3342,3344,3345,3349,3349,3351,3351,3352,3352,4642,4642,4642,4642,4642,4642,4642,4642,4642,4642,4643,4643,4643,4643,4643,4643,4643,4643,4643,4643,4643,4643,4643,4643,4643,4644,4644,4644,4644,4644,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4645,4646,4646,4646,4647,4647,4648,4648,4648,4648,4648,4648,4648,4648,4648,4648,4648,4648,4648,4648,4648,4648,4648,4648,4649,4649,4651,4651,4652,4653,4653,4653,4655,4655,4656,4657,4659,4659,4663,4664,4665,4666,4668,4674,4676,4679,4683,4684,4687,4688,4688,4693,4696,4699,4704,4707,4708,4710,4710,4710,4712,4713,4716,4716,4717,4718,4718,4719,4720,4722,4725,4728,4729,4730,4731,4734,4735,4735,4735,};
        List<Integer> dead = new ArrayList<Integer>(228);
        Collections.addAll(dead,de);
        d.setConfirmed(confirmed);
        d.setCured(cured);
        d.setDead(dead);

        graphData.postValue(d);

        // Do an asynchronous operation to fetch events.
//        new Thread(()->{
//            String dataJson = Api.getEpidemicDataJson();
//            try {
//                JSONObject jObject = new JSONObject(dataJson);
//                Iterator iter = jObject.keys();
//                List<EpidemicData> epidemicDataList = new ArrayList<>();
//                while (iter.hasNext()){
//                    String region = (String)iter.next();
//                    JSONObject countryBean = jObject.getJSONObject(region);
//                    epidemicDataList.add(EpidemicData.fromJson(countryBean, region));
//                }
//
//                epidemicData.postValue(epidemicDataList);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }


}