package com.baims.app.presentation.coursedetails;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baims.app.R;
import com.baims.app.data.entities.Lecture;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.ListItemListener;
import com.baims.app.presentation.common.ListItemListener2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Radwa Elsahn on 10/23/2018.
 */
public class LecturesAdapter extends RecyclerView.Adapter<LecturesAdapter.ViewHolder> {
    private List<Lecture> mList;
    private Context mContext;
    private String color;
    private ListItemListener2<Lecture> mListItemListener;
    int selectedIndex = -1;
    String chapterName;

    public LecturesAdapter(Context context, String color) {
        this.mContext = context;
        this.color = color;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lecture, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Lecture lecture = mList.get(position);

        viewHolder.mTvTitle.setText(lecture.getName());
        viewHolder.mTvTime.setText(lecture.getVideoTime());

        //mContext.getResources().getColor(R.color.colorHint));
        //if (lecture.getPlaying())
        if (position == selectedIndex)
            viewHolder.layout.setBackgroundColor(Color.parseColor("#33" + color.substring(1, color.length())));
        else
            viewHolder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.intercom_white));

        if (lecture.getTypeCost().equals(Constants.premium))
            viewHolder.mTvPrice.setVisibility(View.GONE);
        else
            viewHolder.mTvPrice.setVisibility(View.VISIBLE);

        if (!lecture.getFile().isEmpty()) {
            viewHolder.mIvIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_file));
        }
        else
            viewHolder.mIvIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_video));
    }


//    public void setSelected(int position) {
//        for (int i = 0; i < mList.size(); i++) {
//            if (i == position)
//                mList.get(i).setPlaying(true);
//            else
//                mList.get(i).setPlaying(false);
//        }
//
//        notifyDataSetChanged();
//
//    }

    public void setOnClickListener(ListItemListener2<Lecture> listItemListener, String chapterName) {
        mListItemListener = listItemListener;
        this.chapterName = chapterName;
    }

    public void setList(List<Lecture> list) {

        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return this.mList != null ? mList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTvTitle;

        @BindView(R.id.tv_time)
        TextView mTvTime;

        @BindView(R.id.tv_price)
        TextView mTvPrice;

        @BindView(R.id.main_layout)
        ConstraintLayout layout;

        @BindView(R.id.iv_icon)
        ImageView mIvIcon;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick(R.id.main_layout)
        void setItemClicked() {
            Lecture lecture = mList.get(getAdapterPosition());
            mListItemListener.itemListener(lecture, chapterName);
            //setSelected(getAdapterPosition());
            selectedIndex = getAdapterPosition();
            notifyDataSetChanged();
        }
    }


}
