package com.example.daidaijie.syllabusapplication.syllabus.deleteCustomizeLesson;

import android.content.Context;

import com.example.daidaijie.syllabusapplication.ILoginModel;
import com.example.daidaijie.syllabusapplication.LoginModel;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.syllabus.CustomizeLessonDataBase;
import com.example.daidaijie.syllabusapplication.syllabus.customizelesson.CustomizeContract;

import dagger.Module;
import dagger.Provides;

/**
 * yuan
 * 2019/11/2
 **/
@Module
public class DeleteLessonModule {

    private DeleteLessonContract.view view;
    public DeleteLessonModule(DeleteLessonContract.view view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    DeleteLessonContract.view provideView() {
        return view;
    }

    @Provides
    @PerActivity
    IDeleteLessonModel provideDeleteLessonModel(ILoginModel loginModel, CustomizeLessonDataBase customizeLessonDataBase) {
        return new DeleteLessonModel(loginModel, customizeLessonDataBase);
    }

    @Provides
    CustomizeLessonDataBase provideCustomizeLessonDataBase() {
        return new CustomizeLessonDataBase((Context) view, "customize_lesson", null, 1);
    }
}
