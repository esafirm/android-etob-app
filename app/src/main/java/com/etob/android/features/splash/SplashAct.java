package com.etob.android.features.splash;

import android.content.Intent;
import android.os.Bundle;
import butterknife.ButterKnife;
import com.etob.android.R;
import com.etob.android.features.common.BaseActivity;
import com.etob.android.features.main.MainActivity;
import javax.inject.Inject;

/**
 * Created by esafirm on 8/13/16.
 */
public class SplashAct extends BaseActivity implements SplashView {

  @Inject SplashPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);

    activityComponent().inject(this);

    presenter.attachView(this);
    presenter.loadConfig();
  }

  @Override public void showConfigLoaded() {
    finish();
    startActivity(new Intent(this, MainActivity.class));
  }
}
