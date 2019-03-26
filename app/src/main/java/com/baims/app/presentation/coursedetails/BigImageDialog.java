package com.baims.app.presentation.coursedetails;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.baims.app.R;
import com.baims.app.data.api.RestClient;
import com.baims.app.data.entities.ChapterTag;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.sectiondetails.TagsAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Radwa Elsahn on 10/28/2018.
 */
public class BigImageDialog extends DialogFragment {

    @BindView(R.id.iv_image)
    ImageView mIvImage;


    private Unbinder unbinder;

    List<ChapterTag> tags;
    String color;
    Double price;

    @Override
    public void onStart() {
        super.onStart();

        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        Window window = getDialog().getWindow();
        window.setAttributes(lp);
    }

    public BigImageDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static BigImageDialog newInstance(String title, String imageUrl) {
        BigImageDialog frag = new BigImageDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString(Constants.KEY_QUESTION_IMAGE, imageUrl);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_big_image, container, false);
        unbinder = ButterKnife.bind(this, v);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        String imageUrl = getArguments().getString(Constants.KEY_QUESTION_IMAGE);
        Glide.with(getActivity())
                .load(RestClient.ROOT + imageUrl)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(Constants.roundCurve)))
                .into(mIvImage);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_close)
    public void onCloseClick() {
        dismiss();
    }

}
