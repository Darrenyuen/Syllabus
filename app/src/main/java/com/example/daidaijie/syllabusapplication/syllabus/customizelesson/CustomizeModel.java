package com.example.daidaijie.syllabusapplication.syllabus.customizelesson;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daidaijie.syllabusapplication.ILoginModel;
import com.example.daidaijie.syllabusapplication.bean.Lesson;
import com.example.daidaijie.syllabusapplication.bean.Syllabus;
import com.example.daidaijie.syllabusapplication.syllabus.CustomizeLessonDataBase;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by jarvis yuen
 * Date: 2019/10/18
 */
public class CustomizeModel implements ICustomizeModel{
    private String TAG = this.getClass().getSimpleName();

    Realm mRealm;
    ILoginModel mLoginModel;
    Syllabus mSyllabus;

    CustomizeLessonDataBase customizeLessonDataBase;
    SQLiteDatabase sqLiteDatabase;

    public CustomizeModel(Realm realm, ILoginModel iLoginModel, CustomizeLessonDataBase customizeLessonDataBase) {
        super();
        mRealm = realm;
        mLoginModel = iLoginModel;
        this.customizeLessonDataBase = customizeLessonDataBase;
    }

    @Override
    public void addLesson(String name, String classroom, String weekSelected, String detail) {
        List<Lesson> lessonList = null;
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Syllabus> results =
                        realm.where(Syllabus.class)
                                .equalTo("mSemester.season", mLoginModel.getCurrentSemester().getSeason())
                                .equalTo("mSemester.startYear", mLoginModel.getCurrentSemester().getStartYear())
                                .findAll();
                if (results.size() > 0) {
                    mSyllabus = realm.copyFromRealm(results.first());
                }
            }
        });
        if (mSyllabus != null) {
            lessonList = mSyllabus.loadLessonForCustomizeUse(mRealm);
        }
        // TODO: 2019/11/1 没有判断是否和已有的课程冲突
        Log.d(TAG, "test: " + lessonList.size());

        Log.d(TAG, "addLesson: " + mLoginModel.getCurrentSemester().getYearString());
        Log.d(TAG, "addLesson: " + mLoginModel.getCurrentSemester().getSeasonString());

        sqLiteDatabase = customizeLessonDataBase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Long.parseLong(String.valueOf(Calendar.getInstance().getTimeInMillis())));
        contentValues.put("years", mLoginModel.getCurrentSemester().getYearString());
        contentValues.put("semester", mLoginModel.getCurrentSemester().getSeasonString());
        contentValues.put("name", name);
        contentValues.put("classroom", classroom);
        contentValues.put("week", weekSelected);
        contentValues.put("detail", detail);
        sqLiteDatabase.insert("customize_lesson", null, contentValues);
        sqLiteDatabase.close();
    }

}
