package com.example.daidaijie.syllabusapplication.syllabus.customizelesson;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.syllabus.SyllabusComponent;
import com.example.daidaijie.syllabusapplication.util.SnackbarUtil;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
public class CustomizeActivity extends BaseActivity implements CustomizeContract.view, View.OnClickListener {
    private String TAG = this.getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.lessonNameEditText)
    EditText name;
    @BindView(R.id.classroomEditText)
    EditText classroom;
    @BindView(R.id.customWeek)
    TextView week;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.addLessonButton)
    Button addLessonButton;

    private ArrayList<Integer> startWeek;
    private ArrayList<ArrayList<Integer>> endWeek;
    private ArrayList<String> day;
    private ArrayList<String> time;

    private String weekSelected;
    private String timeSelected;

    @Inject
    CustomizePresenter customizePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTitleBar(mToolbar);

        DaggerCustomizeComponent.builder()
                .syllabusComponent(SyllabusComponent.getINSTANCE())
                .customizeModule(new CustomizeModule(this))
                .build().inject(this);

        customizePresenter.start();

        week.setOnClickListener(this);
        detail.setOnClickListener(this);
        addLessonButton.setOnClickListener(this);

        startWeek = new ArrayList<>();
        endWeek = new ArrayList<>();
        day = new ArrayList<>();
        time = new ArrayList<>();

        for (int i = 1; i<= 18; i++) {
            startWeek.add(i);
        }
        for (int i = 1; i<=18; i++) {
            endWeek.add(startWeek);
        }
        for (int i=0; i<7; i++) {
            switch (i) {
                case 0 : day.add("星期日"); break;
                case 1 : day.add("星期一"); break;
                case 2 : day.add("星期二"); break;
                case 3 : day.add("星期三"); break;
                case 4 : day.add("星期四"); break;
                case 5 : day.add("星期五"); break;
                case 6 : day.add("星期六"); break;
            }
        }
        for (int i=1; i<14; i++) {
            if (i<10) {
                time.add(String.valueOf(i));
            } else if (i==10) {
                time.add("0");
            } else if (i==11) {
                time.add("A");
            } else if (i==12) {
                time.add("B");
            } else {
                time.add("C");
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail: chooseDetail();break;
            case R.id.customWeek: chooseWeek(); break;
            case R.id.addLessonButton:
                Log.d(TAG, "onClick: " + Calendar.getInstance().getTimeInMillis());
                Log.d(TAG, "onClick: " + " " + name.getText().toString()+ " " + classroom.getText().toString()+ " " + weekSelected+ " " + detail.getText().toString());
                customizePresenter.addLesson(name.getText().toString(), classroom.getText().toString(), weekSelected, timeSelected);
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_custom_lesson;
    }

    @Override
    public String chooseWeek() {
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int i, int i1, int i2, View view) {
                weekSelected = startWeek.get(i) + "-" + endWeek.get(i).get(i1);
                week.setText(weekSelected);
            }
        }).setTitleText("周数选择")
                .setContentTextSize(18)
                .setDividerColor(Color.GRAY)
                .setSelectOptions(0, 0)
                .setBgColor(Color.WHITE)
                .isRestoreItem(true)
                .isCenterLabel(false)
                .build();
        optionsPickerView.setPicker(startWeek, endWeek);
        optionsPickerView.show();
        Log.d(TAG, "chooseWeek: " + weekSelected);
        return weekSelected;
    }

    @Override
    public void chooseDetail() {
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int i, int i1, int i2, View view) {
                timeSelected = day.get(i) + " " + time.get(i1) + "-" + time.get(i2);
                detail.setText(timeSelected);
            }
        }).setTitleText("时间选择")
                .setContentTextSize(18)
                .setDividerColor(Color.GRAY)
                .setSelectOptions(0, 0)
                .setBgColor(Color.WHITE)
                .isRestoreItem(true)
                .isCenterLabel(false)
                .build();
        optionsPickerView.setNPicker(day, time, time);
        optionsPickerView.show();
    }

    @Override
    public void showSuccessMessage(String msg) {
        SnackbarUtil.ShortSnackbar(
                addLessonButton,
                msg,
                SnackbarUtil.Confirm
        ).show();
    }

    @Override
    public void showFailMessage(String msg) {
        SnackbarUtil.ShortSnackbar(
                addLessonButton,
                msg,
                SnackbarUtil.Alert
        ).show();
    }
}
