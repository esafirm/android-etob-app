package com.etob.android.di.component;

import dagger.Subcomponent;
import com.etob.android.di.PerActivity;
import com.etob.android.di.module.ActivityModule;
import com.etob.android.features.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity @Subcomponent(modules = ActivityModule.class) public interface ActivityComponent {
  void inject(MainActivity mainActivity);
}
