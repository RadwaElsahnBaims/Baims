//package com.baims.app.presentation.register.email;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.baims.app.R;
//import com.baims.app.presentation.common.Constants;
//
//import com.baims.app.presentation.utils.Validation;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import butterknife.OnTextChanged;
//
//public class EnterEmailActivity extends AppCompatActivity {
//
//    @BindView(R.id.btn_next)
//    Button mBtnNext;
//
//    @BindView(R.id.edt_email)
//    EditText mEdtEmail;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_enter_email);
//
//        ButterKnife.bind(this);
//    }
//
//    @OnTextChanged(R.id.edt_email)
//    public void onNameTextChanged() {
//        if (!mEdtEmail.getText().toString().isEmpty()) {
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
//        if (Validation.isValidEmail(mEdtEmail, this)) {
//            Intent intent = new Intent(this, EnterPasswordActivity.class);
//
//            Bundle extras = getIntent().getExtras();
//            extras.putString(Constants.EMAIL_KEY, mEdtEmail.getText().toString());
//
//            intent.putExtras(extras);
//
//            startActivity(intent);
//        }
//    }
//}
