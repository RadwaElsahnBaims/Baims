//package com.baims.app.presentation.register.name;
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
//public class YourNameActivity extends AppCompatActivity {
//
//    @BindView(R.id.edt_name)
//    EditText mEdtName;
//
//    @BindView(R.id.btn_next)
//    Button mBtnNext;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_your_name);
//        ButterKnife.bind(this);
//
//    }
//
//    @OnTextChanged(R.id.edt_name)
//    public void onNameTextChanged() {
//        if (!mEdtName.getText().toString().isEmpty()) {
//            mBtnNext.setEnabled(true);
//
//        } else {
//            mBtnNext.setEnabled(false);
//        }
//    }
//
//    @OnClick(R.id.btn_next)
//    public void onNextClick() {
//        if (Validation.isValidString(mEdtName, getString(R.string.require_field))) {
//            Intent intent = new Intent(this, EnterMobileActivity.class);
//            intent.putExtra(Constants.NAME_KEY, mEdtName.getText().toString());
//            startActivity(intent);
//        }
//    }
//}
