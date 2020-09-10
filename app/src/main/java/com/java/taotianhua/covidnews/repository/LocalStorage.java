package com.java.taotianhua.covidnews.repository;

import android.util.Log;

import com.java.taotianhua.covidnews.COVIDNewsApplication;
import com.java.taotianhua.covidnews.model.EventBrief;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * Local Storage Helper Class
 */
public class LocalStorage {
    public static Boolean store(String prefix, String name, String content) {
        File directory = COVIDNewsApplication.getAppContext().getFilesDir();
        File subDir = new File(directory, prefix);
        if (!subDir.exists()) {
            subDir.mkdir();
        }
        File file = new File(subDir, name);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public static String load(String prefix, String name) {
        File directory = COVIDNewsApplication.getAppContext().getFilesDir();
        File subDir = new File(directory, prefix);
        if (subDir.exists() && !subDir.isDirectory()) {// it was once a file not dir
            if (!subDir.delete()) {
                Log.e("LocalStorage", "cannot delete");
            }
        }
        if (!subDir.exists()) {
            if (!subDir.mkdir()) {
                Log.e("LocalStorage", "cannot make dir");
            }
        }

        File file = new File(subDir, name);

        if (!file.exists()) return "";

        String content;

        StringBuffer stringBuffer = new StringBuffer();
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line = reader.readLine();
            while (line != null) {
                stringBuffer.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred opening raw file for reading.
            e.printStackTrace();
        } finally {
            content = stringBuffer.toString();
        }

        return content;
    }

    public static Boolean exist(String prefix, String name) {
        File directory = COVIDNewsApplication.getAppContext().getFilesDir();
        File subDir = new File(directory, prefix);
        if (!subDir.exists()) {
            subDir.mkdir();
        }
        File file = new File(subDir, name);

        return file.exists();
    }

    public static Boolean existAndGood(String prefix, String name) {
        if (exist(prefix, name)) {
            String content = load(prefix, name);
            return content != null && content.length() > 0;
        }

        return false;
    }


    public static void storeLocalCache(String catalog, List<EventBrief> list) {
        String prefix = catalog;
        File directory = COVIDNewsApplication.getAppContext().getFilesDir();
        File subDir = new File(directory, prefix);
        if (!subDir.exists()) {
            subDir.mkdir();
        }
        File file = new File(subDir, "cache_list");


        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<EventBrief> loadLocalCache(String catalog, int num) {
        String prefix = catalog;
        File directory = COVIDNewsApplication.getAppContext().getFilesDir();
        File subDir = new File(directory, prefix);
        if (!subDir.exists()) {
            subDir.mkdir();
        }
        File file = new File(subDir, "cache_list");
        if (!file.exists()) {
            return new ArrayList<>();
        }
        FileInputStream fin = null;
        List<EventBrief> list = null;
        try {
            fin = new FileInputStream(file);
            ObjectInputStream oos = new ObjectInputStream(fin);
            list = (List<EventBrief>) oos.readObject();
            oos.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
