package com.etob.android.features.main;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.etob.android.R;
import com.etob.android.data.model.Config;
import com.etob.android.features.common.BaseActivity;
import com.etob.android.util.ActivityHelper;
import com.etob.android.util.glide.CropCircleTransformation;
import com.etob.android.util.rx.RxGoogleMap;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMvpView {

  @BindView(R.id.imgContent) ImageView imgContent;
  @BindView(R.id.txtName) TextView txtName;
  @BindView(R.id.txtCaption) TextView txtBalance;
  @BindView(R.id.swipeRefreshView) SwipeRefreshLayout swipeRefreshLayout;

  @Inject MainPresenter presenter;

  private GoogleMap mGoogleMap;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityComponent().inject(this);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    activityComponent().inject(this);

    presenter.attachView(this);
    presenter.loadProfile();

    swipeRefreshLayout.setDistanceToTriggerSync(Integer.MAX_VALUE);
    swipeRefreshLayout.setColorSchemeResources(R.color.accent);

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
    txtBalance.setText(profileEntity.getBalance());
  }

  @Override public void showCurrentLocation(Location location) {
  }

  @Override public void showError(Throwable throwable) {
  }

  @Override public void showLoading(boolean isLoading) {
    swipeRefreshLayout.setRefreshing(isLoading);
  }
}
