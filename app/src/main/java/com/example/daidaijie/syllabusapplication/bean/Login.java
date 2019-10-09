package com.example.daidaijie.syllabusapplication.bean;

/**
 * Created by jarvis yuen
 * Date: 2019/9/28
 */
public class Login {
    /**
     * {
     *     "code": 200,
     *     "message": "success",
     *     "data": {
     *         "token": "041263"
     *     }
     * }
     */
    int code;
    String message;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    Data data;
    static class Data {
        String token;
    }
}
