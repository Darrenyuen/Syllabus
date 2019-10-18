package com.example.daidaijie.syllabusapplication.syllabus.customizelesson;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.example.daidaijie.syllabusapplication.syllabus.lessonDetail.DaggerLessonInfoComponent;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
public class CustomizeActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = this.getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.lessonNameEditText)
    EditText name;
    @BindView(R.id.classroomEditText)
    EditText classroom;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.customWeekButton)
    RadioButton customWeekButton;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.submitButton)
    Button submitButton;

    private ArrayList<Integer> startWeek;
    private ArrayList<ArrayList<Integer>> endWeek;
    private ArrayList<String> day;
    private ArrayList<String> time;

    private String weekSelected;

    CustomizePresenter customizePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTitleBar(mToolbar);

        customizePresenter = new CustomizePresenter(this);
        customWeekButton.setOnClickListener(this);
        detail.setOnClickListener(this);
        submitButton.setOnClickListener(this);
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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.singleWeekButton) {
                    weekSelected = "single";
                } else if (i == R.id.doubleWeekButton) {
                    weekSelected = "double";
                } else {
                    weekSelected = chooseWeek();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail: chooseDetail();break;
            case R.id.submitButton:
                submit(name.getText().toString(), classroom.getText().toString(), weekSelected, detail.getText().toString());
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_custom_lesson;
    }

    public String chooseWeek() {
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int i, int i1, int i2, View view) {
                final String weekSelected = startWeek.get(i) + "-" + endWeek.get(i).get(i1);
                customWeekButton.setText(weekSelected);
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
        return weekSelected;
    }

    public void chooseDetail() {
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int i, int i1, int i2, View view) {
                String str = day.get(i) + " " + time.get(i1) + "-" + time.get(i2);
                detail.setText(str);
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

    public boolean submit(String name, String classroom, String weekSelected, String detail) {
//        Log.d(TAG, "submit: " + name + " " + " " + classroom + " " + weekSelected + " " + detail);

        return false;
    }
}
