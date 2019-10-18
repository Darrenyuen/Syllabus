package com.example.daidaijie.syllabusapplication.syllabus.customizelesson;

import com.example.daidaijie.syllabusapplication.LoginModel;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.syllabus.ISyllabusModel;
import com.example.daidaijie.syllabusapplication.syllabus.lessonDetail.LessonInfoContract;

import javax.inject.Inject;

/**
 * Created by jarvis yuen
 * Date: 2019/9/27
 */
public class CustomizePresenter {
    CustomizeActivity mView;
    CustomizeModel mCustomizeModel;

    public CustomizePresenter(CustomizeActivity view) {
        mView = view;
    }
}
