package com.example.daidaijie.syllabusapplication.syllabus.customizelesson;

/**
 * Created by jarvis yuen
 * Date: 2019/9/27
 */
public class CustomizePresenter implements ICustomizePresenter {
    private ICustomView customView;
    public CustomizePresenter(ICustomView customView) {
        super();
        this.customView = customView;
    }
}
