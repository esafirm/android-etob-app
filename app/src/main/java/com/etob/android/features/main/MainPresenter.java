package com.etob.android.features.main;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateUtils;
import com.etob.android.EtobApp;
import com.etob.android.data.local.Preferences;
import com.etob.android.di.ConfigPersistent;
import com.etob.android.features.common.BasePresenter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import javax.inject.Inject;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import rx_activity_result.RxActivityResult;

@ConfigPersistent public class MainPresenter extends BasePresenter<MainMvpView> {

  private Subscription mSubscription;

  @Inject public MainPresenter() {
  }

  @Override public void attachView(MainMvpView mvpView) {
    super.attachView(mvpView);
  }

  @Override public void detachView() {
    super.detachView();
    if (mSubscription != null) mSubscription.unsubscribe();
  }

  public void loadProfile() {
    getView().showProfile(Preferences.getConfig().getProfile());
  }

  public void loadCurrentLocation(Activity activity) {
    getView().showLoading(true);

    ReactiveLocationProvider locationProvider = EtobApp.component().locationProvider();
    mSubscription = locationProvider.checkLocationSettings(getSettingRequest())
        .switchMap(result -> handleResolution(activity, result))
        .switchMap(aBoolean -> {
          if (aBoolean) {
            return locationProvider.getLastKnownLocation();
          } else {
            return Observable.error(new IllegalStateException("Location service must be enabled"));
          }
        })
        .doOnTerminate(() -> getView().showLoading(false))
        .subscribe(location -> {
          getView().showCurrentLocation(location);
        }, throwable -> {
          getView().showError(throwable);
        });
  }

  private Observable<Boolean> handleResolution(Activity activity, LocationSettingsResult result) {
    Status status = result.getStatus();
    if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
      return RxActivityResult.on(activity)
          .startIntentSender(status.getResolution().getIntentSender(), new Intent(), 0, 0, 0)
          .map(activityResult -> activityResult.resultCode() == Activity.RESULT_OK);
    } else {
      return Observable.just(false);
    }
  }

  private LocationSettingsRequest getSettingRequest() {
    LocationRequest request = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setInterval(DateUtils.SECOND_IN_MILLIS);

    return new LocationSettingsRequest.Builder().addLocationRequest(request)
        .setAlwaysShow(true)
        .build();
  }
}
