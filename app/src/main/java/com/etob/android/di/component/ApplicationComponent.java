package com.etob.android.di.component;

import android.app.Application;
import android.content.Context;
import com.etob.android.data.DataManager;
import com.etob.android.data.SyncService;
import com.etob.android.data.local.DatabaseHelper;
import com.etob.android.di.module.ApplicationModule;
import com.etob.android.di.module.NetworkModule;
import dagger.Component;
import javax.inject.Singleton;
import com.etob.android.data.remote.ApiService;
import com.etob.android.di.ApplicationContext;
import com.etob.android.util.RxEventBus;

@Singleton @Component(modules = {
    ApplicationModule.class, NetworkModule.class
}) public interface ApplicationComponent {

  void inject(SyncService syncService);

  @ApplicationContext Context context();

  Application application();
  ApiService apiService();
  DatabaseHelper databaseHelper();
  DataManager dataManager();
  RxEventBus eventBus();
}
