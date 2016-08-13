package com.etob.android.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import com.etob.android.EtobApp;
import com.etob.android.util.AndroidComponentUtil;
import com.etob.android.util.NetworkUtil;
import javax.inject.Inject;
import rx.Subscription;
import timber.log.Timber;

public class SyncService extends Service {

  @Inject DataManager mDataManager;
  private Subscription mSubscription;

  public static Intent getStartIntent(Context context) {
    return new Intent(context, SyncService.class);
  }

  public static boolean isRunning(Context context) {
    return AndroidComponentUtil.isServiceRunning(context, SyncService.class);
  }

  @Override public void onCreate() {
    super.onCreate();
    EtobApp.component().inject(this);
  }

  @Override public IBinder onBind(Intent intent) {
    return null;
  }

  public static class SyncOnConnectionAvailable extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
      if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
          && NetworkUtil.isNetworkConnected(context)) {
        Timber.i("Connection is now available, triggering sync...");
        AndroidComponentUtil.toggleComponent(context, this.getClass(), false);
        context.startService(getStartIntent(context));
      }
    }
  }
}