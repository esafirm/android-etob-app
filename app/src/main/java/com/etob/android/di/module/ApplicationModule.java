package com.etob.android.di.module;

import android.app.Application;
import android.content.Context;
import com.etob.android.di.ApplicationContext;
import com.etob.android.managers.LocationManager;
import dagger.Module;
import dagger.Provides;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

/**
 * Provide application-level dependencies.
 */
@Module public class ApplicationModule {
  protected final Application app;

  public ApplicationModule(Application application) {
    app = application;
  }

  @Provides Application provideApplication() {
    return app;
  }

  @Provides ReactiveLocationProvider provideLocationProvider() {
    return new ReactiveLocationProvider(app);
  }

  @Provides LocationManager provideLocationManager(ReactiveLocationProvider provider){
    return new LocationManager(provider);
  }

  @Provides @ApplicationContext Context provideContext() {
    return app;
  }
}
