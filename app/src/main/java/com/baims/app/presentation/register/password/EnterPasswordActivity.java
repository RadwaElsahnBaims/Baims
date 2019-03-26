//package com.baims.app.presentation.register.password;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.baims.app.R;
//import com.baims.app.presentation.common.Constants;
//import com.baims.app.presentation.utils.Validation;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import butterknife.OnTextChanged;
//
//public class EnterPasswordActivity extends AppCompatActivity {
//
//    @BindView(R.id.btn_next)
//    Button mBtnNext;
//
//    @BindView(R.id.edt_password)
//    EditText mEdtPassword;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_enter_password);
//
//        getIntent().getExtras().get(Constants.PHONE_KEY);
//        ButterKnife.bind(this);
//
//    }
//
//    @OnTextChanged(R.id.edt_password)
//    public void onNameTextChanged() {
//        if (!mEdtPassword.getText().toString().isEmpty()) {
//            mBtnNext.setEnabled(true);
//
//        } else {
//            mBtnNext.setEnabled(false);
//        }
//    }
//
//    @OnClick(R.id.btn_next)
//    public void onNextClick() {
//
//        if (Validation.isValidPassword(6, mEdtPassword, this)) {
//            Intent intent = new Intent(this, ConfirmAccountActivity.class);
//
//            Bundle extras = getIntent().getExtras();
//            extras.putString(Constants.PASSWORD_KEY, mEdtPassword.getText().toString());
//
//            intent.putExtras(extras);
//
//            startActivity(intent);
//        }
//    }
//}
