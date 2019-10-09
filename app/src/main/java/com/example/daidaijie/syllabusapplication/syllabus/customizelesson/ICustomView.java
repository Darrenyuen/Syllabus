package com.example.daidaijie.syllabusapplication.syllabus.customizelesson;

/**
 * Created by jarvis yuen
 * Date: 2019/9/27
 */
public interface ICustomView {
    void setWeeks(String weeks);
    void setDetail(String detail);
    boolean add(String name, String classroom, String weeks, String detail);
}
