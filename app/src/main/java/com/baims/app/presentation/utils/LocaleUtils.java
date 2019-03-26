package com.baims.app.presentation.utils;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.ContextThemeWrapper;

import java.util.Locale;

/**
 * Created by vezikon on 4/22/17.
 */

public class LocaleUtils {
    private static Locale sLocale;

    public static void setLocale(Locale locale) {
        sLocale = locale;
        if (sLocale != null) {
            Locale.setDefault(sLocale);
        }
    }

    public static void updateConfig(ContextThemeWrapper wrapper) {
        if (sLocale != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Configuration configuration = new Configuration();
            configuration.setLocale(sLocale);
            wrapper.applyOverrideConfiguration(configuration);
        }
    }

    public static void updateConfig(Activity activity, Configuration configuration) {
        if (sLocale != null) {
            //Wrapping the configuration to avoid Activity endless loop
            Configuration config = new Configuration(configuration);
            config.locale = sLocale;
            config.setLayoutDirection(sLocale);
            Resources res = activity.getBaseContext().getResources();
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
    }

    public static void updateConfig(Application application, Configuration configuration) {
        if (sLocale != null) {
            //Wrapping the configuration to avoid Activity endless loop
            Configuration config = new Configuration(configuration);
            config.locale = sLocale;
            config.setLayoutDirection(sLocale);
            Resources res = application.getBaseContext().getResources();
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
    }
}
