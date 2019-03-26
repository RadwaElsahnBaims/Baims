package com.baims.app.presentation.home;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baims.app.R;
import com.baims.app.data.entities.BundleCourse;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.ListItemListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Radwa Elsahn on 10/17/2018.
 */
public class CoursesBundlesAdapter extends RecyclerView.Adapter<CoursesBundlesAdapter.ViewHolder> {

    List<BundleCourse> mList;
    Context mContext;
    int roundCurve = 20;
    String sectionName;
    private ListItemListener<BundleCourse> mListItemListener;

    public CoursesBundlesAdapter(Context context, String sectionName) {
        this.mContext = context;
        this.sectionName = sectionName;
    }

    public CoursesBundlesAdapter(Context context) {
        this.mContext = context;
        this.sectionName = "";
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bundle, parent, false);

        if (!sectionName.isEmpty()) {
            DisplayMetrics display = mContext.getResources().getDisplayMetrics();
            int width = display.widthPixels - (display.widthPixels / 5);
//            int height= display.heightPixels /3;

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
        }

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        BundleCourse course = mList.get(position);

        if (course.getTypeFeat().equals(Constants.COURSE_KEY)) {
            viewHolder.mTvPrice.setVisibility(View.VISIBLE);
            viewHolder.mTvPrice.setText(course.getTotalPrice().equals("0") ? mContext.getString(R.string.free) : String.format(mContext.getString(R.string.currency), course.getTotalPrice()));
            viewHolder.mTvCoursesNumber.setVisibility(View.GONE);

            if (!course.getPrice().equals(course.getTotalPrice())) {
                viewHolder.mTvBefore.setVisibility(View.VISIBLE);
                viewHolder.mTvBefore.setText(course.getTotalPrice().equals("0") ? mContext.getString(R.string.free) : String.format(mContext.getString(R.string.currency), course.getPrice()));
//                viewHolder.mTvBefore.setPaintFlags(viewHolder.mTvBefore.getPaintFlags()
//                        | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                viewHolder.mTvBefore.setVisibility(View.GONE);
            }
        } else {
            viewHolder.mTvPrice.setVisibility(View.GONE);
            viewHolder.mTvBefore.setVisibility(View.GONE);
            viewHolder.mTvCoursesNumber.setText(String.format(mContext.getString(R.string.courses_number), course.getCountCourse()));
            viewHolder.mTvCoursesNumber.setVisibility(View.VISIBLE);
        }

        viewHolder.mTvCourseName.setText(course.getName());
        viewHolder.mTvInstructorName.setText(course.getInstructor());

        Glide.with(mContext)
                .load(course.getImage())
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(roundCurve)))

                //.placeholder(R.drawable.default_placeholder)
                .into(viewHolder.mImage);
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

        @BindView(R.id.tv_instructor_name)
        TextView mTvInstructorName;

        @BindView(R.id.tv_course_name)
        TextView mTvCourseName;

        @BindView(R.id.tv_course_count)
        TextView mTvCoursesNumber;

        @BindView(R.id.iv_image)
        ImageView mImage;

        @BindView(R.id.tv_price)
        TextView mTvPrice;

        @BindView(R.id.tv_price_before)
        TextView mTvBefore;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.main_layout)
        void setItemClicked() {
            BundleCourse bundleCourse = mList.get(getAdapterPosition());
            bundleCourse.setSectionName(sectionName);
            mListItemListener.itemListener(bundleCourse);
        }
    }


}
