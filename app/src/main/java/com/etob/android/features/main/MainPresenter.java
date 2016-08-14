package com.etob.android.features.main;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.text.format.DateUtils;
import com.etob.android.EtobApp;
import com.etob.android.data.local.Preferences;
import com.etob.android.di.ConfigPersistent;
import com.etob.android.features.common.BasePresenter;
import com.etob.android.managers.LocationManager;
import com.etob.android.managers.OrientationManager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import rx_activity_result.RxActivityResult;
import timber.log.Timber;

@ConfigPersistent public class MainPresenter extends BasePresenter<MainMvpView> {

  private CompositeSubscription mSubscription = new CompositeSubscription();
  private ReactiveLocationProvider locationProvider = EtobApp.component().locationProvider();

  private final LocationManager locationManager;
  private final OrientationManager orientationManager;

  @Inject
  public MainPresenter(LocationManager locationManager, OrientationManager orientationManager) {
    this.locationManager = locationManager;
    this.orientationManager = orientationManager;
  }

  @Override public void attachView(MainMvpView mvpView) {
    super.attachView(mvpView);
  }

  @Override public void detachView() {
    super.detachView();
    if (mSubscription != null) mSubscription.clear();
  }

  public void loadProfile() {
    getView().showProfile(Preferences.getConfig().getProfile());
  }

  public void loadCurrentLocation(Activity activity) {
    getView().showLoading(true);

    mSubscription.add(locationProvider.checkLocationSettings(getSettingRequest())
        .switchMap(result -> handleResolution(activity, result))
        .switchMap(aBoolean -> {
          if (aBoolean) {
            return getCurrentLocation();
          } else {
            return Observable.error(new IllegalStateException("Location service must be enabled"));
          }
        })
        .doOnTerminate(() -> getView().showLoading(false))
        .doOnNext(location -> startUpdatingHeading())
        .doOnNext(location -> startUpdatingLocation())
        .subscribe(location -> getView().showCurrentLocation(location), (throwable) -> {
          Timber.e(throwable, "loadCurrentLocationError");
          getView().showError(throwable);
        }));
  }

  private void startUpdatingLocation() {
    mSubscription.add(locationManager.getLocationUpdate()
        .subscribe(location -> getView().showCurrentLocation(location)));
  }

  private void startUpdatingHeading() {
    mSubscription.add(orientationManager.getOrientation()
        .throttleLast(2500, TimeUnit.MILLISECONDS)
        .map(e -> OrientationManager.getDegreeValue(e.getAzimuth()))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(aFloat -> getView().updateHeading(aFloat)));
  }

  private Observable<Location> getCurrentLocation() {
    return Observable.merge(locationManager.getLocationUpdate(),
        locationProvider.getLastKnownLocation()).first();
  }

  private Observable<Boolean> handleResolution(Activity activity, LocationSettingsResult result) {
    Status status = result.getStatus();
    if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
      return RxActivityResult.on(activity)
          .startIntentSender(status.getResolution().getIntentSender(), new Intent(), 0, 0, 0)
          .map(activityResult -> activityResult.resultCode() == Activity.RESULT_OK);
    } else {
      return Observable.just(true);
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
