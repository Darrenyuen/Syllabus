package com.example.daidaijie.syllabusapplication.syllabus.classmateDetail;

import android.util.Log;

import com.example.daidaijie.syllabusapplication.base.IBaseModel;
import com.example.daidaijie.syllabusapplication.bean.Lesson;
import com.example.daidaijie.syllabusapplication.bean.StudentInfo;
import com.example.daidaijie.syllabusapplication.util.LoggerUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscriber;

/**
 * Created by daidaijie on 2016/10/26.
 */

public class ClassmatePresenter implements ClassmateContract.presenter {

    private String TAG = this.getClass().getSimpleName();

    IClassmateModel mIClassmateModel;
    ClassmateContract.view mView;
    Document document;
    List<StudentInfo> studentInfoList;

    @Inject
    public ClassmatePresenter(IClassmateModel IClassmateModel, ClassmateContract.view view) {
        mIClassmateModel = IClassmateModel;
        mView = view;
        studentInfoList = new LinkedList<>();
    }

    @Override
    public void search(String keyword) {
        Log.d(TAG, "search: " + keyword);
        mIClassmateModel.searchStudentsList(keyword, new IBaseModel.OnGetSuccessCallBack<List<StudentInfo>>() {
            @Override
            public void onGetSuccess(List<StudentInfo> studentInfos) {
                mView.showData(studentInfos);
            }
        }, new IBaseModel.OnGetFailCallBack() {
            @Override
            public void onGetFail() {
                mView.showData(new ArrayList<StudentInfo>());
            }
        }, studentInfoList);
    }

    @Override
    public void start() {
        mView.showLoading(true);
        studentInfoList.clear();
                         mIClassmateModel.getStudentsFromNet()
                         .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {
                        mView.showLoading(false);
                        mView.showData(studentInfoList);
                        }

                        @Override
                        public void onError(Throwable throwable) {
                        mView.showWarningMessage("请连接校园网查看");
                        throwable.printStackTrace();
                        }

                        @Override
                        public void onNext(String s) {

                        /**
                         * <tr class="gridview_row">
                         * <td>2016401088</td>
                         * <td>刘美琪</td>
                         * <td>女</td>
                         * <td>工学大类(2016) </td>
                         * <td align="center">0</td>
                         * <td>&nbsp;</td>
                         * </tr>
                         */

                        document = Jsoup.parseBodyFragment(s);
                        Elements allTdEle = document.getElementsByTag("td");
                        int numOfStudents = (allTdEle.size() - 17) / 6;
                        Log.d(TAG, "onNext: " + numOfStudents);
                        int i = 0, j = 18;
                        //第19个数据开始都是有价值的数据，每个六个数据项（为同一人）new 一个对象
                        while (i < numOfStudents) {
                            StudentInfo studentInfo = new StudentInfo();
                            while (j < allTdEle.size() && (j - 17) % 6 != 0 ) {
                                if ((j - 17) % 6 == 1) studentInfo.setNumber(allTdEle.get(j).text());
                                if ((j - 17) % 6 == 2) studentInfo.setName(allTdEle.get(j).text());
                                if ((j - 17) % 6 == 3) studentInfo.setGender(allTdEle.get(j).text());
                                if ((j - 17) % 6 == 4) studentInfo.setMajor(allTdEle.get(j).text());
                                j++;
                            }
                            j++;
                            studentInfoList.add(studentInfo);
                            i++;
                        }
                    }
                });
    }
}
