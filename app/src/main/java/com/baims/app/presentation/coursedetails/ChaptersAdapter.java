package com.baims.app.presentation.coursedetails;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baims.app.R;
import com.baims.app.data.entities.Chapter;
import com.baims.app.data.entities.Lecture;
import com.baims.app.presentation.common.ListItemListener;
import com.baims.app.presentation.common.ListItemListener2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Radwa Elsahn on 10/23/2018.
 */
public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.ViewHolder> {
    List<Chapter> mList;
    Context mContext;
    String color;
    ListItemListener2<Lecture> mListItemListener;


    public ChaptersAdapter(Context context, String color) {
        this.mContext = context;
        this.color = color.isEmpty() ? "#000000" : color;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chapter, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Chapter chapter = mList.get(position);

        viewHolder.mTvChapterName.setText(chapter.getName());
        if(color.isEmpty())
            color = "#000000";

        viewHolder.mTvChapterName.setBackgroundColor(Color.parseColor(color));

        LecturesAdapter lecturesAdapter = new LecturesAdapter(mContext, color);
        lecturesAdapter.setList(chapter.getLectures());
        lecturesAdapter.setOnClickListener(mListItemListener,chapter.getName());

        viewHolder.mRcLectures.setAdapter(lecturesAdapter);
    }

    public void setOnClickListener(ListItemListener2<Lecture> listItemListener) {
        mListItemListener = listItemListener;
    }

    public void setList(List<Chapter> list) {

        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return this.mList != null ? mList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_chapter_name)
        TextView mTvChapterName;

        @BindView(R.id.rc_lectures)
        RecyclerView mRcLectures;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }


}
