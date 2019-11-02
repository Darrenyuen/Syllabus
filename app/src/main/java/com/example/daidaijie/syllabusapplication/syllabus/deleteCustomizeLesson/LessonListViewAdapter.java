package com.example.daidaijie.syllabusapplication.syllabus.deleteCustomizeLesson;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.bean.Lesson;
import com.example.daidaijie.syllabusapplication.syllabus.CustomizeLessonDataBase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * yuan
 * 2019/11/1
 **/
public class LessonListViewAdapter extends ArrayAdapter<CustomLessonBean> {
    private String TAG = this.getClass().getSimpleName();
    private int resourceId;
    private List<CustomLessonBean> lessonList;
    public LessonListViewAdapter(@NonNull Context context, int resource, @NonNull List<CustomLessonBean> objects) {
        super(context, resource, objects);
        resourceId = resource;
        lessonList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final CustomLessonBean customLessonBean = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.nameTextView.setText(customLessonBean.getName());
        viewHolder.weekTextView.setText(customLessonBean.getWeek());
        viewHolder.timeTextView.setText(customLessonBean.getDetail());
        viewHolder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("是否删除该课程");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lessonList.remove(position);
                        notifyDataSetChanged();
                        CustomizeLessonDataBase databaseHelper = new CustomizeLessonDataBase(App.getContext(), "customize_lesson", null, 1);
                        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
                        //执行多条件sql语句进行删除具体数据项
                        String sql = "delete from customize_lesson where id = '" + customLessonBean.getId() + "' ";
                        Log.d(TAG, "onClick: " + customLessonBean.getId());
                        sqLiteDatabase.execSQL(sql);
                        sqLiteDatabase.close();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        return view;
    }

    class ViewHolder {
        @BindView(R.id.nameTextView)
        TextView nameTextView;
        @BindView(R.id.weekTextView)
        TextView weekTextView;
        @BindView(R.id.timeTextView)
        TextView timeTextView;
        @BindView(R.id.deleteImageView)
        ImageView deleteImageView;

        public ViewHolder(View view) {
            super();
            ButterKnife.bind(this, view);
        }
    }
}
