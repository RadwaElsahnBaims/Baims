package com.baims.app.presentation.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.data.entities.BundleCourse;
import com.baims.app.data.entities.Section;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.common.BaseFragment;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.ListItemListener;
import com.baims.app.presentation.coursedetails.CourseDetailsActivity;
import com.baims.app.presentation.sectiondetails.SectionDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Radwa Elsahn on 12/17/2018.
 */
public class FragmentHome extends BaseFragment implements HomeView{

    private static final String ARG_TARGET = "";
    @BindView(R.id.rc_sections)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.tv_university)
    TextView mTvUniversity;

    @BindView(R.id.tv_secondary)
    TextView mTvSecondary;

    HomeItemAdapter adapter;
    HomePresenter presenter;

    public static FragmentHome newInstance() {
        FragmentHome fragment = new FragmentHome();
//        Bundle args = new Bundle();
//        args.putInt(ARG_TARGET, target);
//        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new HomePresenterImpl(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(getContext())));


    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getFeatured(Constants.UNIVERSITY_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new HomeItemAdapter(getContext(), this);
        adapter.setOnClickListener(new ListItemListener<BundleCourse>() {
            @Override
            public void itemListener(BundleCourse item) {

                if (item.getTypeFeat().equals(Constants.COURSE_KEY)) {
                    Intent intent = new Intent(getActivity(), CourseDetailsActivity.class);
                    intent.putExtra(Constants.KEY_EXTRA_COURSE_LINK, item.getLink());
                    intent.putExtra(Constants.KEY_SECTION_NAME, item.getSectionName());
                    //intent.putParcelableArrayListExtra(Constants.KEY_TAGS, (ArrayList<? extends Parcelable>) item.getChapterTag());
                    intent.putExtra(Constants.KEY_COLOR, item.getColor());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), SectionDetailsActivity.class);
                    intent.putExtra(Constants.KEY_TYPE_FEAT, item.getTypeFeat());
                    intent.putExtra(Constants.KEY_BUNDLE_LINK, item.getLink());
                    intent.putExtra(Constants.KEY_BUNDLE_NAME, item.getName());
                    intent.putExtra(Constants.KEY_BUNDLE_IMAGE, item.getImage());
                    intent.putExtra(Constants.KEY_SECTION_NAME, item.getSectionName());
                    startActivity(intent);
                }

            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showData(List<Section> sections) {
        Log.e("list", sections.size() + "");
        adapter.setList(sections);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showProgress(final boolean show) {
        showProgressBar(progressBar, show,getActivity());
    }

    @Override
    public void showError(String error) {
        showDialog(getActivity(), error);
    }


    @OnClick(R.id.tv_secondary)
    public void onSecondaryClick() {
        Log.e("list", "onSecondaryClick");
        mTvUniversity.setBackground(null);
        mTvSecondary.setBackground(getResources().getDrawable(R.drawable.bottom_line));

        presenter.getFeatured(Constants.SECONDARY_KEY);
    }

    @OnClick(R.id.tv_university)
    public void onUniversityClick() {
        Log.e("list", "onUniversityClick");
        mTvSecondary.setBackground(null);
        mTvUniversity.setBackground(getResources().getDrawable(R.drawable.bottom_line));

        presenter.getFeatured(Constants.UNIVERSITY_KEY);
    }

}
