//package com.baims.app.presentation.confirmpayment;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.baims.app.R;
//import com.baims.app.data.entities.ChapterTag;
//import com.baims.app.data.repositories.BaimsRemoteDataSource;
//import com.baims.app.data.repositories.BaimsRepository;
//import com.baims.app.data.repository.BaimsCacheDataSource;
//import com.baims.app.data.utils.schedulers.SchedulerProvider;
//import com.baims.app.presentation.common.BaseActivity;
//import com.baims.app.presentation.common.Constants;
//import com.baims.app.presentation.payment.PaymentActivity;
//import com.baims.app.presentation.payment.PaymentDoneDialog;
//import com.baims.app.presentation.sectiondetails.TagsAdapter;
//import com.baims.app.presentation.utils.Utils;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class ConfirmPaymentActivity extends BaseActivity implements ConfirmPaymentView {
//
//    @BindView(R.id.tv_cobon)
//    EditText mTvCobone;
//    @BindView(R.id.rc_tags)
//    RecyclerView mRcTags;
//
//    @BindView(R.id.btn_subscribe)
//    Button mBtnSubscribe;
//    @BindView(R.id.tv_price)
//    TextView mTvPrice;
//    @BindView(R.id.tv_name)
//    TextView mTvName;
//    @BindView(R.id.tv_price_after_discount)
//    TextView mTvPriceAfterDiscount;
//    @BindView(R.id.tv_discount)
//    TextView mTvDiscount;
//
//    @BindView(R.id.progress_bar)
//    ProgressBar progressBar;
//
//
//    List<ChapterTag> tags;
//    String color;
//    String name;
//    Double finalPrice;
//    Double originalPrice;
//    int discount;
//    String type;
//
//    ConfirmPaymentPresenter presenter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_confirm_payment);
//        ButterKnife.bind(this);
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        Utils.hideKeyboard(this);
//        presenter = new ConfirmPaymentPresenter(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(getBaseContext())));
//        bindUi();
//    }
//
//    private void bindUi() {
//
//        presenter.setUrlPayment(getIntent().getStringExtra(Constants.KEY_PAYMENT_URL));
//        tags = getIntent().getParcelableArrayListExtra(Constants.KEY_TAGS);
//        color = getIntent().getStringExtra(Constants.KEY_COLOR);
//        name = getIntent().getStringExtra(Constants.KEY_SECTION_NAME);
//        originalPrice = getIntent().getDoubleExtra(Constants.KEY_EXTRA_COURSE_PRICE, 0);
//        finalPrice = getIntent().getDoubleExtra(Constants.KEY_EXTRA_PAYMENT_FINAL, 0);
//        type = getIntent().getStringExtra(Constants.KEY_EXTRA_PAYMENT_TYPE_COST);
//        discount = getIntent().getIntExtra(Constants.KEY_EXTRA_PAYMENT_DISCOUNT, 0);
//        mBtnSubscribe.setBackground(Utils.createRoundedBg(getApplicationContext(), color, 15));
//        mTvName.setText(name);
//
//        if (discount > 0) {
//            mTvPrice.setText(String.format(getString(R.string.currency), String.valueOf(originalPrice)));
//            mTvDiscount.setText("-" + discount + "% " + getString(R.string.discount));
//            mTvCobone.setText(getIntent().getStringExtra(Constants.KEY_EXTRA_COURSE_HAS_PROMOCODE));
//
//        } else { // no discount
//            mTvPrice.setVisibility(View.GONE);
//            mTvDiscount.setVisibility(View.GONE);
//            mTvCobone.setVisibility(View.GONE);
//        }
//
//        if (type.equals("free")) {
//            mTvPriceAfterDiscount.setText(getString(R.string.free));
//        } else {
//            mTvPriceAfterDiscount.setText(String.format(getString(R.string.currency), String.valueOf(finalPrice)));
//        }
//
//        TagsAdapter tagsAdapter = new TagsAdapter(this, color);
//        tagsAdapter.setList(tags);
//        mRcTags.setAdapter(tagsAdapter);
//
//    }
//
//    @Override
//    public void showProgress(final boolean show) {
//
//        //showProgressBar(progressBar, show);
//    }
//
//    @Override
//    public void showError(String error) {
//        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void openPaymentPage(String url) {
//
//        Intent intent = new Intent(this, PaymentActivity.class);
//        intent.putExtras(getIntent());
//        startActivity(intent);
//    }
//
//    @Override
//    public void openSuccessPage() {
//        FragmentManager fm = getSupportFragmentManager();
//        PaymentDoneDialog subscribeDialog = PaymentDoneDialog.newInstance("Baims");
//
//        Bundle bundle = new Bundle();
//        bundle.putAll(getIntent().getExtras());
////        bundle.putParcelableArrayList(Constants.KEY_TAGS, (ArrayList<? extends Parcelable>) tags);
////        bundle.putString(Constants.KEY_COLOR, color);
////        bundle.putString(Constants.KEY_EXTRA_COURSE_NAME, course.getName());
////        bundle.putString(Constants.KEY_EXTRA_COURSE_PRICE, course.getPrice());
////        bundle.putBoolean(Constants.KEY_EXTRA_COURSE_HAS_PROMOCODE, course.hasPromoCode());
////        bundle.putString(Constants.KEY_EXTRA_COURSE_LINK, course.getLink());
//        subscribeDialog.setArguments(bundle);
//
//        subscribeDialog.show(fm, "Baims");
//    }
////
////    @Override
////    public void onPaymentSuccess() {
////        FragmentManager fm = getSupportFragmentManager();
////        PaymentDoneDialog paymentDoneDialog = PaymentDoneDialog.newInstance("Baims");
////
////        Bundle bundle = new Bundle();
////        bundle.putParcelableArrayList(Constants.KEY_TAGS, getIntent().getExtras().getParcelableArrayList(Constants.KEY_TAGS));
////        bundle.putString(Constants.KEY_COLOR, getIntent().getStringExtra(Constants.KEY_COLOR));
////        bundle.putString(Constants.KEY_EXTRA_PAYMENT_FINAL, getIntent().getStringExtra(Constants.KEY_EXTRA_PAYMENT_FINAL));
////
//////        bundle.putBoolean(Constants.KEY_EXTRA_COURSE_HAS_PROMOCODE, course.hasPromoCode());
//////        bundle.putString(Constants.KEY_EXTRA_COURSE_LINK, course.getLink());
//////        subscribeDialog.setArguments(bundle);
////
////        paymentDoneDialog.show(fm, "Baims");
////    }
//
//    @OnClick(R.id.btn_subscribe)
//    public void onSubscribeClick() {
//        presenter.onSubscribeClick(type);
//    }
//
//    @OnClick(R.id.iv_close)
//    public void onCloseClick() {
//        onBackPressed();
//    }
//}
