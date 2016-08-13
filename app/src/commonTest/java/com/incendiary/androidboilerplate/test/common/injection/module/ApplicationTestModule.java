package com.incendiary.androidboilerplate.test.common.injection.module;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import com.etob.android.data.DataManager;
import com.etob.android.data.remote.ApiService;
import com.etob.android.di.ApplicationContext;

import static org.mockito.Mockito.mock;

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module public class ApplicationTestModule {

  private final Application mApplication;

  public ApplicationTestModule(Application application) {
    mApplication = application;
  }

  @Provides Application provideApplication() {
    return mApplication;
  }

  @Provides @ApplicationContext Context provideContext() {
    return mApplication;
  }

  /************* MOCKS *************/

  @Provides @Singleton DataManager provideDataManager() {
    return mock(DataManager.class);
  }

  @Provides @Singleton ApiService provideRibotsService() {
    return mock(ApiService.class);
  }
}
