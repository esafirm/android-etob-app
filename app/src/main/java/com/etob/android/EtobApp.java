package com.etob.android;

import android.app.Application;
import com.etob.android.data.local.Preferences;
import com.etob.android.di.component.ApplicationComponent;
import com.etob.android.di.component.DaggerApplicationComponent;
import com.etob.android.di.module.ApplicationModule;
import com.incendiary.androidcommon.AndroidCommon;
import com.incendiary.androidcommon.android.ContextProvider;
import rx_activity_result.RxActivityResult;
import timber.log.Timber;

public class EtobApp extends Application {

  ApplicationComponent mApplicationComponent;

  @Override public void onCreate() {
    super.onCreate();
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }

    setupStorage();
    setupAndroidCommon();
    setupRxActivityResult();
  }

  private void setupRxActivityResult() {
    RxActivityResult.register(this);
  }

  private void setupStorage() {
    Preferences.setup(this);
  }

  private void setupAndroidCommon() {
    boolean isDebug = BuildConfig.DEBUG;
    AndroidCommon.with(this).enableStricMode(isDebug).install();
  }

  /* --------------------------------------------------- */
  /* > AppComponent */
  /* --------------------------------------------------- */

  public static EtobApp get() {
    return (EtobApp) ContextProvider.get();
  }

  public static ApplicationComponent component() {
    return get().getComponent();
  }

  private ApplicationComponent getComponent() {
    if (mApplicationComponent == null) {
      mApplicationComponent = DaggerApplicationComponent.builder()
          .applicationModule(new ApplicationModule(this))
          .build();
    }
    return mApplicationComponent;
  }

  // Needed to replace the component with a test specific one
  public void setComponent(ApplicationComponent applicationComponent) {
    mApplicationComponent = applicationComponent;
  }
}
