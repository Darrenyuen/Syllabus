package com.example.daidaijie.syllabusapplication.syllabus.customizelesson;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;

import butterknife.BindView;

public class CustomizeActivity extends BaseActivity implements ICustomView, View.OnClickListener {
    private String TAG = this.getClass().getSimpleName();

    @BindView(R.id.lessonNameEditText)
    EditText name;
    @BindView(R.id.classroomEditText)
    EditText classroom;
    @BindView(R.id.weeks)
    TextView weeks;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    private ICustomizePresenter iCustomizePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        iCustomizePresenter = new CustomizePresenter(this);
        weeks.setOnClickListener(this);
        detail.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weeks: chooseWeek();break;
            case R.id.detail: chooseDetail();break;
            case R.id.add:
                add(name.getText().toString(), classroom.getText().toString(), weeks.getText().toString(), detail.getText().toString());
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_custom_lesson;
    }

    public void chooseWeek() {
        Log.d(TAG, "chooseWeek: ");

    }

    public void chooseDetail() {
        Log.d(TAG, "chooseDetail: ");
    }
    @Override
    public void setWeeks(String weeks) {
        this.weeks.setText(weeks);
    }

    @Override
    public void setDetail(String detail) {
        this.detail.setText(detail);
    }

    @Override
    public boolean add(String name, String classroom, String weeks, String detail) {
        Log.d(TAG, "add: ");
        return false;
    }
}
