package com.baims.app.presentation.sectiondetails;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baims.app.R;
import com.baims.app.data.entities.BundleCourse;
import com.baims.app.presentation.common.ListItemListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Radwa Elsahn on 10/17/2018.
 */
class SectionDetailsCoursesAdapter extends RecyclerView.Adapter<SectionDetailsCoursesAdapter.ViewHolder> {

    List<BundleCourse> mList;
    Context mContext;
    SectionDetailsView mView;
    String color;

    private ListItemListener<BundleCourse> mListItemListener;

    public SectionDetailsCoursesAdapter(Context context, SectionDetailsView view) {
        this.mContext = context;
        this.mView = view;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bundle_course_info, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        BundleCourse course = mList.get(position);

        viewHolder.mTvTime.setText(course.getTimeVideos());
        viewHolder.mTvVideosCount.setText(String.format(mContext.getString(R.string.videos), course.getCountVideos()));

        if (color.isEmpty())
            color = "#000000";

        viewHolder.mTvCost.setBackgroundColor(Color.parseColor(color));

        LayerDrawable drawable = (LayerDrawable) viewHolder.mTvCost.getBackground();
        //drawable.setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));

        if (course.isBuy()) {
            // Get another drawable object
            Drawable drawableBlue = ContextCompat.getDrawable(
                    mContext,
                    R.drawable.ic_tick
            );

            // Another way to set left drawable for button widget programmatically
            viewHolder.mTvCost.setCompoundDrawablesWithIntrinsicBounds(
                    null, // Drawable left
                    null, // Drawable top
                    drawableBlue, // Drawable right
                    null // Drawable bottom
            );

            viewHolder.mTvCost.setCompoundDrawablePadding(10);
            viewHolder.mTvCost.setText("");
        } else {
            viewHolder.mTvCost.setText(course.getTotalPrice().equals("0") ? mContext.getString(R.string.free) : String.format(mContext.getString(R.string.currency), course.getTotalPrice()));
        }

        TagsAdapter adapter = new TagsAdapter(mContext, color);
        adapter.setList(course.getChapterTag());
        viewHolder.mRvTags.setAdapter(adapter);
        viewHolder.mRvTags.scrollToPosition(0);
    }

    public void setList(List<BundleCourse> list) {
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

        @BindView(R.id.rc_tags)
        RecyclerView mRvTags;

        @BindView(R.id.tv_time)
        TextView mTvTime;

        @BindView(R.id.tv_videos)
        TextView mTvVideosCount;

        @BindView(R.id.tv_cost)
        TextView mTvCost;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.main_layout)
        void setItemClicked() {
            BundleCourse bundleCourse = mList.get(getAdapterPosition());
            mListItemListener.itemListener(bundleCourse);
        }


    }


}
