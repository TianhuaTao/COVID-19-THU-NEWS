package com.taotianhua.covidnews.network;

import java.text.MessageFormat;

public class UrlConst {
    static final String URL_LIST_TEMPLATE = "https://covid-dashboard.aminer.cn/api/events/list?type={0}&page={1}&size={2}";
    static final String URL_EPIDEMIC = "https://covid-dashboard.aminer.cn/api/dist/epidemic.json";
    static final String URL_EVENTS = "https://covid-dashboard.aminer.cn/api/dist/events.json";
    static final String URL_EVENT_DETAIL_TEMPLATE = "https://covid-dashboard-api.aminer.cn/event/{0}";
    static final String URL_ENTITY_QUERY_TEMPLATE = "https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity={0}";
    static final String URL_EXPERTS = "https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2";


    public static String genUrlListPagination(String type, int page, int size){
        return MessageFormat.format(URL_LIST_TEMPLATE, type, page,size);
    }
    public static String genUrlEventDetail(String id){
        return MessageFormat.format(URL_EVENT_DETAIL_TEMPLATE,id);
    }
    public static String genUrlEntityQuery(String entity){
        return MessageFormat.format(URL_ENTITY_QUERY_TEMPLATE,entity);
    }


}
