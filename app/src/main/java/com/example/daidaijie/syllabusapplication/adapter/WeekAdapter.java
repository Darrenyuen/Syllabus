package com.example.daidaijie.syllabusapplication.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.daidaijie.syllabusapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by daidaijie on 2016/8/30.
 */
public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();

    private Activity mActivity;

    private int selectItem;

    public interface onItemClickListener {
        void onClick(int position);
    }

    onItemClickListener mOnItemClickListener;

    public int getSelectItem() {
        Log.d(TAG, "setSelectItem: " + selectItem);
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
        Log.d(TAG, "setSelectItem: " + selectItem);
    }

    public onItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public WeekAdapter(Activity activity) {
        mActivity = activity;
        selectItem = 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {



    }

    @Override
    public int getItemCount() {
        return 18;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
