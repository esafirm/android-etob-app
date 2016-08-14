package com.etob.android.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.etob.android.EtobApp;
import com.etob.android.data.DataManager;
import com.etob.android.managers.LocationManager;
import com.etob.android.util.AndroidComponentUtil;
import com.etob.android.util.NetworkUtil;
import javax.inject.Inject;
import timber.log.Timber;

public class TrackingService extends Service {

  @Inject DataManager mDataManager;
  @Inject LocationManager locationManager;

  @Override public IBinder onBind(Intent intent) {
    return null;
  }

  public static boolean isRunning(Context context) {
    return AndroidComponentUtil.isServiceRunning(context, TrackingService.class);
  }

  @Override public void onCreate() {
    super.onCreate();
    EtobApp.component().inject(this);

    Timber.d("Tracking started ...");
    locationManager.getMovingLocationUpdate()
        .filter(l -> NetworkUtil.isNetworkConnected(this))
        .flatMap(location -> mDataManager.reportLocation(location))
        .forEach(res -> Timber.d("Tracking response:" + res.getMessage()));
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    return START_NOT_STICKY;
  }

  @Override public void onDestroy() {
    Timber.d("Tracking Service destroyed ..");
    super.onDestroy();
  }

  /* --------------------------------------------------- */
  /* > Stater */
  /* --------------------------------------------------- */

  public static Intent getStartIntent(Context context) {
    return new Intent(context, TrackingService.class);
  }
}