package com.baims.app.presentation.categories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.data.entities.Category;
import com.baims.app.data.entities.SubCategory;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.common.BaseFragment;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.ListItemListener;
import com.baims.app.presentation.payment.PaymentDoneDialog;
import com.baims.app.presentation.subcategories.SubcategoriesDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Radwa Elsahn on 12/17/2018.
 */
public class CategoriesFragment extends BaseFragment implements CategoriesView {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    CategoriesPresenter presenter;

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new CategoriesPresenter(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(getContext())));
        presenter.getCategories();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void showCategories(List<Category> categories) {
        CategoriesAdapter adapter = new CategoriesAdapter(getContext());
        adapter.setList(categories);
        adapter.setOnClickListener(new ListItemListener<Category>() {
            @Override
            public void itemListener(Category category) {
                Log.i("linkclicked", category.getLink());

//                Intent intent = new Intent(getActivity(), SubcategoryActivity.class);
//                intent.putParcelableArrayListExtra("subcategories", (ArrayList<? extends Parcelable>) category.getSubcategories());
//                intent.putExtra("categoryName",category.getName());
//                intent.putExtra("categoryLink",category.getLink());
//                startActivity(intent);


                FragmentManager fm = getActivity().getSupportFragmentManager();
                SubcategoriesDialog dialog = SubcategoriesDialog.newInstance("Baims",
                        (ArrayList<SubCategory>) category.getSubcategories(),
                        category.getName(),category.getLink());

//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("subcategories", (ArrayList<? extends Parcelable>) category.getSubcategories());
//                bundle.putString("categoryName", category.getName());
//                bundle.putString("categoryLink",category.getLink());
//
//                dialog.setArguments(bundle);
                dialog.show(fm, "Baims");

            }
        });

        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void showProgress(final boolean show) {
        showProgressBar(progressBar, show, getActivity());
    }

    @Override
    public void showError(String error) {
        showDialog(getActivity(),error);
    }


}
