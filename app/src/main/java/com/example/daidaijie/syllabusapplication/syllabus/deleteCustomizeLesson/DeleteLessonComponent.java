package com.example.daidaijie.syllabusapplication.syllabus.deleteCustomizeLesson;

import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.syllabus.SyllabusComponent;

import dagger.Component;
import dagger.Provides;

/**
 * yuan
 * 2019/11/2
 **/
@PerActivity
@Component(dependencies = SyllabusComponent.class, modules = DeleteLessonModule.class)
public interface DeleteLessonComponent {
    void inject(DeleteLessonActivity deleteLessonActivity);
}
