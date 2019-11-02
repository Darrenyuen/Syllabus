package com.example.daidaijie.syllabusapplication.syllabus.deleteCustomizeLesson;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;

import java.util.List;

/**
 * yuan
 * 2019/11/2
 **/
public interface DeleteLessonContract {
    interface view extends BaseView {

        void showData(List<CustomLessonBean> lessonBeans);

        void showFailMessage(String msg);

        void showSuccessMessage(String msg);
    }
    interface presenter extends BasePresenter {

    }
}
