package com.baims.app.presentation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.data.api.RestClient;
import com.baims.app.data.entities.Course;
import com.baims.app.data.entities.User;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.common.BaseFragment;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.ListItemListener;
import com.baims.app.presentation.coursedetails.CourseDetailsActivity;
import com.baims.app.presentation.courses.CoursesAdapter;
import com.baims.app.presentation.login.LoginPromptFragment;
import com.baims.app.presentation.utils.ActivityUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Radwa Elsahn on 12/17/2018.
 */
public class ProfileFragment extends BaseFragment implements ProfileView {
    @BindView(R.id.rc_sections)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_mail)
    TextView mTvMail;

    @BindView(R.id.tv_name)
    TextView mTvName;

    @BindView(R.id.tv_number)
    TextView mTvNumber;

    @BindView(R.id.tv_watches)
    TextView mTvWatches;

    @BindView(R.id.tv_courses_number)
    TextView mTvCourses;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_no_courses)
    TextView mTvNoCourses;

    @BindView(R.id.image_profile)
    ImageView imageProfile;

    ProfilePresenter presenter;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_TARGET, target);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ProfilePresenterImpl(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(getContext())));
        presenter.getUserProfile();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showProfileInfo(User user) {
        mTvMail.setText(user.getEmail());
        mTvName.setText(user.getDisplay_name());
        mTvNumber.setText(user.getPhone());
        mTvWatches.setText(user.getNum_watched());

        Glide.with(this)
                  .load(RestClient.ROOT + user.getImage())
//                .load("http://beta.baims.com/images/letterAEName/r_copy.png")
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(Constants.roundCurve)))
                .into(imageProfile);


    }

    @Override
    public void showProgress(final boolean show) {
        showProgressBar(progressBar, show, getActivity());
    }

    @Override
    public void showError(String error) {
        showDialog(getActivity(), error);
    }

    @Override
    public void showNoCourses() {
        mTvNoCourses.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCourses(List<Course> myCourses, List<Course> registeredCourses, String sectionName) {
        mTvNoCourses.setVisibility(View.GONE);
        mTvCourses.setText(registeredCourses != null ? registeredCourses.size() + "" : "0");
        CoursesAdapter adapter = new CoursesAdapter(getContext());
        adapter.setList(registeredCourses);
        adapter.setOnClickListener(new ListItemListener<Course>() {
            @Override
            public void itemListener(Course item) {

                Intent intent = new Intent(getActivity(), CourseDetailsActivity.class);
                intent.putExtra(Constants.KEY_EXTRA_COURSE_LINK, item.getLink());
                intent.putExtra(Constants.KEY_SECTION_NAME, item.getSectionName());
//                intent.putParcelableArrayListExtra(Constants.KEY_TAGS, (ArrayList<? extends Parcelable>) item.getChapterTag());
                //intent.putExtra(Constants.KEY_COLOR, item.getColor());
                startActivity(intent);

            }
        });

        mRecyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.tv_logout)
    public void logoutClick() {
        presenter.logout();

        ActivityUtil.replaceFragmentToActivity(getActivity().getSupportFragmentManager(),
                LoginPromptFragment.newInstance(), R.id.content, getActivity());

    }

}
