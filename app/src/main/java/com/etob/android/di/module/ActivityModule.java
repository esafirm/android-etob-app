package com.etob.android.di.module;

import android.app.Activity;
import android.content.Context;
import com.etob.android.di.ActivityContext;
import dagger.Module;
import dagger.Provides;

@Module public class ActivityModule {

  private Activity mActivity;

  public ActivityModule(Activity activity) {
    mActivity = activity;
  }

  @Provides Activity provideActivity() {
    return mActivity;
  }

  @Provides @ActivityContext Context providesContext() {
    return mActivity;
  }
}
