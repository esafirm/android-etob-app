package com.etob.android.data;

import com.etob.android.data.model.BaseResponse;
import com.etob.android.data.model.Config;
import com.etob.android.data.remote.ApiService;
import com.google.android.gms.maps.model.LatLng;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

@Singleton public class DataManager {

  private final ApiService mApiService;

  @Inject public DataManager(ApiService apiService) {
    mApiService = apiService;
  }

  public Observable<Config> getConfig() {
    return mApiService.getConfig(true);
  }

  public Observable<BaseResponse> reportLocation(LatLng latLng) {
    return mApiService.reportLocation(latLng.latitude, latLng.longitude);
  }
}