package com.example.vivekbhalodiya.railticket;

import android.app.Application;
import timber.log.Timber;

/**
 * Created by vivekbhalodiya on 2/7/18.
 */

public class BaseApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }
}
