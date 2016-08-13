package com.etob.android.features.splash;

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
import javax.inject.Inject;

/**
 * Created by esafirm on 8/13/16.
 */
public class SplashAct extends BaseActivity implements SplashView {

  private static final int ANIMATION_DURATION = 300 ;

  @BindView(R.id.imgContent) ImageView imgContent;

  @Inject SplashPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);

    activityComponent().inject(this);

    imgContent.setTranslationY(100f);
    imgContent.setAlpha(0f);

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

  @Override public void showConfigLoaded() {
    finish();
    startActivity(new Intent(this, MainActivity.class));
  }
}
