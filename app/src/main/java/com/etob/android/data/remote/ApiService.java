package com.etob.android.data.remote;

import com.etob.android.data.model.Ribot;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import rx.Observable;

public interface ApiService {

  @GET("ribots") Observable<List<Ribot>> getRibots();

  class Creator {
    public static ApiService newRibotsService(Retrofit retrofit) {
      return retrofit.create(ApiService.class);
    }
  }
}
