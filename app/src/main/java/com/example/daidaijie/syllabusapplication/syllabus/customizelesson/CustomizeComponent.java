package com.example.daidaijie.syllabusapplication.syllabus.customizelesson;

import com.example.daidaijie.syllabusapplication.AppComponent;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.syllabus.SyllabusComponent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * yuan
 * 2019/10/30
 **/
@PerActivity
@Component(dependencies = SyllabusComponent.class, modules = CustomizeModule.class)
public interface CustomizeComponent {

    void inject(CustomizeActivity customizeActivity);
}
