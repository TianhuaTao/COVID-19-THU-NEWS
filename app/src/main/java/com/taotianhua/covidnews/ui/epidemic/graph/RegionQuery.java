package com.taotianhua.covidnews.ui.epidemic.graph;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用来获得国家列表、某个国家对应的省份或是某个省份对应的城市。
 */
public class RegionQuery {
    private static String []countryList = {"Afghanistan","Albania","Algeria","American Samoa","Andorra","Angola","Antigua and Barb.","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bhutan","Bolivia","Bosnia and Herz.","Botswana","Brazil","Brunei","Bulgaria","Burkina Faso","Burundi","Cabo Verde","Cambodia","Cameroon","Canada","Cayman Is.","Central African Rep.","Chad","Chile","China","Colombia","Congo","Costa Rica","Croatia","Cuba","Cyprus","Czechia","Côte d'Ivoire","Dem. Rep. Congo","Denmark","Djibouti","Dominica","Dominican Rep.","Ecuador","Egypt","El Salvador","Eq. Guinea","Eritrea","Estonia","Ethiopia","Faeroe Is.","Fiji","Finland","Fr. Polynesia","France","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guam","Guatemala","Guernsey","Guinea","Guinea-Bissau","Guyana","Haiti","Honduras","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Isle of Man","Israel","Italy","Jamaica","Japan","Jersey","Jordan","Kazakhstan","Kenya","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Mauritania","Mauritius","Mexico","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Myanmar","N. Mariana Is.","Namibia","Nepal","Netherlands","New Zealand","Nicaragua","Niger","Nigeria","Norway","Oman","Pakistan","Palestine","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Puerto Rico","Qatar","Romania","Russia","Rwanda","S. Sudan","Saint Barthelemy","Saint Lucia","San Marino","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Sint Maarten","Slovakia","Slovenia","Somalia","South Africa","South Korea","Spain","Sri Lanka","St-Martin","St. Kitts and Nevis","St. Vin. and Gren.","Sudan","Suriname","Sweden","Switzerland","Syria","São Tomé and Principe","Tajikistan","Tanzania","Thailand","Timor-Leste","Togo","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","U.S. Virgin Is.","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States of America","Uruguay","Uzbekistan","Vatican","Venezuela","Vietnam","World","Yemen","Zambia","Zimbabwe","eSwatini"};
    private static String countryJsonStr = "{\"Australia\":[\"Australian Capital Territory\",\"Jervis Bay Territory\",\"New South Wales\",\"Northern Territory\",\"Queensland\",\"South Australia\",\"Tasmania\",\"Victoria\",\"Western Australia\"],\"Canada\":[\"Alberta\",\"British Columbia\",\"Manitoba\",\"New Brunswick\",\"Newfoundland and Labrador\",\"Northwest Territories\",\"Nova Scotia\",\"Nunavut\",\"Ontario\",\"Prince Edward Island\",\"Québec\",\"Saskatchewan\",\"Yukon\"],\"China\":[\"Anhui\",\"Beijing\",\"Chongqing\",\"Fujian\",\"Gansu\",\"Guangdong\",\"Guangxi\",\"Guizhou\",\"Hainan\",\"Hebei\",\"Heilongjiang\",\"Henan\",\"Hong Kong\",\"Hubei\",\"Hunan\",\"Inner Mongol\",\"Jiangsu\",\"Jiangxi\",\"Jilin\",\"Liaoning\",\"Macao\",\"Ningxia\",\"Qinghai\",\"Shaanxi\",\"Shandong\",\"Shanghai\",\"Shanxi\",\"Sichuan\",\"Taiwan\",\"Tianjin\",\"Xinjiang\",\"Xizang\",\"Yunnan\",\"Zhejiang\"],\"France\":[\"Auvergne-Rhône-Alpes\",\"Bretagne\",\"Centre-Val de Loires\",\"Corse\",\"Grand Est\",\"Guadeloupe\",\"Guyane française\",\"Hauts-de-France\",\"La Réunion\",\"Martinique\",\"Mayotte\",\"Normandie\",\"Nouvelle-Aquitaine\",\"Pays de la Loire\",\"Provence-Alpes-Côté-d’Azur\",\"Île-de-France\"],\"Germany\":[\"Baden-Württemberg\",\"Bayern\",\"Berlin\",\"Brandenburg\",\"Bremen\",\"Hamburg\",\"Hessen\",\"Mecklenburg-Vorpommern\",\"Niedersachsen\",\"Nordrhein-Westfalen\",\"Rheinland-Pfalz\",\"Saarland\",\"Sachsen\",\"Sachsen-Anhalt\",\"Schleswig-Holstein\",\"Thüringen\"],\"Iran\":[\"Alborz\",\"Ardebil\",\"Bushehr\",\"Chahar Mahall and Bakhtiari\",\"East Azarbaijan\",\"Esfahan\",\"Fars\",\"Gilan\",\"Golestan\",\"Hamadan\",\"Hormozgan\",\"Ilam\",\"Kerman\",\"Kermanshah\",\"Khorasan\",\"Khuzestan\",\"Kohgiluyeh and Buyer Ahmad\",\"Kordestan\",\"Lorestan\",\"Markazi\",\"Mazandaran\",\"North Khorasan\",\"Qazvin\",\"Qom\",\"Semnan\",\"Sistan and Baluchestan\",\"South Khorasan\",\"Tehran\",\"West Azarbaijan\",\"Yazd\",\"Zanjan\"],\"Italy\":[\"Abruzzo\",\"Basilicata\",\"Calabria\",\"Campania\",\"Emilia-Romagna\",\"Friuli-Venezia Giulia\",\"Lazio\",\"Liguria\",\"Lombardia\",\"Marche\",\"Molise\",\"Piemonte\",\"Puglia\",\"Sardegna\",\"Sicilia\",\"Trentino-Alto Adige\",\"Umbria\",\"Valle d'Aosta\",\"Veneto\"],\"Japan\":[\"Aichi\",\"Akita\",\"Aomori\",\"Chiba\",\"Ehime\",\"Fukui\",\"Fukuoka\",\"Fukushima\",\"Gifu\",\"Gunma\",\"Hiroshima\",\"Hokkaidō\",\"Hyōgo\",\"Ibaraki\",\"Ishikawa\",\"Kagawa\",\"Kagoshima\",\"Kanagawa\",\"Kumamoto\",\"Kyōto\",\"Kōchi\",\"Mie\",\"Miyagi\",\"Miyazaki\",\"Nagano\",\"Nagasaki\",\"Nara\",\"Niigata\",\"Okayama\",\"Okinawa\",\"Saga\",\"Saitama\",\"Shiga\",\"Shimane\",\"Shizuoka\",\"Tochigi\",\"Tokushima\",\"Tokyo\",\"Tottori\",\"Toyama\",\"Wakayama\",\"Yamagata\",\"Yamaguchi\",\"Yamanashi\",\"Ōita\",\"Ōsaka\"],\"Netherlands\":[\"Antilles\"],\"Saint Barthelemy\":[\"Saint Barthélemy\"],\"South Korea\":[\"Busan\",\"Daegu\",\"Daejeon\",\"Gangwon\",\"Gwangju\",\"Gyeonggi\",\"Incheon\",\"Jeju\",\"North Chungcheong\",\"North Gyeongsang\",\"North Jeolla\",\"Sejong\",\"Seoul\",\"South Chungcheong\",\"South Gyeongsang\",\"South Jeolla\",\"Ulsan\"],\"Spain\":[\"Andalucía\",\"Aragon\",\"Asturias\",\"Baleares\",\"Canary Islands\",\"Cantabria\",\"Castilla-La Mancha\",\"Catalunya\",\"Ceuta\",\"Comunidad de Castilla y León\",\"Extremadura\",\"Galicia\",\"La Rioja\",\"Madrid\",\"Melilla\",\"Murcia\",\"Navarra\",\"País Vasco\",\"Valencia Region\"],\"United Kingdom\":[\"Barking and Dagenham\",\"Barnet\",\"Barnsley\",\"Bath and North East Somerset\",\"Bedford\",\"Bexley\",\"Birmingham\",\"Blackburn with Darwen\",\"Blackpool\",\"Blaenau Gwent\",\"Bolton\",\"Bournemouth and Poole\",\"Bracknell Forest\",\"Bradford\",\"Brent\",\"Bridgend\",\"Brighton and Hove\",\"Bristol\",\"Bromley\",\"Buckinghamshire\",\"Bury\",\"Caerphilly\",\"Calderdale\",\"Cambridgeshire\",\"Camden\",\"Cardiff\",\"Carmarthenshire\",\"Central Bedfordshire\",\"Ceredigion\",\"Cheshire East\",\"Cheshire West and Chester\",\"City\",\"Conwy\",\"Cornwall and Isles of Scilly\",\"Coventry\",\"Croydon\",\"Cumbria\",\"Darlington\",\"Denbighshire\",\"Derby\",\"Derbyshire\",\"Devon\",\"Doncaster\",\"Dorset\",\"Dudley\",\"Durham\",\"Ealing\",\"East Riding of Yorkshire\",\"East Sussex\",\"Enfield\",\"Essex\",\"Flintshire\",\"Gateshead\",\"Gloucestershire\",\"Greenwich\",\"Gwynedd\",\"Hackney and City of London\",\"Halton\",\"Hammersmith and Fulham\",\"Hampshire\",\"Haringey\",\"Harrow\",\"Hartlepool\",\"Havering\",\"Herefordshire\",\"Hertfordshire\",\"Hillingdon\",\"Hounslow\",\"Isle of Wight\",\"Islington\",\"Kensington and Chelsea\",\"Kent\",\"Kingston upon Hull\",\"Kingston upon Thames\",\"Kirklees\",\"Knowsley\",\"Lambeth\",\"Lancashire\",\"Leeds\",\"Leicester\",\"Leicestershire\",\"Lewisham\",\"Lincolnshire\",\"Liverpool\",\"Luton\",\"Manchester\",\"Medway\",\"Merthyr Tydfil\",\"Merton\",\"Middlesbrough\",\"Milton Keynes\",\"Monmouthshire\",\"Neath Port Talbot\",\"Newcastle upon Tyne\",\"Newham\",\"Newport\",\"Norfolk\",\"North East Lincolnshire\",\"North Lincolnshire\",\"North Somerset\",\"North Tyneside\",\"North Yorkshire\",\"Northamptonshire\",\"Northern Ireland\",\"Northumberland\",\"Nottingham\",\"Nottinghamshire\",\"Oldham\",\"Oxfordshire\",\"Pembrokeshire\",\"Peterborough\",\"Plymouth\",\"Portsmouth\",\"Powys\",\"Reading\",\"Redbridge\",\"Redcar and Cleveland\",\"Rhondda, Cynon, Taff\",\"Richmond upon Thames\",\"Rochdale\",\"Rotherham\",\"Royal Borough of Windsor and Maidenhead\",\"Rutland\",\"Salford\",\"Sandwell\",\"Scotland\",\"Sefton\",\"Sheffield\",\"Shropshire\",\"Slough\",\"Solihull\",\"Somerset\",\"South Gloucestershire\",\"South Tyneside\",\"Southampton\",\"Southend-on-Sea\",\"Southwark\",\"Staffordshire\",\"Stockport\",\"Stockton-on-Tees\",\"Stoke-on-Trent\",\"Suffolk\",\"Sunderland\",\"Surrey\",\"Sutton\",\"Swansea\",\"Swindon\",\"Tameside\",\"Telford and Wrekin\",\"Thurrock\",\"Torbay\",\"Torfaen\",\"Tower Hamlets\",\"Trafford\",\"Vale of Glamorgan\",\"Wakefield\",\"Walsall\",\"Waltham Forest\",\"Wandsworth\",\"Warrington\",\"Warwickshire\",\"West Berkshire\",\"West Sussex\",\"Westminster\",\"Wigan\",\"Wiltshire\",\"Wokingham\",\"Wolverhampton\",\"Worcestershire\",\"Wrexham\",\"York\"],\"United States of America\":[\"Alabama\",\"Alaska\",\"Arizona\",\"Arkansas\",\"California\",\"Colorado\",\"Connecticut\",\"Delaware\",\"District of Columbia\",\"Florida\",\"Georgia\",\"Hawaii\",\"Idaho\",\"Illinois\",\"Indiana\",\"Iowa\",\"Kansas\",\"Kentucky\",\"Louisiana\",\"Maine\",\"Maryland\",\"Massachusetts\",\"Michigan\",\"Minnesota\",\"Mississippi\",\"Missouri\",\"Montana\",\"Nebraska\",\"Nevada\",\"New Hampshire\",\"New Jersey\",\"New Mexico\",\"New York\",\"North Carolina\",\"North Dakota\",\"Ohio\",\"Oklahoma\",\"Oregon\",\"Pennsylvania\",\"Rhode Island\",\"South Carolina\",\"South Dakota\",\"Tennessee\",\"Texas\",\"Utah\",\"Vermont\",\"Virginia\",\"Washington\",\"West Virginia\",\"Wisconsin\",\"Wyoming\"]}";
    private static String provinceJsonStr = "{\"Anhui\":[\"Anqing\",\"Bengbu\",\"Bozhou\",\"Chizhou\",\"Chuzhou\",\"Fuyang\",\"Hefei\",\"Huaibei\",\"Huainan\",\"Huangshan\",\"Lu'an\",\"Ma'anshan\",\"Suzhou\",\"Tongling\",\"Wuhu\",\"Xuancheng\"],\"Beijing\":[\"Changping District\",\"Chaoyang District\",\"Daxing District\",\"Dongcheng District\",\"Fangshan District\",\"Fengtai District\",\"Haidian District\",\"Huairou District\",\"Mentougou District\",\"Miyun District\",\"Shijingshan District\",\"Shunyi District\",\"Tongzhou District\",\"Xicheng District\",\"Yanqing District\"],\"Chongqing\":[\"Banan District\",\"Bishan District\",\"Changshou District\",\"Chengkou County\",\"Dadukou District\",\"Dazu District\",\"Dianjiang County\",\"Fengdu County\",\"Fengjie County\",\"Fuling District\",\"Hechuan District\",\"Jiangbei District\",\"Jiangjin District\",\"Jiulongpo District\",\"Kaizhou District\",\"Liangping District\",\"Nan'an District\",\"Pengshui Miao and Tujia Autonomous County\",\"Qianjiang Tujia and Miao Autonomous County\",\"Qijiang District\",\"Rongchang District\",\"Shapingba District\",\"Shizhu Tujia Autonomous County\",\"Tongliang District\",\"Tongnan District\",\"Wanzhou District\",\"Wulong District\",\"Wushan County\",\"Wuxi County\",\"Xiushan Tujia and Miao Autonomous County\",\"Yongchuan District\",\"Youyang Tujia and Miao Autonomous County\",\"Yubei District\",\"Yunyang County\",\"Yuzhong District\",\"Zhong County\"],\"Fujian\":[\"Fuzhou\",\"Longyan\",\"Nanping\",\"Ningde\",\"Putian\",\"Quanzhou\",\"Sanming\",\"Xiamen\",\"Zhangzhou\"],\"Gansu\":[\"Baiyin\",\"Dingxi\",\"Gannan\",\"Jinchang\",\"Lanzhou\",\"Linxia\",\"Longnan\",\"Pingliang\",\"Qingyang\",\"Tianshui\",\"Zhangye\"],\"Guangdong\":[\"Chaozhou\",\"Dongguan\",\"Foshan\",\"Guangzhou\",\"Heyuan\",\"Huizhou\",\"Jiangmen\",\"Jieyang\",\"Maoming\",\"Meizhou\",\"Qingyuan\",\"Shantou\",\"Shanwei\",\"Shaoguan\",\"Shenzhen\",\"Yangjiang\",\"Zhanjiang\",\"Zhaoqing\",\"Zhongshan\",\"Zhuhai\"],\"Guangxi\":[\"Baise\",\"Beihai\",\"Fangchenggang\",\"Guigang\",\"Guilin\",\"Hechi\",\"Hezhou\",\"Laibin\",\"Liuzhou\",\"Nanning\",\"Qinzhou\",\"Wuzhou\",\"Yulin\"],\"Guizhou\":[\"Anshun\",\"Bijie\",\"Guiyang\",\"Liupanshui\",\"Qiandongnan\",\"Qiannan\",\"Qianxinan\",\"Tongren\",\"Zunyi\"],\"Hainan\":[\"Baoting Li and Miao Autonomous County\",\"Changjiang Li Autonomous County\",\"Chengmai\",\"Danzhou\",\"Ding'an County\",\"Dongfang\",\"Haikou\",\"Ledong Li Autonomous County\",\"Lingao\",\"Lingshui Li Autonomous County\",\"Qionghai\",\"Qiongzhong Li and Miao Autonomous County\",\"Sanya\",\"Wanning\",\"Wenchang\"],\"Hebei\":[\"Baoding\",\"Cangzhou\",\"Chengde\",\"Handan\",\"Hengshui\",\"Langfang\",\"Qinhuangdao\",\"Shijiazhuang\",\"Tangshan\",\"Xingtai\",\"Zhangjiakou\"],\"Heilongjiang\":[\"Daqing\",\"Daxinganling\",\"Harbin\",\"Hegang\",\"Heihe\",\"Jiamusi\",\"Jixi\",\"Mudanjiang\",\"Qiqihar\",\"Qitaihe\",\"Shuangyashan\",\"Suihua\",\"Yichun\"],\"Henan\":[\"Anyang\",\"Hebi\",\"Jiaozuo\",\"Jiyuan\",\"Kaifeng\",\"Luohe\",\"Luoyang\",\"Nanyang\",\"Pingdingshan\",\"Puyang\",\"Sanmenxia\",\"Shangqiu\",\"Xinxiang\",\"Xinyang\",\"Xuchang\",\"Zhengzhou\",\"Zhoukou\",\"Zhumadian\"],\"Hubei\":[\"Enshi Tujia and Miao Autonomous Prefecture\",\"Ezhou\",\"Huanggang\",\"Huangshi\",\"Jingmen\",\"Jingzhou\",\"Qianjiang\",\"Shennongjia\",\"Shiyan\",\"Suizhou\",\"Tianmen\",\"Wuhan\",\"Xiangyang\",\"Xianning\",\"Xiantao\",\"Xiaogan\",\"Yichang\"],\"Hunan\":[\"Changde\",\"Changsha\",\"Chenzhou\",\"Hengyang\",\"Huaihua\",\"Loudi\",\"Shaoyang\",\"Xiangtan\",\"Xiangxi\",\"Yiyang\",\"Yongzhou\",\"Yueyang\",\"Zhangjiajie\",\"Zhuzhou\"],\"Inner Mongol\":[\"Baotou\",\"Bayannur\",\"Chifeng\",\"Hohhot\",\"Hulunbuir\",\"Linguolexi\",\"Ordos\",\"Tongliao\",\"Ulanqab\",\"Wuhai\",\"Xinganmeng\"],\"Jiangsu\":[\"Changzhou\",\"Huainan\",\"Lianyungang\",\"Nanjing\",\"Nantong\",\"Suqian\",\"Suzhou\",\"Taizhou\",\"Wuxi\",\"Xuzhou\",\"Yancheng\",\"Yangzhou\",\"Zhenjiang\"],\"Jiangxi\":[\"Fuzhou\",\"Ganzhou\",\"Ji'an\",\"Jingdezhen\",\"Jiujiang\",\"Nanchang\",\"Pingxiang\",\"Shangrao\",\"Xinyu\",\"Yichun\",\"Yingtan\"],\"Jilin\":[\"Baicheng\",\"Changchun\",\"Jilin\",\"Liaoyuan\",\"Siping\",\"Songyuan\",\"Tonghua\",\"Yanbian\"],\"Liaoning\":[\"Anshan\",\"Benxi\",\"Chaoyang\",\"Dalian\",\"Dandong\",\"Fushun\",\"Fuxin\",\"Huludao\",\"Jinzhou\",\"Liaoyang\",\"Panjin\",\"Shenyang\",\"Tieling\",\"Yingkou\"],\"Ningxia\":[\"Guyuan\",\"Shizuishan\",\"Wuzhong\",\"Yinchuan\",\"Zhongwei\"],\"Qinghai\":[\"Haibei\",\"Xining\"],\"Shaanxi\":[\"Ankang\",\"Baoji\",\"Hanzhong\",\"Shangluo\",\"Tongchuan\",\"Weinan\",\"Xi'an\",\"Xianyang\",\"Yan'an\",\"Yulin\"],\"Shandong\":[\"Binzhou\",\"Dezhou\",\"Heze\",\"Jinan\",\"Jining\",\"Liaocheng\",\"Linyi\",\"Qingdao\",\"Rizhao\",\"Tai'an\",\"Weifang\",\"Weihai\",\"Yantai\",\"Zaozhuang\",\"Zibo\"],\"Shanghai\":[\"Baoshan District\",\"Changning District\",\"Chongming District\",\"Fengxian District\",\"Hongkou District\",\"Huangpu District\",\"Jiading District\",\"Jing'an District\",\"Jinshan District\",\"Minhang District\",\"Pudong District\",\"Putuo District\",\"Qingpu District\",\"Songjiang District\",\"Xuhui District\",\"Yangpu District\"],\"Shanxi\":[\"Changzhi\",\"Datong\",\"Jincheng\",\"Jinzhong\",\"Linfen\",\"Lüliang\",\"Shuozhou\",\"Taiyuan\",\"Xinzhou\",\"Yangquan\",\"Yuncheng\"],\"Sichuan\":[\"Bazhong\",\"Chengdu\",\"Dazhou\",\"Deyang\",\"Garzê Tibetan Autonomous Prefecture\",\"Guang'an\",\"Guangyuan\",\"Leshan\",\"Liangshan Yi Autonomous Prefecture\",\"Luzhou\",\"Meishan\",\"Mianyang\",\"Nanchong\",\"Neijiang\",\"Ngawa Tibetan and Qiang Autonomous Prefecture\",\"Panzhihua\",\"Suining\",\"Ya'an\",\"Yibin\",\"Zigong\",\"Ziyang\"],\"Tianjin\":[\"Baodi District\",\"Beichen District\",\"Binhai New Area\",\"Dongli District\",\"Hebei District\",\"Hedong District\",\"Heping District\",\"Hexi District\",\"Hongqiao District\",\"Jinnan District\",\"Nankai District\",\"Ninghe District\",\"Wuqing District\",\"Xiqing District\"],\"Xinjiang\":[\"Akesu\",\"Bayingol Mongolian Autonomous Prefecture\",\"Changji\",\"Shihezi\",\"Tacheng\",\"Turpan\",\"Urumqi\",\"Wujiaqu\",\"Yili\"],\"Xizang\":[\"Lhasa\"],\"Yunnan\":[\"Baoshan\",\"Chuxiong\",\"Dali\",\"Dehong\",\"Kunming\",\"Lijiang\",\"Lincang\",\"Pu'er\",\"Qujing\",\"Wenshan\",\"Xishuangbanna\",\"Yuxi\",\"Zhaotong\"],\"Zhejiang\":[\"Hangzhou\",\"Huzhou\",\"Jiaxing\",\"Jinhua\",\"Lishui\",\"Ningbo\",\"Quzhou\",\"Shaoxing\",\"Taizhou\",\"Wenzhou\",\"Zhoushan\"]}";

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
    public static String [] getProvinceList(String country){
        JSONObject countryJsonObj;
        try {
            countryJsonObj = new JSONObject(countryJsonStr);
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
    public static String [] getCountyList(String province){
        JSONObject provinceJsonObj;
        try {
            provinceJsonObj = new JSONObject(provinceJsonStr);
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