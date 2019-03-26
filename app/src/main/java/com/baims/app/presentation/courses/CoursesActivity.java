package com.baims.app.presentation.courses;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.data.entities.Course;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.common.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoursesActivity extends BaseActivity implements CoursesView {

    @BindView(R.id.rc_courses)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    CoursesAdapter adapter;
    CoursesPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        adapter = new CoursesAdapter(this);
        recyclerView.setAdapter(adapter);
        presenter = new CoursesPresenterImpl(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(this)));
        presenter.getCourses();

        initToolbar(toolbar, "Baims");

    }

    @Override
    public void showData(List<Course> courses) {
        Log.e("list", courses.size() + "");
        adapter.setList(courses);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showProgress(final boolean show) {
        showProgressBar(progressBar, show, this);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
