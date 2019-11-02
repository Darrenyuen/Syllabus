package com.example.daidaijie.syllabusapplication.syllabus.deleteCustomizeLesson;

import javax.inject.Inject;

/**
 * yuan
 * 2019/11/2
 **/
public class DeleteLessonPresenter implements DeleteLessonContract.presenter {
    private IDeleteLessonModel iDeleteLessonModel;
    private DeleteLessonContract.view view;

    @Inject
    public DeleteLessonPresenter(DeleteLessonContract.view view, IDeleteLessonModel iDeleteLessonModel) {
        super();
        this.iDeleteLessonModel = iDeleteLessonModel;
        this.view = view;
    }

    @Override
    public void start() {
        view.showData(iDeleteLessonModel.loadDataFromDataBase());
    }

}
