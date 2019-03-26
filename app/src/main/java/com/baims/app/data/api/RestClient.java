package com.baims.app.data.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.baims.app.data.entities.response.ErrorBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Radwa Elsahn on 10/14/2018.
 */
public class RestClient {

    public static String ROOT;
    private static final String BETA_URL = "https://beta.baims.com/";
    private static final String LIVE_URL = "https://baims.com/";
    private static Api API;
    private static Retrofit retrofit;
    static Context context;

    static {
        ROOT = BETA_URL;
        setupRestClient();
    }

    private RestClient() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static Api get() {
        return API;
    }

    private static void setupRestClient() {
        Gson gson = new GsonBuilder().create();

        HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(logLevel);

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(100L, TimeUnit.SECONDS)
                .connectTimeout(100L, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .writeTimeout(100L, TimeUnit.SECONDS)
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);

                        if (response.code() == 400) {
                            ErrorBody errorBody = new Gson().fromJson(response.body().string(), ErrorBody.class);
                            Log.i("error: ", errorBody.getMessage());
//                            if (context != null)
//                                Toast.makeText(context, errorBody.getMessage(), Toast.LENGTH_SHORT).show();
                            return response;
                        }

                        return response;
                    }
                })
                .build();

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ROOT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        API = retrofit.create(Api.class);

    }

}
