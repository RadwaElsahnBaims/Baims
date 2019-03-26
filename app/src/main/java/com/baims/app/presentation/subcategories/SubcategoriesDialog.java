package com.baims.app.presentation.subcategories;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baims.app.R;
import com.baims.app.data.entities.ChapterTag;
import com.baims.app.data.entities.SubCategory;
import com.baims.app.presentation.categorycourses.CategoryCoursesActivity;
import com.baims.app.presentation.categorycourses.CategoryCoursesPresenter;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.common.ListItemListener;
import com.baims.app.presentation.sectiondetails.TagsAdapter;
import com.baims.app.presentation.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Radwa Elsahn on 10/28/2018.
 */
public class SubcategoriesDialog extends DialogFragment {

    CategoryCoursesPresenter presenter;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    List<SubCategory> subCategories;
    String link;
    String category;

    private Unbinder unbinder;

    @Override
    public void onStart() {
        super.onStart();

//        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//
//        Window window = getDialog().getWindow();
//        window.setAttributes(lp);
    }

    public SubcategoriesDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    public static SubcategoriesDialog newInstance(String title, ArrayList<SubCategory> subcategories, String categoryName, String categoryLink) {
        SubcategoriesDialog frag = new SubcategoriesDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putParcelableArrayList("subcategories", subcategories);
        args.putString("categoryName", categoryName);
        args.putString("categoryLink", categoryLink);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_subcategory, container, false);
        unbinder = ButterKnife.bind(this, v);

      //  getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        subCategories = getArguments().getParcelableArrayList("subcategories");
        category = getArguments().getString("categoryName");
        link = getArguments().getString("categoryLink");

        mTvTitle.setText(category);
        showSubCategories(subCategories);
    }

    public void showSubCategories(List<SubCategory> subCategories) {
        SubcategoriesAdapter adapter = new SubcategoriesAdapter(getActivity());
        adapter.setList(subCategories);
        adapter.setOnClickListener(new ListItemListener<SubCategory>() {
            @Override
            public void itemListener(SubCategory subCategory) {
                Log.i("linkclicked", subCategory.getName());

                Intent intent = new Intent(getActivity(), CategoryCoursesActivity.class);
                intent.putExtra("courseLink", subCategory.getLink());//link);
                intent.putExtra("categoryName", category);
                intent.putExtra("subCategoryName", subCategory.getName());
                startActivity(intent);
            }
        });

        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_close)
    public void onCloseClick() {
        dismiss();
    }

}
