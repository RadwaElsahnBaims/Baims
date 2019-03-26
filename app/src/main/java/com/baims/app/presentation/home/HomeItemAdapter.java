package com.baims.app.presentation.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baims.app.R;
import com.baims.app.data.entities.BundleCourse;
import com.baims.app.data.entities.Section;
import com.baims.app.presentation.common.ListItemListener;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Radwa Elsahn on 10/21/2018.
 */
public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {

    List<Section> mList;
    Context mContext;
    HomeView mView;
    int roundCurve = 20;
    private ListItemListener<BundleCourse> mListItemListener;

    public HomeItemAdapter(Context context, HomeView view) {
        this.mContext = context;
        this.mView = view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_section, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Section section = mList.get(position);
        Log.i("Section", new Gson().toJson(section));

        viewHolder.mTvSectionName.setText(section.getName());

        CoursesBundlesAdapter coursesAdapter = new CoursesBundlesAdapter(mContext, section.getName());
        coursesAdapter.setList(section.getBundleCourse());
        coursesAdapter.setOnClickListener(mListItemListener);

        viewHolder.mRCCourses.setAdapter(coursesAdapter);
    }

    public void setList(List<Section> list) {

        this.mList = list;
    }

    public void setOnClickListener(ListItemListener<BundleCourse> listItemListener) {
        mListItemListener = listItemListener;
    }

    @Override
    public int getItemCount() {
        return this.mList != null ? mList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_section_name)
        TextView mTvSectionName;

        @BindView(R.id.rc_courses)
        RecyclerView mRCCourses;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
