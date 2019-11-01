package com.example.daidaijie.syllabusapplication.syllabus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * yuan
 * 2019/11/1
 **/
public class CustomizeLessonDataBase extends SQLiteOpenHelper {

    public CustomizeLessonDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // TODO: 2019/11/1 创建一张数据表
        String sql = "create table customize_lesson(years varchar(64), semester varchar(64), id varchar(64), name varchar(64), classroom varchar(64), week varchar(64), detail varchar(64))";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
