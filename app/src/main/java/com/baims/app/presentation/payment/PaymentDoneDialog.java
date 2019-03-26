package com.baims.app.presentation.payment;

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
import android.view.WindowManager;
import android.widget.TextView;

import com.baims.app.R;
import com.baims.app.data.entities.ChapterTag;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.SubscriptionNotifier;
import com.baims.app.presentation.sectiondetails.TagsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Radwa Elsahn on 10/28/2018.
 */
public class PaymentDoneDialog extends DialogFragment {

    @BindView(R.id.rc_tags)
    RecyclerView mRcTags;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_name)
    TextView mTvName;

    private Unbinder unbinder;

    List<ChapterTag> tags;
    String color;
    Double price;

    @Override
    public void onStart() {
        super.onStart();

//        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//
//        Window window = getDialog().getWindow();
//        window.setAttributes(lp);
    }

    public PaymentDoneDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static PaymentDoneDialog newInstance(String title) {
        PaymentDoneDialog frag = new PaymentDoneDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_payment_done, container, false);
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

        tags = getArguments().getParcelableArrayList(Constants.KEY_TAGS);
        color = getArguments().getString(Constants.KEY_COLOR);
        String name = getArguments().getString(Constants.KEY_SECTION_NAME);
        price = getArguments().getDouble(Constants.KEY_EXTRA_PAYMENT_FINAL);
//        if (price == 0.0)
//            price = getArguments().getDouble(Constants.KEY_EXTRA_COURSE_PRICE);
        Log.i("name", name+ "");

        mTvName.setText(name);
        mTvPrice.setText(price == 0.0 ? getString(R.string.free) : String.format(getString(R.string.currency), price + ""));

        TagsAdapter tagsAdapter = new TagsAdapter(getContext(), color);
        tagsAdapter.setList(tags);
        mRcTags.setAdapter(tagsAdapter);

        SubscriptionNotifier.getInstance().post(false);// show subsription

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_close)
    public void onCloseClick() {
        dismiss();
        if (price > 0)
            getActivity().finish();
    }

}
