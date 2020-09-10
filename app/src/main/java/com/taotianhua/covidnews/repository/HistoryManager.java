package com.taotianhua.covidnews.repository;

import android.util.Log;

import com.taotianhua.covidnews.COVIDNewsApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 历史记录管理类
 * 单例模式
 * 只保存用户查看过的新闻的 id，没有具体内容
 * 如果需要具体的新闻，应该通过 id 在 Repository 中获取
 */
public class HistoryManager {

    HistoryManager() {
        historyCache = new HashSet<>(getHistory());
    }

    /* 由于需要频繁查找一个 id 是否在历史中，所以用一个 set 来加速，构造的时候读入文件中的内容 */
    private Set<String> historyCache;

    /* 单例 */
    private static HistoryManager instance;

    public static HistoryManager getInstance() {
        if (instance == null) {
            instance = new HistoryManager();
        }
        return instance;
    }

    private static String prefix = "history";   /* 存储的目录名 */

    private static String filename = "history"; /* 存储的文件名 */

    /**
     * 存储新闻的 id
     *
     * @param id
     */
    public void addHistory(String id) {
        /* naive 的实现，把文本id写到文件的最后一行*/

        // Update cache
        historyCache.add(id);

        // Get the file
        File directory = COVIDNewsApplication.getAppContext().getFilesDir();
        File subDir = new File(directory, prefix);
        if (!subDir.exists()) {
            subDir.mkdir();
        }
        File file = new File(subDir, filename);

        // Write to file end
        try {
            FileOutputStream out = new FileOutputStream(file, true);
            PrintStream ps = new PrintStream(out);
            ps.println(id);
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * 读取所有的历史记录
     *
     * @return 历史新闻的 id，最新的记录在最前
     */
    public List<String> getHistory() {
        /* 配合 addHistory 的 naive 的实现，读取文本的每一行 */
        String content = LocalStorage.load(prefix, filename);

        // splitting of empty string gives a list of size one, we want empty list
        if (content == null || content.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> list = Arrays.stream(content.split("\n"))
                .distinct()
                .collect(Collectors.toList());
        Collections.reverse(list);  /* 最后插入的最新 */
        return list;
    }

    public void clearHistory() {
        // Get the file
        File directory = COVIDNewsApplication.getAppContext().getFilesDir();
        File subDir = new File(directory, prefix);
        if (!subDir.exists()) {
            subDir.mkdir();
        }
        File file = new File(subDir, filename);

       boolean succeed =  file.delete();
       if(succeed){
           Log.i("HistoryManager","history file deleted");
       }else {
           Log.i("HistoryManager","no file is deleted");
       }
       historyCache.clear();
    }

    /**
     * id 是否在记录中
     *
     * @param id
     * @return
     */
    public boolean inHistory(String id) {
        return historyCache.contains(id);
    }
}
