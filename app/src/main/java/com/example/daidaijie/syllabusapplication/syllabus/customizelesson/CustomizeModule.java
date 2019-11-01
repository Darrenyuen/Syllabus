package com.example.daidaijie.syllabusapplication.syllabus.customizelesson;

import android.content.Context;

import com.example.daidaijie.syllabusapplication.ILoginModel;
import com.example.daidaijie.syllabusapplication.di.qualifier.realm.UserRealm;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.syllabus.CustomizeLessonDataBase;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * yuan
 * 2019/10/30
 **/
@Module
public class CustomizeModule {

    private CustomizeContract.view view;
    public CustomizeModule(CustomizeContract.view view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    CustomizeContract.view provideView() {
        return view;
    }

    @PerActivity
    @Provides
    ICustomizeModel provideCustomizeModel(@UserRealm Realm realm, ILoginModel iLoginModel, CustomizeLessonDataBase customizeLessonDataBase) {
        return new CustomizeModel(realm, iLoginModel, customizeLessonDataBase);
    }

    @Provides
    CustomizeLessonDataBase provideCustomizeLessonDataBase() {
        return new CustomizeLessonDataBase((Context) view, "custom_lesson", null, 1);
    }

}
