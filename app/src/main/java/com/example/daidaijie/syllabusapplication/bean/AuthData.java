package com.example.daidaijie.syllabusapplication.bean;

import io.realm.RealmObject;

/**
 * yuan
 * 2019/10/25
 **/
public class AuthData extends RealmObject {

    /**
     * "data": {
     *      *         "user_id": 5671,
     *      *         "avatar": "https://i.loli.net/2019/10/11/qBgGuUl9xrEZXH2.jpg",
     *      *         "nickname": "反击呀少女",
     *      *         "level": 0,
     *      *         "token": "146058"
     *      *     }
     */
    String user_id;
    String avatar;
    String nickname;
    int level;
    String token;

    public String getUser_id() {
        return user_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public int getLevel() {
        return level;
    }

    public String getToken() {
        return token;
    }
}
