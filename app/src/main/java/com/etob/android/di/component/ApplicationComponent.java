package com.etob.android.di.component;

import android.app.Application;
import android.content.Context;
import com.etob.android.data.DataManager;
import com.etob.android.services.TrackingService;
import com.etob.android.data.remote.ApiService;
import com.etob.android.di.ApplicationContext;
import com.etob.android.di.module.ApplicationModule;
import com.etob.android.di.module.NetworkModule;
import com.etob.android.util.rx.RxEventBus;
import dagger.Component;
import javax.inject.Singleton;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

@Singleton @Component(modules = {
    ApplicationModule.class, NetworkModule.class
}) public interface ApplicationComponent {

  void inject(TrackingService syncService);

  @ApplicationContext Context context();

  Application application();
  ApiService apiService();
  DataManager dataManager();
  RxEventBus eventBus();
  ReactiveLocationProvider locationProvider();
}
