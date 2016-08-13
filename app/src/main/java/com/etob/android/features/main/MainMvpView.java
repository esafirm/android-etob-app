package com.etob.android.features.main;

import android.location.Location;
import com.etob.android.data.model.Config;
import com.etob.android.features.common.MvpView;

public interface MainMvpView extends MvpView {

  void showLoading(boolean isLoading);
  void showProfile(Config.ProfileEntity profileEntity);
  void showCurrentLocation(Location location);
  void showError(Throwable throwable);
}
