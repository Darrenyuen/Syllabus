package com.example.daidaijie.syllabusapplication.syllabus.customizelesson;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;

/**
 * yuan
 * 2019/10/30
 **/
public interface CustomizeContract {
    interface view extends BaseView<presenter> {

        void finishThis();

        String chooseWeek();

        void chooseDetail();

        void showFailMessage(String msg);

        void showSuccessMessage(String msg);
    }
    interface presenter extends BasePresenter {
        void addLesson(String name, String classroom, String weekSelected, String detail);
    }
}
