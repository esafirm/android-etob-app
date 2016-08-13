package com.etob.android.di.component;

import android.app.Activity;
import com.etob.android.di.PerActivity;
import com.etob.android.di.module.ActivityModule;
import com.etob.android.features.main.MainActivity;
import com.etob.android.features.splash.SplashAct;
import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity @Subcomponent(modules = ActivityModule.class) public interface ActivityComponent {
  void inject(SplashAct splashAct);
  void inject(MainActivity mainActivity);

  Activity activity();
}
