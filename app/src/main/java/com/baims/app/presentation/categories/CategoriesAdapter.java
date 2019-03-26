package com.baims.app.presentation.categories;

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
import com.baims.app.data.entities.Category;
import com.baims.app.data.entities.Course;
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
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    List<Category> mList;
    Context mContext;
    int roundCurve = 20;
    ListItemListener<Category> mListItemListener;

    public CategoriesAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    public void setOnClickListener(ListItemListener<Category> listItemListener) {
        mListItemListener = listItemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder viewHolder, int position) {
        Category category = mList.get(position);
        Log.i("name", category.getName());
        viewHolder.mTvCategoryName.setText(category.getName());

        Glide.with(mContext)
                .load(category.getIcon_image()).into(viewHolder.mImage);
                //.apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(roundCurve))
                        //        .placeholder(R.drawable.ic_book)
//                ).into(viewHolder.mImage);
    }

    public void setList(List<Category> list) {

        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return this.mList != null ? mList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_category_name)
        TextView mTvCategoryName;

        @BindView(R.id.iv_image)
        ImageView mImage;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.main_layout)
        void setItemClicked() {
            Category category = mList.get(getAdapterPosition());
            mListItemListener.itemListener(category);
        }


    }


}
