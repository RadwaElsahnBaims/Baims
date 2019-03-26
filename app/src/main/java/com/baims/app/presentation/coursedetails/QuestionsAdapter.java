package com.baims.app.presentation.coursedetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baims.app.R;
import com.baims.app.data.api.RestClient;
import com.baims.app.data.entities.Question;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.LikedNotifier;
import com.baims.app.presentation.common.SubscriptionNotifier;
import com.baims.app.presentation.question.QuestionClickListener;
import com.baims.app.presentation.subscribe.SubscribeDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Radwa Elsahn on 10/17/2018.
 */
public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    List<Question> mList;
    Context mContext;
    QuestionClickListener mListItemListener;
    int roundCurve = 20;
    String color;

    public QuestionsAdapter(Context context, String color) {
        this.mContext = context;
        this.color = color.isEmpty() ? "#000000" : color;

        //LikedNotifier.getInstance().subscribe(indx -> updateLiked(indx));

    }

    private void updateLiked(Integer position) {
        mList.get(position).setLike(true);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    public void setOnClickListener(QuestionClickListener listItemListener) {
        mListItemListener = listItemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsAdapter.ViewHolder viewHolder, int position) {
        Question question = mList.get(position);

        if (question.getChildComments() != null && question.getChildComments().size() > 0) {
            viewHolder.rc_comments.setVisibility(View.VISIBLE);

            QuestionsAdapter adapter = new QuestionsAdapter(mContext, color);
            adapter.setList(question.getChildComments());
            adapter.setOnClickListener(mListItemListener);
            viewHolder.rc_comments.setAdapter(adapter);
        } else
            viewHolder.rc_comments.setVisibility(View.GONE);

        viewHolder.mTvComment.setText(question.getContent());
        viewHolder.mTvLikes.setText(question.getNumLike());
        viewHolder.mTvTime.setText(question.getTimeTask());
        viewHolder.mTvTime.setVisibility(question.getTimeTask().isEmpty() ? View.GONE : View.VISIBLE);

        viewHolder.mTvTime.setBackgroundColor(Color.parseColor(color));

        viewHolder.mIvReply.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);

        viewHolder.mIvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListItemListener.onReplyClick(question);
            }
        });

        viewHolder.mTvLikes.setText(question.getNumLike().equals("0") ? "" : question.getNumLike());
        if (question.getLike())
            viewHolder.mTvLikes.setCompoundDrawablesWithIntrinsicBounds(
                    null, // Drawable left
                    ContextCompat.getDrawable(mContext, R.drawable.ic_liked), // Drawable top
                    null, // Drawable right
                    null // Drawable bottom
            );

        viewHolder.mTvLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLiked(viewHolder.getAdapterPosition());
                mListItemListener.onLikeClick(question, viewHolder.getAdapterPosition());
            }
        });

        if (!question.getReplyUserImage().isEmpty()) {
            viewHolder.mIvUserBack.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(RestClient.ROOT + question.getReplyUserImage())
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(roundCurve)))
                    .into(viewHolder.mIvUser);

            Glide.with(mContext)
                    .load(RestClient.ROOT + question.getParentUserImage())
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(roundCurve)).placeholder(R.drawable.circle))
                    .into(viewHolder.mIvUserBack);

            viewHolder.mTvUserName.setText(question.getReplyUserName());
        } else {
            viewHolder.mIvUserBack.setVisibility(View.GONE);
            viewHolder.mTvUserName.setText(question.getParentUserName());
            Glide.with(mContext)
                    .load(RestClient.ROOT + question.getParentUserImage())
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(roundCurve)))
                    .into(viewHolder.mIvUser);
        }

        if (question.getImage() != null && !question.getImage().isEmpty()) {
            viewHolder.mIvImage.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(question.getImage())
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(roundCurve)))
                    .into(viewHolder.mIvImage);

            viewHolder.mIvImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListItemListener.onImageClick(question.getImage());
                }
            });
        }
        if (question.getAudio() != null && !question.getAudio().isEmpty()) {
            viewHolder.mBtnAudio.setVisibility(View.VISIBLE);
            viewHolder.mBtnAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(RestClient.ROOT + question.getAudio()), "audio/*");
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public void setList(List<Question> list) {

        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return this.mList != null ? mList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_user_back)
        ImageView mIvUserBack;

        @BindView(R.id.iv_user)
        ImageView mIvUser;

        @BindView(R.id.tv_user_name)
        TextView mTvUserName;

        @BindView(R.id.tv_comment)
        TextView mTvComment;

        @BindView(R.id.tv_likes)
        TextView mTvLikes;

        @BindView(R.id.iv_reply)
        ImageView mIvReply;

        @BindView(R.id.iv_image)
        ImageView mIvImage;

        @BindView(R.id.btn_audio)
        Button mBtnAudio;

        @BindView(R.id.rc_comments)
        RecyclerView rc_comments;

        @BindView(R.id.tv_time)
        TextView mTvTime;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.iv_reply)
        void onReplyClick() {
            //mListItemListener.itemListener(mList.get(getAdapterPosition()));
//            if (!mList.get(getAdapterPosition()).isRead()) {
//                mList.get(getAdapterPosition()).setRead(true);
//                notifyItemChanged(getAdapterPosition());
//            }
        }
    }


}
