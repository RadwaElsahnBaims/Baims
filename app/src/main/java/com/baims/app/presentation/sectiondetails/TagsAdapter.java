package com.baims.app.presentation.sectiondetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baims.app.R;
import com.baims.app.data.entities.ChapterTag;
import com.baims.app.presentation.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Radwa Elsahn on 10/23/2018.
 */
public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {
    List<ChapterTag> mList;
    Context mContext;
    String color;


    public TagsAdapter(Context context, String color) {
        this.mContext = context;
        this.color = color;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tag, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ChapterTag tag = mList.get(position);

        viewHolder.mTvTag.setText(tag.getName());

        if (color == null || color.isEmpty()) {
            color = "#BF5DAE";
        }

        viewHolder.mTvTag.setBackground(Utils.createRoundedBg(mContext, color, 75));

    }

    public void setList(List<ChapterTag> list) {

        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return this.mList != null ? mList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_tag)
        TextView mTvTag;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }


}
