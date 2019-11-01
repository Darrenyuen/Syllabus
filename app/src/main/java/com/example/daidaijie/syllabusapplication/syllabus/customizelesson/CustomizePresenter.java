package com.example.daidaijie.syllabusapplication.syllabus.customizelesson;

import android.util.Log;

import com.example.daidaijie.syllabusapplication.LoginModel;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.syllabus.ISyllabusModel;
import com.example.daidaijie.syllabusapplication.syllabus.lessonDetail.LessonInfoContract;

import javax.inject.Inject;

/**
 * Created by jarvis yuen
 * Date: 2019/9/27
 */
public class CustomizePresenter  implements CustomizeContract.presenter{
    private String TAG = this.getClass().getSimpleName();

    CustomizeContract.view mView;
    ICustomizeModel customizeModel;
    @Inject
    public CustomizePresenter(CustomizeContract.view view, ICustomizeModel iCustomizeModel) {
        mView = view;
        customizeModel = iCustomizeModel;
    }

    @Override
    public void start() {
    }

    @Override
    public void addLesson(String name, String classroom, String weekSelected, String detail) {
        Log.d(TAG, "addLesson: " + name + " " + classroom  + "  " + weekSelected + " " + detail);
        if (name.isEmpty()) {
            mView.showFailMessage("课程名不能为空");
            return;
        } else if (weekSelected == null) {
            mView.showFailMessage("请选择周数");
            return;
        } else if (detail == null) {
            mView.showFailMessage("请选择时间");
            return;
        }

        String[] week = weekSelected.split("-");
        int startWeek = Integer.parseInt(week[0]);
        int endWeek = Integer.parseInt(week[1]);
        Log.d(TAG, "addLesson: ");
        if (startWeek > endWeek) {
            mView.showFailMessage("周数选择不合理");
            return;
        }

        Log.d(TAG, "addLesson: " + detail.substring(4, 7));
        String[] details = detail.substring(4, 7).split("-");
        if (details[0].equals("0")) {
            details[0] = "10";
        } else if (details[0].equals("A")) {
            details[0] = "11";
        } else if (details[0].equals("B")) {
            details[0] = "12";
        } else if (details[0].equals("C")){
            details[0] = "13";
        }

        if (details[1].equals("0")) {
            details[1] = "10";
        } else if (details[1].equals("A")) {
            details[1] = "11";
        } else if (details[1].equals("B")) {
            details[1] = "12";
        } else if (details[1].equals("C")){
            details[1] = "13";
        }

        Log.d(TAG, "addLesson: " + details[0] + Integer.parseInt(details[0]));
        Log.d(TAG, "addLesson: " + details[1] + Integer.parseInt(details[1]));
        if (Integer.parseInt(details[0]) > Integer.parseInt(details[1])) {
            mView.showFailMessage("时间选择不合理");
            return;
        }
        customizeModel.addLesson(name, classroom, weekSelected, detail);
        mView.showSuccessMessage("添加成功");
        mView.finishThis();
    }

}
