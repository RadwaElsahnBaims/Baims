package com.baims.app.presentation.confirmpayment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.data.entities.ChapterTag;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.payment.PaymentActivity;
import com.baims.app.presentation.payment.PaymentDoneDialog;
import com.baims.app.presentation.sectiondetails.TagsAdapter;
import com.baims.app.presentation.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Radwa Elsahn on 10/28/2018.
 */
public class ConfirmPaymentDialog extends DialogFragment implements ConfirmPaymentView {

    @BindView(R.id.tv_cobon)
    EditText mTvCobone;
    @BindView(R.id.rc_tags)
    RecyclerView mRcTags;

    @BindView(R.id.btn_subscribe)
    Button mBtnSubscribe;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_price_after_discount)
    TextView mTvPriceAfterDiscount;
    @BindView(R.id.tv_discount)
    TextView mTvDiscount;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;


    List<ChapterTag> tags;
    String color;
    String name;
    Double finalPrice;
    Double originalPrice;
    int discount;
    String type;

    ConfirmPaymentPresenter presenter;

    private Unbinder unbinder;

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

    public ConfirmPaymentDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ConfirmPaymentDialog newInstance(String title) {
        ConfirmPaymentDialog frag = new ConfirmPaymentDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_confirm_payment, container, false);
        unbinder = ButterKnife.bind(this, v);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ConfirmPaymentPresenter(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(getContext())));

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        Utils.hideKeyboard(getActivity());
        bindUi();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void bindUi() {

        presenter.setUrlPayment(getArguments().getString(Constants.KEY_PAYMENT_URL));
        tags = getArguments().getParcelableArrayList(Constants.KEY_TAGS);
        color = getArguments().getString(Constants.KEY_COLOR);
        name = getArguments().getString(Constants.KEY_SECTION_NAME);
        originalPrice = getArguments().getDouble(Constants.KEY_EXTRA_COURSE_PRICE, 0);
        finalPrice = getArguments().getDouble(Constants.KEY_EXTRA_PAYMENT_FINAL, 0);
        type = getArguments().getString(Constants.KEY_EXTRA_PAYMENT_TYPE_COST);
        discount = getArguments().getInt(Constants.KEY_EXTRA_PAYMENT_DISCOUNT, 0);
        mBtnSubscribe.setBackground(Utils.createRoundedBg(getContext(), color, 15));
        mTvName.setText(name);

        if (discount > 0) {
            mTvPrice.setText(originalPrice.equals("0") ? getString(R.string.free) : String.format(getString(R.string.currency), originalPrice + ""));
            mTvDiscount.setText("-" + discount + "% " + getString(R.string.discount));
            mTvCobone.setText(getArguments().getString(Constants.KEY_EXTRA_COURSE_HAS_PROMOCODE));
            if (discount == 100)
                type = "free";

        } else { // no discount
            mTvPrice.setVisibility(View.GONE);
            mTvDiscount.setVisibility(View.GONE);
            mTvCobone.setVisibility(View.GONE);
        }

        if (type.equals("free") || originalPrice == 0 || discount == 100) {
            mTvPriceAfterDiscount.setText(getString(R.string.free));
        } else {
            mTvPriceAfterDiscount.setText(finalPrice.equals("0") ? getString(R.string.free) : String.format(getString(R.string.currency), finalPrice + ""));
            //String.format(getString(R.string.currency), String.valueOf(finalPrice)));
        }

        TagsAdapter tagsAdapter = new TagsAdapter(getContext(), color);
        tagsAdapter.setList(tags);
        mRcTags.setAdapter(tagsAdapter);

    }


    @OnClick(R.id.iv_close)
    public void onCloseClick() {
        dismiss();
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }


    @Override
    public void openPaymentPage(String url) {
        dismiss();
        Intent intent = new Intent(getContext(), PaymentActivity.class);
        intent.putExtras(getArguments());
        startActivity(intent);
    }

    @Override
    public void openSuccessPage() {
        dismiss();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        PaymentDoneDialog dialog = PaymentDoneDialog.newInstance("Baims");

        Bundle bundle = getArguments();//new Bundle();
        //bundle.putAll();

        bundle.putDouble(Constants.KEY_EXTRA_COURSE_PRICE, originalPrice);
        bundle.putDouble(Constants.KEY_EXTRA_PAYMENT_FINAL, finalPrice);
        bundle.putString(Constants.KEY_SECTION_NAME, name);

        dialog.setArguments(bundle);

        dialog.show(fm, "Baims");
    }

    @OnClick(R.id.btn_subscribe)
    public void onSubscribeClick() {
        presenter.onSubscribeClick(type);
    }


    protected void showProgressBar(ProgressBar progressBar, boolean show) {
        if (progressBar != null)
            if (show) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
    }


}
