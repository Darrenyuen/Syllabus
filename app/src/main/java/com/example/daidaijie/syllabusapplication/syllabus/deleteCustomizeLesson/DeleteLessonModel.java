package com.example.daidaijie.syllabusapplication.syllabus.deleteCustomizeLesson;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daidaijie.syllabusapplication.ILoginModel;
import com.example.daidaijie.syllabusapplication.bean.Lesson;
import com.example.daidaijie.syllabusapplication.syllabus.CustomizeLessonDataBase;

import java.util.LinkedList;
import java.util.List;

/**
 * yuan
 * 2019/11/2
 **/
public class DeleteLessonModel implements IDeleteLessonModel {
    private ILoginModel iLoginModel;
    private CustomizeLessonDataBase customizeLessonDataBase;

    public DeleteLessonModel(ILoginModel iLoginModel, CustomizeLessonDataBase customizeLessonDataBase) {
        super();
        this.iLoginModel = iLoginModel;
        this.customizeLessonDataBase = customizeLessonDataBase;
    }

    @Override
    public List<CustomLessonBean> loadDataFromDataBase() {
        List<CustomLessonBean> lessonBeans = new LinkedList<>();

        SQLiteDatabase sqLiteDatabase = customizeLessonDataBase.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from customize_lesson where years like ? and semester=?", new String[]{iLoginModel.getCurrentSemester().getYearString(), iLoginModel.getCurrentSemester().getSeasonString()});
        CustomLessonBean customLessonBean;

        while (cursor.moveToNext()) {
            customLessonBean = new CustomLessonBean();
            customLessonBean.setId(cursor.getString(cursor.getColumnIndex("id")));
            customLessonBean.setName(cursor.getString(cursor.getColumnIndex("name")));
            customLessonBean.setWeek(cursor.getString(cursor.getColumnIndex("week")));
            customLessonBean.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
            lessonBeans.add(customLessonBean);
        }
        sqLiteDatabase.close();
        return lessonBeans;
    }

}
