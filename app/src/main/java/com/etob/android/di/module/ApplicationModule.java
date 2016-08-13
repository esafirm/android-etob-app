package com.etob.android.di.module;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import com.etob.android.di.ApplicationContext;

/**
 * Provide application-level dependencies.
 */
@Module public class ApplicationModule {
  protected final Application mApplication;

  public ApplicationModule(Application application) {
    mApplication = application;
  }

  @Provides Application provideApplication() {
    return mApplication;
  }

  @Provides @ApplicationContext Context provideContext() {
    return mApplication;
  }
}
