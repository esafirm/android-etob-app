package com.etob.android.data;

import android.location.Location;
import com.etob.android.data.model.BaseResponse;
import com.etob.android.data.model.Config;
import com.etob.android.data.remote.ApiService;
import com.etob.android.util.rx.Transformers;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

@Singleton public class DataManager {

  private final ApiService mApiService;

  @Inject public DataManager(ApiService apiService) {
    mApiService = apiService;
  }

  public Observable<Config> getConfig() {
    return mApiService.getConfig(true).compose(Transformers.applyApiCall());
  }

  public Observable<BaseResponse> reportLocation(Location location) {
    return mApiService.reportLocation(location.getLatitude(), location.getLongitude())
        .compose(Transformers.applyApiCall());
  }
}