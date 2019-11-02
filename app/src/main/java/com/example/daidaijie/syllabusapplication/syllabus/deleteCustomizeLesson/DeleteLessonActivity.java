package com.example.daidaijie.syllabusapplication.syllabus.deleteCustomizeLesson;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.syllabus.CustomizeLessonDataBase;
import com.example.daidaijie.syllabusapplication.syllabus.SyllabusComponent;
import com.example.daidaijie.syllabusapplication.util.SnackbarUtil;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class DeleteLessonActivity extends BaseActivity implements DeleteLessonContract.view{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.listView)
    ListView listView;

    @Inject
    DeleteLessonPresenter deleteLessonPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTitleBar(mToolbar);

        DaggerDeleteLessonComponent.builder()
                .syllabusComponent(SyllabusComponent.getINSTANCE())
                .deleteLessonModule(new DeleteLessonModule(this))
                .build().inject(this);

        deleteLessonPresenter.start();

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_delete_lesson;
    }

    @Override
    public void showData(List<CustomLessonBean> lessonBeans) {
        LessonListViewAdapter lessonListViewAdapter = new LessonListViewAdapter(this, R.layout.item_delete_lesson, lessonBeans);
        listView.setAdapter(lessonListViewAdapter);
    }

    @Override
    public void showSuccessMessage(String msg) {
        SnackbarUtil.ShortSnackbar(
                listView,
                msg,
                SnackbarUtil.Confirm
        ).show();
    }

    @Override
    public void showFailMessage(String msg) {
        SnackbarUtil.ShortSnackbar(
                listView,
                msg,
                SnackbarUtil.Alert
        ).show();
    }
}
