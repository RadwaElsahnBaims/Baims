//package com.baims.app.presentation.categories;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.baims.app.R;
//import com.baims.app.data.entities.Category;
//import com.baims.app.data.entities.Course;
//import com.baims.app.data.repositories.BaimsRemoteDataSource;
//import com.baims.app.data.repositories.BaimsRepository;
//import com.baims.app.data.repository.BaimsCacheDataSource;
//import com.baims.app.data.utils.schedulers.SchedulerProvider;
//import com.baims.app.presentation.common.BaseActivity;
//import com.baims.app.presentation.common.Constants;
//import com.baims.app.presentation.common.ListItemListener;
//import com.baims.app.presentation.coursedetails.CourseDetailsActivity;
//import com.baims.app.presentation.courses.CoursesAdapter;
//import com.baims.app.presentation.profile.ProfileActivity;
//import com.baims.app.presentation.profile.ProfilePresenterImpl;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class CategoriesActivity extends BaseActivity implements CategoriesView {
//    @BindView(R.id.recyclerView)
//    RecyclerView mRecyclerView;
//    @BindView(R.id.progress_bar)
//    ProgressBar progressBar;
//
//    CategoriesPresenter presenter;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_categories);
//        ButterKnife.bind(this);
//
//        presenter = new CategoriesPresenter(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(this)));
//        presenter.getCategories();
//    }
//
//    @Override
//    public void showCategories(List<Category> categories) {
//        CategoriesAdapter adapter = new CategoriesAdapter(this);
//        adapter.setList(categories);
//        adapter.setOnClickListener(new ListItemListener<Category>() {
//            @Override
//            public void itemListener(Category category) {
//
//            }
//        });
//
//        mRecyclerView.setVisibility(View.VISIBLE);
//        mRecyclerView.setAdapter(adapter);
//
//    }
//
//    @Override
//    public void showProgress(final boolean show) {
//        showProgressBar(progressBar, show, this);
//    }
//
//    @Override
//    public void showError(String error) {
//        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
//    }
//
//}
