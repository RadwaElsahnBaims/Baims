//package com.baims.app.presentation.register.number;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.baims.app.R;
//import com.baims.app.presentation.common.Constants;
//import com.baims.app.presentation.register.email.EnterEmailActivity;
//import com.baims.app.presentation.utils.Validation;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import butterknife.OnTextChanged;
//
//public class EnterMobileActivity extends AppCompatActivity {
//    @BindView(R.id.btn_next)
//    Button mBtnNext;
//
//    @BindView(R.id.edt_country_code)
//    EditText mEdtCountryCode;
//
//    @BindView(R.id.edt_mobile)
//    EditText mEdtMobile;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_enter_mobile);
//
//        ButterKnife.bind(this);
//
//    }
//
//    @OnTextChanged({R.id.edt_mobile, R.id.edt_country_code})
//    public void onNameTextChanged() {
//        if (!mEdtCountryCode.getText().toString().isEmpty() && !mEdtMobile.getText().toString().isEmpty()) {
//            mBtnNext.setEnabled(true);
//
//        } else {
//            mBtnNext.setEnabled(false);
//        }
//    }
//
//    @OnClick(R.id.btn_next)
//    public void onNextClick() {
//        if (Validation.isValidMobileNumber(mEdtMobile, this)) {
//            Intent intent = new Intent(this, EnterEmailActivity.class);
//
//            Bundle extras = getIntent().getExtras();
//            extras.putString(Constants.PHONE_KEY, mEdtMobile.getText().toString());
//            extras.putString(Constants.COUNTRY_CODE_KEY, mEdtCountryCode.getText().toString());
//            Log.i("mobile" ,mEdtMobile.getText().toString());
//            Log.i("cc" ,mEdtCountryCode.getText().toString());
//
//            intent.putExtras(extras);
//
//            startActivity(intent);
//        }
//    }
//}
