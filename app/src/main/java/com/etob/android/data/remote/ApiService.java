package com.etob.android.data.remote;

import com.etob.android.data.model.BaseResponse;
import com.etob.android.data.model.Config;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

//@formatter:off
public interface ApiService {

  @GET("config") Observable<Config> getConfig(
      @Query("config") boolean config
  );

  @FormUrlEncoded @POST("track") Observable<BaseResponse> reportLocation(
      @Field("latitude") double lat,
      @Field("longitude") double lng
  );
}
