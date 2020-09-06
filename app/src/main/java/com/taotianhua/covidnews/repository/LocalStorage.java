package com.taotianhua.covidnews.repository;

import com.taotianhua.covidnews.COVIDNewsApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalStorage {
    public static Boolean store(String name, String content){
        File directory = COVIDNewsApplication.getAppContext().getFilesDir();
        File file = new File(directory, name);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public static String load(String name){
        String content = null;
        File directory = COVIDNewsApplication.getAppContext().getFilesDir();
        File file = new File(directory, name);

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

    public static Boolean exist(String name){
        File directory = COVIDNewsApplication.getAppContext().getFilesDir();
        File file = new File(directory, name);
        return file.exists();
    }

    public static Boolean existAndGood(String name){
        if(exist(name)){
            String content = load(name);
            return content != null && content.length() > 0;
        }

        return false;
    }

    public static Boolean checkRead(String id){
        // TODO
        return false;
    }

    public static void storeRead(String id){
        // TODO

    }




}
