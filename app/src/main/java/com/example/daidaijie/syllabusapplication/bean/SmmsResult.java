package com.example.daidaijie.syllabusapplication.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jarvis yuen
 * Date: 2019/10/7
 */
public class SmmsResult {
    @SerializedName("code")
    private String status;
    @SerializedName("data")
    private SmData data;
    //display if upload failed
    @SerializedName("msg")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SmData getData() {
        return data;
    }

    public void setData(SmData data) {
        this.data = data;
    }

    public class SmData {

        @SerializedName("width")
        private int width;
        @SerializedName("height")
        private int height;
        @SerializedName("url")
        private String picUrl;
        @SerializedName("delete")
        private String deleteUrl;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getDeleteUrl() {
            return deleteUrl;
        }

        public void setDeleteUrl(String deleteUrl) {
            this.deleteUrl = deleteUrl;
        }
    }
//    boolean success;
//    String code;
//    String message;
//    public Data data;
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public static class Data {
//        int file_id;
//        int width;
//        int height;
//        String filename;
//        String storename;
//        int size;
//        String path;
//        String hash;
//        String url;
//        String delete;
//        String page;
//
//        public String getUrl() {
//            return url;
//        }
//    }
//    String RequestId;
}
