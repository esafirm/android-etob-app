package com.etob.android.features.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.etob.android.R;
import com.etob.android.features.common.BaseActivity;
import com.etob.android.features.main.MainActivity;
import com.incendiary.androidcommon.android.Toasts;
import com.tbruyelle.rxpermissions.RxPermissions;
import javax.inject.Inject;

/**
 * Created by esafirm on 8/13/16.
 */
public class SplashAct extends BaseActivity implements SplashView {

  private static final int ANIMATION_DURATION = 300;

  @BindView(R.id.imgContent) ImageView imgContent;

  @Inject SplashPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);

    activityComponent().inject(this);

    imgContent.setTranslationY(100f);
    imgContent.setAlpha(0f);

    RxPermissions.getInstance(this)
        .request(Manifest.permission.ACCESS_FINE_LOCATION)
        .subscribe(aBoolean -> {
          if (aBoolean) {
            init();
          } else {
            finish();
            Toasts.show("You should allow location permission to use this app");
          }
        });
  }

  private void init() {
    ViewCompat.animate(imgContent)
        .setDuration(ANIMATION_DURATION)
        .alpha(1f)
        .translationY(0f)
        .setInterpolator(new FastOutLinearInInterpolator())
        .withEndAction(() -> {
          presenter.attachView(this);
          presenter.loadConfig();
        });
  }

  @Override public void showError(Throwable throwable) {
    String message = throwable != null
        ? throwable.getMessage()
        : "Connection Error";
    Toasts.show(message);
    finish();
  }

  @Override public void showConfigLoaded() {
    finish();
    startActivity(new Intent(this, MainActivity.class));
  }
}
