package com.example.daidaijie.syllabusapplication.bean;

/**
 * Created by jarvis yuen
 * Date: 2019/10/7
 */
public class SmmsResult {
    boolean success;
    String code;
    String message;
    public Data data;

    public boolean isSuccess() {
        return success;
    }

    public static class Data {
        int file_id;
        int width;
        int height;
        String filename;
        String storename;
        int size;
        String path;
        String hash;
        String url;
        String delete;
        String page;

        public String getUrl() {
            return url;
        }
    }
    String RequestId;
}
