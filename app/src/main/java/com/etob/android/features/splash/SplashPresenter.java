package com.etob.android.features.splash;

import com.etob.android.data.DataManager;
import com.etob.android.data.local.Preferences;
import com.etob.android.features.common.BasePresenter;
import javax.inject.Inject;

/**
 * Created by esafirm on 8/13/16.
 */
public class SplashPresenter extends BasePresenter<SplashView> {

  private final DataManager dataManager;

  @Inject  public SplashPresenter(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  public void loadConfig() {
    dataManager.getConfig().doOnNext(Preferences::saveConfig).subscribe(config -> {
      getView().showConfigLoaded();
    });
  }
}
