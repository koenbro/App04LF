package com.koenbro.android.app04listview;

import android.app.Application;
import android.content.Context;

/**
 * This class gets the application context outside of an activity (inside of an activity one may
 * use getApplicationContext()).  This class has to be registered in the manifest.
 * <p>
 * The getApplicationContext() method return the context of the single, global Application object
 * of the current process. This generally should only be used if you need a Context whose lifecycle
 * is separate from the current context, that is tied to the lifetime of the process rather than the
 * current component.
 * <br>
 * <a href="http://www.myandroidsolutions.com/2013/04/27/android-get-application-context">Source
 * </a>. See also <a href="http://stackoverflow.com/questions/708012/how-to-declare-global-variables-in-android/708317#708317">How to declare global variables?</a> and Addev's answer to <a href="http://stackoverflow.com/questions/6589797/how-to-get-package-name-from-anywhere">this question</a>.
 *
 * @author laszlo
 * @version 2/25/15
 */


public class ApplicationContextProvider extends Application {
    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }

}