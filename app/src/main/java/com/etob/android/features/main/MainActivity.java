package com.etob.android.features.main;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.etob.android.R;
import com.etob.android.data.model.Config;
import com.etob.android.features.common.BaseActivity;
import com.etob.android.services.TrackingService;
import com.etob.android.util.ActivityHelper;
import com.etob.android.util.glide.CropCircleTransformation;
import com.etob.android.util.maps.GoogleMapUtils;
import com.etob.android.util.rx.RxGoogleMap;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.text.Strings;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMvpView {

  @BindView(R.id.imgContent) ImageView imgContent;
  @BindView(R.id.txtName) TextView txtName;
  @BindView(R.id.txtCaption) TextView txtBalance;
  @BindView(R.id.pbLoad) ProgressBar progressBar;

  @Inject MainPresenter presenter;

  private GoogleMap mGoogleMap;
  private Marker marker;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityComponent().inject(this);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    init();
  }

  private void init() {
    /* Start Tracking */
    startService(TrackingService.getStartIntent(this));

    presenter.attachView(this);
    presenter.loadProfile();

    SupportMapFragment mapFragment = ActivityHelper.findFragment(this, R.id.map);
    RxGoogleMap.bind(mapFragment).subscribe(googleMap -> {
      mGoogleMap = googleMap;
      presenter.loadCurrentLocation(this);
    });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
  }

  @Override public void showProfile(Config.ProfileEntity profileEntity) {
    Glide.with(this)
        .load(profileEntity.getPhoto())
        .bitmapTransform(new CropCircleTransformation(this))
        .into(imgContent);

    txtName.setText(profileEntity.getName());
    txtBalance.setText(Strings.format("Rp.%s", profileEntity.getBalance()));
  }

  @Override public void showCurrentLocation(Location location) {
    GoogleMapUtils.moveCamera(mGoogleMap, location.getLatitude(), location.getLongitude(),
        marker != null);

    if (marker == null) {
      marker = GoogleMapUtils.addMyLocationMarker(mGoogleMap, location);
    } else {
      marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
    }
  }

  @Override public void showError(Throwable throwable) {
    if (throwable != null) {
      Toasts.show(throwable.getMessage());
    }
  }

  @Override public void showLoading(boolean isLoading) {
    progressBar.setVisibility(isLoading
        ? View.VISIBLE
        : View.GONE);
  }

  @Override public void updateHeading(float rotation) {
    if (marker == null) return;
    GoogleMapUtils.rotateMarker(marker, rotation);
  }

  @OnClick(R.id.btnInfo) void onMyLocationClick() {
    presenter.loadCurrentLocation(this);
  }
}
