package com.etob.android.util;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by esafirm on 8/13/16.
 */
public class ActivityHelper {

  @SuppressWarnings("unchecked")
  public static <T extends Fragment> T findFragment(AppCompatActivity activity, @IdRes int resId) {
    return (T) activity.getSupportFragmentManager().findFragmentById(resId);
  }
}
