package com.baims.app.presentation.courses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baims.app.R;
import com.baims.app.data.entities.BundleCourse;
import com.baims.app.data.entities.Course;
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
public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    List<Course> mList;
    Context mContext;
    int roundCurve = 20;
    ListItemListener<Course> mListItemListener;

    public CoursesAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bundle, parent, false);
        return new ViewHolder(view);
    }

    public void setOnClickListener(ListItemListener<Course> listItemListener) {
        mListItemListener = listItemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesAdapter.ViewHolder viewHolder, int position) {
        Course course = mList.get(position);
        Log.i("name", course.getName());
        viewHolder.mTvCourseName.setText(course.getName());
        viewHolder.mTvCoursesNumber.setText(String.valueOf(course.getCountLecture()));
        viewHolder.mTvInstructorName.setText(course.getInstructor());
        Glide.with(mContext)
                .load(course.getImage())
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(roundCurve)))

                //.placeholder(R.drawable.default_placeholder)
                .into(viewHolder.mImage);

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

    }

    public void setList(List<Course> list) {

        this.mList = list;
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
            Course course = mList.get(getAdapterPosition());
            mListItemListener.itemListener(course);
        }



    }


}
