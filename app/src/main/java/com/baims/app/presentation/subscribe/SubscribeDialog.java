package com.baims.app.presentation.subscribe;

import android.content.Context;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.baims.app.presentation.common.BaseFragment;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.confirmpayment.ConfirmPaymentDialog;
import com.baims.app.presentation.payment.PaymentDoneDialog;
import com.baims.app.presentation.sectiondetails.TagsAdapter;
import com.baims.app.presentation.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Radwa Elsahn on 10/28/2018.
 */
public class SubscribeDialog extends DialogFragment implements SubscribeView {

    @BindView(R.id.ed_cobon)
    EditText mEdtCobone;
    @BindView(R.id.rc_tags)
    RecyclerView mRcTags;

    @BindView(R.id.btn_subscribe)
    Button mBtnSubscribe;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_have_cobone)
    TextView mTvHaveCobone;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;


    private Unbinder unbinder;

    List<ChapterTag> tags;
    String color;
    String name;

    ISubscribePresenter presenter;

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

    public SubscribeDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static SubscribeDialog newInstance(String title) {
        SubscribeDialog frag = new SubscribeDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_subscribe, container, false);
        unbinder = ButterKnife.bind(this, v);

//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new SubscribePresenter(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(getContext())));

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEdtCobone.requestFocus();

//        Utils.hideKeyboard(getActivity());
        tags = getArguments().getParcelableArrayList(Constants.KEY_TAGS);
        color = getArguments().getString(Constants.KEY_COLOR);
        name = getArguments().getString(Constants.KEY_EXTRA_COURSE_NAME);
        String price = getArguments().getString(Constants.KEY_EXTRA_COURSE_PRICE);
        presenter.setPrice(price);

        presenter.setHasPromoCode(getArguments().getBoolean(Constants.KEY_EXTRA_COURSE_HAS_PROMOCODE));
        presenter.setLink(getArguments().getString(Constants.KEY_EXTRA_COURSE_LINK));
//        mBtnSubscribe.setBackgroundColor(Color.parseColor(color));
        mBtnSubscribe.setBackground(Utils.createRoundedBg(getContext(), color));

        mTvName.setText(name);
        mTvPrice.setText(price.equals("0") ? getString(R.string.free) : String.format(getString(R.string.currency), price+""));

        TagsAdapter tagsAdapter = new TagsAdapter(getContext(), color);
        tagsAdapter.setList(tags);
        mRcTags.setAdapter(tagsAdapter);

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

    @OnClick(R.id.btn_subscribe)
    public void onSubscribeClick() {
        Log.i("radwa","onSubscribeClick");
        presenter.subscribeClick(mEdtCobone.getText().toString());
    }


    @Override
    public void showInValidPromocode() {
        mEdtCobone.setError(getString(R.string.invalid_cobone));
    }

    @Override
    public void openConfirmPage(String urlPayment, double discount, double originalPrice, double finalPrice, String typeCost) {
        dismiss();

        FragmentManager fm = getActivity().getSupportFragmentManager();
        ConfirmPaymentDialog dialog = ConfirmPaymentDialog.newInstance("Baims");

        Bundle bundle = getArguments();
        bundle.putString(Constants.KEY_PAYMENT_URL, urlPayment);
        bundle.putString(Constants.KEY_SECTION_NAME, name);
        bundle.putParcelableArrayList(Constants.KEY_TAGS, (ArrayList<? extends Parcelable>) tags);
        bundle.putDouble(Constants.KEY_EXTRA_COURSE_PRICE, originalPrice);
        bundle.putDouble(Constants.KEY_EXTRA_PAYMENT_FINAL, finalPrice);
        bundle.putInt(Constants.KEY_EXTRA_PAYMENT_DISCOUNT, (int) discount);
        bundle.putString(Constants.KEY_EXTRA_PAYMENT_TYPE_COST, typeCost);
        bundle.putString(Constants.KEY_COLOR, color);
        Log.e("promo",mEdtCobone.getText().toString());
        bundle.putString(Constants.KEY_EXTRA_COURSE_HAS_PROMOCODE, mEdtCobone.getText().toString());
        dialog.setArguments(bundle);

        dialog.show(fm, "Baims");



//        Intent intent = new Intent(getActivity(), ConfirmPaymentActivity.class);
//        intent.putExtra(Constants.KEY_PAYMENT_URL, urlPayment);
//        intent.putExtra(Constants.KEY_SECTION_NAME, name);
//        intent.putParcelableArrayListExtra(Constants.KEY_TAGS, (ArrayList<? extends Parcelable>) tags);
//        intent.putExtra(Constants.KEY_EXTRA_COURSE_PRICE, originalPrice);
//        intent.putExtra(Constants.KEY_EXTRA_PAYMENT_FINAL, finalPrice);
//        intent.putExtra(Constants.KEY_EXTRA_PAYMENT_DISCOUNT, (int) discount);
//        intent.putExtra(Constants.KEY_EXTRA_PAYMENT_TYPE_COST, typeCost);
//        intent.putExtra(Constants.KEY_COLOR, color);
//        Log.e("promo",mEdtCobone.getText().toString());
//        intent.putExtra(Constants.KEY_EXTRA_COURSE_HAS_PROMOCODE, mEdtCobone.getText().toString());
//
//        getActivity().startActivity(intent);


    }

    @Override
    public void showLogin() {
//        Intent intent = new Intent(getActivity(), LoginActivity.class);
//        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
//        startActivity(intent);

        Toast.makeText(getContext(), "برجاء تسجيل الدخول", Toast.LENGTH_SHORT).show();
        dismiss();

//        ActivityUtil.replaceFragmentToActivity(getActivity().getSupportFragmentManager(),
//                new LoginPromptFragment(), R.id.content, getActivity());
    }

    @OnClick(R.id.tv_have_cobone)
    public void onHaveCoboneClick() {
        mTvHaveCobone.setVisibility(View.GONE);
        mEdtCobone.setVisibility(View.VISIBLE);
        mBtnSubscribe.setText(getString(R.string.use_promocode));
    }

    @Override
    public void showPromoCodeFeilds() {
        mTvHaveCobone.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress(final boolean show) {
        showProgressBar(progressBar, show);
    }

    @Override
    public void showError(String error) {
//        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        BaseFragment.showDialog2(getContext(),error);
    }

    public void openSuccessPage() {
        dismiss();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        PaymentDoneDialog dialog = PaymentDoneDialog.newInstance("Baims");

        Bundle bundle = getArguments();//new Bundle();
        //bundle.putAll();

        bundle.putDouble(Constants.KEY_EXTRA_COURSE_PRICE, 0);
        bundle.putDouble(Constants.KEY_EXTRA_PAYMENT_FINAL, 0);
        bundle.putString(Constants.KEY_SECTION_NAME, name);
        dialog.setArguments(bundle);

        dialog.show(fm, "Baims");
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
