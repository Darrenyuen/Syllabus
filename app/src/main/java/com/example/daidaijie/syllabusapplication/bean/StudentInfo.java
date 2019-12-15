package com.example.daidaijie.syllabusapplication.bean;

import com.google.gson.annotations.SerializedName;

/**
 * yuan
 * 2019/12/11
 **/
public class StudentInfo {

    /**
     * number : 2015101006
     * name :
     * gender : 男
     * major : 工学大类(2015)
     */

    @SerializedName("number")
    public String number;
    @SerializedName("name")
    public String name;
    @SerializedName("gender")
    public String gender;
    @SerializedName("major")
    public String major;

    public StudentInfo() {
        super();
    }

    public StudentInfo(String number, String name, String gender, String major) {
        super();
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.major = major;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
