package com.baims.app.presentation.sectiondetails;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.data.entities.BundleCourse;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.common.BaseActivity;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.ListItemListener;
import com.baims.app.presentation.coursedetails.CourseDetailsActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SectionDetailsActivity extends BaseActivity implements SectionDetailsView {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.rc_sections)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_section_name)
    TextView mTvSectionName;
    @BindView(R.id.tv_course_name)
    TextView mTvCourseName;
    @BindView(R.id.iv_image)
    ImageView mImage;

    SectionDetailsPresenter mPresenter;
    SectionDetailsCoursesAdapter adapter;

    String color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_details);
        ButterKnife.bind(this);

        adapter = new SectionDetailsCoursesAdapter(this, this);
        adapter.setOnClickListener(new ListItemListener<BundleCourse>() {
            @Override
            public void itemListener(BundleCourse item) {
                Intent intent = new Intent(SectionDetailsActivity.this, CourseDetailsActivity.class);
                intent.putExtra(Constants.KEY_EXTRA_COURSE_LINK, item.getLink());
                intent.putExtra(Constants.KEY_SECTION_NAME, getIntent().getStringExtra(Constants.KEY_BUNDLE_NAME));
                intent.putParcelableArrayListExtra(Constants.KEY_TAGS, (ArrayList<? extends Parcelable>) item.getChapterTag());
                intent.putExtra(Constants.KEY_COLOR, color);
                startActivity(intent);
            }
        });

        initUI();

//        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
//        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
//        mRecyclerView.addItemDecoration(itemDecorator);

        mRecyclerView.setAdapter(adapter);
        mPresenter = new SectionDetailsPresenterImpl(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(this)));
        mPresenter.getSectionDetails(getIntent().getStringExtra(Constants.KEY_BUNDLE_LINK));


    }

    private void initUI() {
        mTvSectionName.setText(getIntent().getStringExtra(Constants.KEY_BUNDLE_NAME));
        mTvCourseName.setText(getIntent().getStringExtra(Constants.KEY_SECTION_NAME));

        Glide.with(this)
                .load(getIntent().getStringExtra(Constants.KEY_BUNDLE_IMAGE))
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(20)))

                //.placeholder(R.drawable.default_placeholder)
                .into(mImage);

    }

    @Override
    public void showData(List<BundleCourse> courses, String color) {
        this.color = color;
        adapter.setColor(color);
        adapter.setList(courses);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void showProgress(final boolean show) {
        showProgressBar(progressBar, show, this);
    }

    @Override
    public void showError(String error) {
        showDialog(this, error);
    }

    @OnClick(R.id.iv_share)
    public void onShareClick() {
        logout();
    }

    @OnClick(R.id.iv_close)
    public void onCloseClick() {
        onBackPressed();
    }

}
