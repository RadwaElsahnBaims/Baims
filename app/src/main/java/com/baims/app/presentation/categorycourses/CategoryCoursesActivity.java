package com.baims.app.presentation.categorycourses;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.data.entities.BundleCourse;
import com.baims.app.data.entities.Category;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.categories.CategoriesPresenter;
import com.baims.app.presentation.common.BaseActivity;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.ListItemListener;
import com.baims.app.presentation.coursedetails.CourseDetailsActivity;
import com.baims.app.presentation.home.CoursesBundlesAdapter;
import com.baims.app.presentation.sectiondetails.SectionDetailsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryCoursesActivity extends BaseActivity implements CategoryCoursesView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.tv_subcategory)
    TextView tvSubCategory;

    @BindView(R.id.tv_category)
    TextView tvCategory;

    @BindView(R.id.tv_no_results)
    TextView tvNoData;

    CategoryCoursesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_courses);
        ButterKnife.bind(this);

        tvCategory.setText(getIntent().getStringExtra("categoryName"));
        tvSubCategory.setText(getIntent().getStringExtra("subCategoryName"));

        presenter = new CategoryCoursesPresenter(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(this)));
        Log.i("courseradwa", getIntent().getStringExtra("courseLink"));
        presenter.getCategory(getIntent().getStringExtra("courseLink"));

    }

    @Override
    public void showCategoryCourses(Category category) {
        CoursesBundlesAdapter adapter = new CoursesBundlesAdapter(this);
        adapter.setOnClickListener(new ListItemListener<BundleCourse>() {
            @Override
            public void itemListener(BundleCourse item) {
                if (item.getTypeFeat().equals(Constants.COURSE_KEY)) {
                    Intent intent = new Intent(CategoryCoursesActivity.this, CourseDetailsActivity.class);
                    intent.putExtra(Constants.KEY_EXTRA_COURSE_LINK, item.getLink());
                    intent.putExtra(Constants.KEY_SECTION_NAME, item.getSectionName());
                    //if(item.getChapterTag()!=null)
                    intent.putParcelableArrayListExtra(Constants.KEY_TAGS, (ArrayList<? extends Parcelable>) item.getChapterTag());
                    intent.putExtra(Constants.KEY_COLOR, item.getColor());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CategoryCoursesActivity.this, SectionDetailsActivity.class);
                    intent.putExtra(Constants.KEY_TYPE_FEAT, item.getTypeFeat());
                    intent.putExtra(Constants.KEY_BUNDLE_LINK, item.getLink());
                    intent.putExtra(Constants.KEY_BUNDLE_NAME, item.getName());
                    intent.putExtra(Constants.KEY_BUNDLE_IMAGE, item.getImage());
                    intent.putExtra(Constants.KEY_SECTION_NAME, item.getSectionName());
                    startActivity(intent);
                }
            }
        });

        adapter.setList(category.getBundleCourses());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgress(boolean show) {
        showProgressBar(progressBar, show, this);
    }

    @Override
    public void showError(String error) {
        showDialog(this, error);
    }

    @Override
    public void showNoResult() {

        recyclerView.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);
    }
}
