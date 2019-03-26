package com.baims.app.presentation.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.data.entities.BundleCourse;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.common.BaseFragment;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.ListItemListener;
import com.baims.app.presentation.coursedetails.CourseDetailsActivity;
import com.baims.app.presentation.home.CoursesBundlesAdapter;
import com.baims.app.presentation.profile.ProfileFragment;
import com.baims.app.presentation.profile.ProfilePresenterImpl;
import com.baims.app.presentation.sectiondetails.SectionDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Radwa Elsahn on 12/17/2018.
 */
public class SearchFramgment extends BaseFragment implements SearchView {
    @BindView(R.id.rc_courses)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    CoursesBundlesAdapter adapter;
    SearchPresenter presenter;

    @BindView(R.id.edt_search)
    EditText mEdtSearch;

    @BindView(R.id.group_results)
    Group groupResults;

    @BindView(R.id.tv_no_results)
    TextView mTvNoResult;

    public static SearchFramgment newInstance() {
        SearchFramgment fragment = new SearchFramgment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SearchPresenterImpl(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(getContext())));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CoursesBundlesAdapter(getContext());
        adapter.setOnClickListener(new ListItemListener<BundleCourse>() {
            @Override
            public void itemListener(BundleCourse item) {
                if (item.getTypeFeat().equals(Constants.COURSE_KEY)) {
                    Intent intent = new Intent(getContext(), CourseDetailsActivity.class);
                    intent.putExtra(Constants.KEY_EXTRA_COURSE_LINK, item.getLink());
                    intent.putExtra(Constants.KEY_SECTION_NAME, item.getSectionName());
                    intent.putParcelableArrayListExtra(Constants.KEY_TAGS, (ArrayList<? extends Parcelable>) item.getChapterTag());
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

        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


                    presenter.getSearchResults(mEdtSearch.getText().toString());//"math");

                    return true;
                }
                return false;
            }
        });

    }


    @Override
    public void showData(List<BundleCourse> courses) {
        Log.e("list", courses.size() + "");

        adapter.setList(courses);
        groupResults.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(adapter);
        mTvNoResult.setVisibility(View.GONE);
    }

    @Override
    public void showProgress(final boolean show) {
        showProgressBar(progressBar, show, getActivity());
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), getString(R.string.no_results), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNoResult() {
        mTvNoResult.setVisibility(View.VISIBLE);
    }
}
