package com.baims.app;

import android.app.DownloadManager;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;

import com.baims.app.presentation.utils.LocaleHelper;
import com.baims.app.presentation.utils.LocaleUtils;
import java.io.File;
import java.util.Locale;

/**
 * Created by Radwa Elsahn on 12/7/2018.
 */
public class App extends MultiDexApplication {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "ar"));
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        userAgent = Util.getUserAgent(this, "ExoPlayerDemo");

        Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        // Create a new configuration object
        Configuration config = new Configuration();
        // Set the locale of the new configuration
        config.locale = locale;
        // Update the configuration of the Accplication context
        getResources().updateConfiguration(
                config,
                getResources().getDisplayMetrics()
        );
//        LocaleUtils.setLocale(new Locale("ar"));
//        LocaleUtils.updateConfig(this, getBaseContext().getResources()
//                .getConfiguration());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

            LocaleUtils.setLocale(new Locale("ar"));
            LocaleUtils.updateConfig(this, getBaseContext().getResources()
                    .getConfiguration());

    }

}
