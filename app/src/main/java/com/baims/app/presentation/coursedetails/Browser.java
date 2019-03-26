package com.baims.app.presentation.coursedetails;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.baims.app.R;
import com.baims.app.presentation.common.BaseActivity;
import com.baims.app.presentation.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Hassan on 17/02/2016.
 * updated by Mahmoud Shawky 01/16/2018
 * In app browser to use this activity send the URL in intent
 */
public class Browser extends AppCompatActivity {

    String mUrlToLoad = "https://baims.com/uploads/video/watermarked_probability_3_2.pdf";
    @BindView(R.id.webView)
    WebView mWebView;
//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
    @BindView(R.id.loadingprogress)
    ProgressBar mLoadingProgress;

    @BindView(R.id.group_reload)
    Group mGroupReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);

//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url="+ mUrlToLoad);

        Bundle mExtras = getIntent().getExtras();
        if (mExtras != null) {
            mUrlToLoad = mExtras.getString("URL_TO_LOAD");
        }

        mWebView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.requestFocus();
        mWebView.requestFocusFromTouch();
        mWebView.setWebViewClient(new CustomWebViewClient());
        showUrlContent();

    }

    private void showUrlContent() {
        if (!Utils.isNetworkAvailable(this)) {
            mGroupReload.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
        } else {
            mWebView.setVisibility(View.VISIBLE);
            mGroupReload.setVisibility(View.GONE);
            loadUrl();
        }
    }

//    @Override
//    public void onBackPressed() {
//        //super.onBackPressed();
//        this.finish();
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.stopLoading();
        mWebView.loadUrl("about:blank");
    }

    private void loadUrl() {
//        mWebView.loadUrl(mUrlToLoad);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url="+ mUrlToLoad);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class CustomWebViewClient extends WebViewClient {

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mLoadingProgress.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mLoadingProgress.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            view.stopLoading();
        }
    }

    @OnClick(R.id.tv_tap_to_retry)
    public void onRetryClick() {
        showUrlContent();
    }
}

