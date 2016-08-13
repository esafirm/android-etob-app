package com.incendiary.androidboilerplate.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.etob.android.di.component.ApplicationComponent;
import com.incendiary.androidboilerplate.test.common.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
