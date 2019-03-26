package com.baims.app.presentation.subcategories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baims.app.R;
import com.baims.app.data.entities.Category;
import com.baims.app.data.entities.SubCategory;
import com.baims.app.presentation.common.ListItemListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Radwa Elsahn on 12/17/2018.
 */
public class SubcategoriesAdapter extends RecyclerView.Adapter<SubcategoriesAdapter.ViewHolder> {

    List<SubCategory> mList;
    Context mContext;
    int roundCurve = 20;
    ListItemListener<SubCategory> mListItemListener;

    public SubcategoriesAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public SubcategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subcategory, parent, false);
        return new SubcategoriesAdapter.ViewHolder(view);
    }

    public void setOnClickListener(ListItemListener<SubCategory> listItemListener) {
        mListItemListener = listItemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull SubcategoriesAdapter.ViewHolder viewHolder, int position) {
        SubCategory subCategory= mList.get(position);
        Log.i("namesub", subCategory.getName());
        viewHolder.mTvSubCategoryName.setText(subCategory.getName());

    }

    public void setList(List<SubCategory> list) {

        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return this.mList != null ? mList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_subcategory_name)
        TextView mTvSubCategoryName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.main_layout)
        void setItemClicked() {
            Log.i("aa","aa");
            SubCategory subcategory = mList.get(getAdapterPosition());
            mListItemListener.itemListener(subcategory);
        }


    }


}
