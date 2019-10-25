package com.example.daidaijie.syllabusapplication.bean;

import io.realm.RealmObject;

/**
 * yuan
 * 2019/10/25
 **/
public class AuthLogin extends RealmObject {
    /**
     * {
     *     "code": 200,
     *     "message": "success",
     *     "data": {
     *         "user_id": 5671,
     *         "avatar": "https://i.loli.net/2019/10/11/qBgGuUl9xrEZXH2.jpg",
     *         "nickname": "反击呀少女",
     *         "level": 0,
     *         "token": "146058"
     *     }
     * }
     */
    int code;
    String message;
    AuthData data;

    public int getCode() {
        return code;
    }

    public AuthData getData() {
        return data;
    }
}
