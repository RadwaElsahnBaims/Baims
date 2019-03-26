package com.baims.app.presentation.lecture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baims.app.R;
import com.baims.app.data.entities.Lecture;
import com.baims.app.presentation.common.Constants;

public class LectureActivity extends AppCompatActivity {

    Lecture lecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        getIntent().getParcelableExtra(Constants.KEY_LECTURE);

    }
}
