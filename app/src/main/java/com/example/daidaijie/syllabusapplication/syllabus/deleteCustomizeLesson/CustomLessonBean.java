package com.example.daidaijie.syllabusapplication.syllabus.deleteCustomizeLesson;

/**
 * yuan
 * 2019/11/1
 **/
public class CustomLessonBean {
    /**
     * id: 12345678
     * years: 2019-2020
     * semester: 秋季学期
     * name: 吃饭
     * classroom: 三饭
     * week: 1-16
     * detail: 星期一 ABC
     */
    private String id;
    private String years;
    private String semester;
    private String name;
    private String classroom;
    private String week;
    private String detail;

    public CustomLessonBean() {
        super();
    }
    public CustomLessonBean(String id, String years, String semester, String name, String classroom, String week, String detail) {
        this.id = id;
        this.years = years;
        this.semester = semester;
        this.name = name;
        this.classroom = classroom;
        this.week = week;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeek() {
        return week;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
