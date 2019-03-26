package com.baims.app.presentation.payment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baims.app.R;
import com.baims.app.data.repositories.BaimsRemoteDataSource;
import com.baims.app.data.repositories.BaimsRepository;
import com.baims.app.data.repository.BaimsCacheDataSource;
import com.baims.app.data.utils.schedulers.SchedulerProvider;
import com.baims.app.presentation.common.BaseActivity;
import com.baims.app.presentation.common.Constants;
import com.baims.app.presentation.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity implements PaymentView {

    @BindView(R.id.webView)
    WebView mWebView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.tv_loading)
    TextView mTvLoading;
    String url;
    PaymentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ButterKnife.bind(this);

        presenter = new PaymentPresenter(this, SchedulerProvider.getInstance(), BaimsRepository.Companion.getInstance(BaimsRemoteDataSource.Companion.getInstance(), BaimsCacheDataSource.Companion.getInstance(this)));
        url = getIntent().getStringExtra(Constants.KEY_PAYMENT_URL);
        setWebView();
    }

    @Override
    public void showProgress(final boolean show) {
        showProgressBar(progressBar, show);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPaymentSuccess() {

        FragmentManager fm = getSupportFragmentManager();
        PaymentDoneDialog paymentDoneDialog = PaymentDoneDialog.newInstance("Baims");

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.KEY_TAGS, getIntent().getExtras().getParcelableArrayList(Constants.KEY_TAGS));
        bundle.putString(Constants.KEY_COLOR, getIntent().getStringExtra(Constants.KEY_COLOR));
        bundle.putDouble(Constants.KEY_EXTRA_PAYMENT_FINAL, getIntent().getDoubleExtra(Constants.KEY_EXTRA_PAYMENT_FINAL, 0));
        bundle.putDouble(Constants.KEY_EXTRA_COURSE_PRICE, getIntent().getDoubleExtra(Constants.KEY_EXTRA_COURSE_PRICE, 0));
        bundle.putString(Constants.KEY_SECTION_NAME, getIntent().getStringExtra(Constants.KEY_SECTION_NAME));
//        bundle.putBoolean(Constants.KEY_EXTRA_COURSE_HAS_PROMOCODE, course.hasPromoCode());
//        bundle.putString(Constants.KEY_EXTRA_COURSE_LINK, course.getLink());
//        subscribeDialog.setArguments(bundle);
        paymentDoneDialog.setArguments(bundle);
        paymentDoneDialog.show(fm, "Baims");

        Utils.hideKeyboard(this);
        mWebView.setVisibility(View.INVISIBLE);


    }

    @Override
    public void onPaymentError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.iv_close)
    public void onCloseClick() {
        onBackPressed();
    }

    protected void showProgressBar(ProgressBar progressBar, boolean show) {
        if (progressBar != null)
            if (show) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
    }


    void setWebView() {

        WebSettings settings = mWebView.getSettings();
        settings.setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.loadUrl(url);
        mWebView.getSettings().setBuiltInZoomControls(true);

        Log.i("redirectionUrl", url);
        mWebView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);
                Log.d("webview", "onReceivedClientCertRequest");
            }


            @Override
            public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
                super.onUnhandledKeyEvent(view, event);
                Log.e("event", event.toString());
                Log.e("view", view.getOriginalUrl());

            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                try {
                    Log.e("view", view.getOriginalUrl());
                    Log.e("failingUrl : ", failingUrl);
                    Log.e("description : ", description);
                    Log.e("errorCode : ", errorCode + "");
                    //errorCall(view.getUrl());
                } catch (Exception e) {
                    e.printStackTrace();
                    //errorCall(view.getUrl());
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  mTvLoading.setVisibility(View.VISIBLE);
                showProgress(true);

                Log.e("shouldOverrideUrl", "url : " + url);

                //http://beta.baims.com/api/paymentcallback/Chem1Chap2/Chemistry_101_-_Chapter_2aQZ6hPdK?paymentId=5480525411883310
                url = url.toLowerCase();
//                if (url.contains("result") || url.contains("Result")) {

//                }

                if (url.contains("paymentcallback")) {
                    //https://beta.baims.com/api/paymentcallback/Chem1Chap2/Chemistry_101_-_Chapter_2aQZ6hPdK?paymentId=6591060010183320
                    //https://beta.baims.com/api/v1/paymentcallback/Probability_Chapter_91543852562/41789c64838aa6e8b8c499?paymentId=4081481141490270&Id=4081481141490270
                    String[] str = url.split("/");
                    String link = str[6];//"Chem1Chap2";
                    String orderLink = str[7].split("\\?")[0];//"Chemistry_101_-_Chapter_2aQZ6hPdK";
                    String paymentId = url.split("paymentid=")[1].split("&id=")[0]; //"5480525411883310";
                    try {
                        //String paymentId = url.substring(url.indexOf("paymentId="));
                        presenter.confirmPayment(link, orderLink, paymentId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (url.contains("error.jsp") || url.contains("error.php")
                        || url.contains("vpc_Message".toLowerCase())) {

                    try {
                        Log.i("error", "url");
                        onPaymentError(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    onPageFinished(view, url);
                } else {

                    mWebView.loadUrl("javascript:HtmlViewer.showHTML" +
                            "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                }

                // return true; //Indicates WebView to NOT load the url;
                return false; //Allow WebView to load url
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showProgress(false);
                mTvLoading.setVisibility(View.GONE);
                Log.d("URL ====== ", url);


                mWebView.loadUrl("javascript:window.HtmlViewer.showHTML" +
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");

            }


            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.d("Error", view.getUrl());
//                errorCall(view.getUrl());
            }
        });

    }

    class MyJavaScriptInterface {
        Context context;

        public MyJavaScriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            if (html.contains("title")) {
                String title = html.substring(html.indexOf("<title>"), html.indexOf("</title>"));
                if (title.contains("KNET Payment Error")) {

                    try {
                        // errorCall(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        }

    }

}
