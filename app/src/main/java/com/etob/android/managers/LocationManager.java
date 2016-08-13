package com.etob.android.managers;

import android.location.Location;
import com.google.android.gms.location.LocationRequest;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import timber.log.Timber;

/**
 * Created by esafirm on 8/13/16.
 */
public class LocationManager {

  private static final long INTERVAL = TimeUnit.SECONDS.toMillis(30);
  private static final int MIN_MOVE_DISTANCE_IN_METER = 2;

  private final ReactiveLocationProvider locationProvider;
  private final BehaviorSubject<Location> subject = BehaviorSubject.create();

  private Location lastLocation;

  @Inject public LocationManager(ReactiveLocationProvider locationProvider) {
    this.locationProvider = locationProvider;
    init();
  }

  private void init() {
    LocationRequest request = LocationRequest.create()
        .setInterval(INTERVAL)
        .setFastestInterval(INTERVAL / 2)
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    locationProvider.getUpdatedLocation(request)
        .subscribeOn(Schedulers.io())
        .filter(o -> o != null)
        .doOnNext(location -> Timber.d("Get location update %s", location.toString()))
        .doOnNext(location -> lastLocation = location)
        .forEach(subject::onNext);
  }

  public Observable<Location> getLocationUpdate() {
    return subject;
  }

  public Observable<Location> getMovingLocationUpdate() {
    return subject.filter(location -> lastLocation == null
        || location.distanceTo(lastLocation) >= MIN_MOVE_DISTANCE_IN_METER);
  }
}
