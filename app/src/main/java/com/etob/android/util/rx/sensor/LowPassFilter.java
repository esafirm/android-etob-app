package com.etob.android.util.rx.sensor;

import rx.Observable;

/**
 * Low pass filter implemented using Transformer api
 *
 * @author manolovn
 * @author TomeOkin
 * @see <a href="https://www.built.io/blog/2013/05/applying-low-pass-filter-to-android-sensors-readings/">Applying
 * Low Pass Filter to Android Sensor’s Readings</a>
 */
public class LowPassFilter implements Observable.Transformer<RxSensorEvent, RxSensorEvent> {

  private static final float DEAFAULT_FACTOR = 0.25f;

  private final float factor;
  private float[] sensorValue;

  public static LowPassFilter makeDefault() {
    return new LowPassFilter(DEAFAULT_FACTOR);
  }

  public static LowPassFilter of(float factor) {
    return new LowPassFilter(factor);
  }

  private LowPassFilter(float factor) {
    this.factor = factor;
  }

  @Override public Observable<RxSensorEvent> call(Observable<RxSensorEvent> source) {
    return source.map(sensorEvent -> {
      sensorValue = lowPass(sensorEvent.values.clone(), sensorValue);
      sensorEvent.values = sensorValue.clone();
      return sensorEvent;
    });
  }

  protected float[] lowPass(float[] current, float[] last) {
    if (last == null) {
      return current;
    }

    for (int i = 0; i < current.length; i++) {
      last[i] = last[i] + factor * (current[i] - last[i]);
    }
    return last;
  }
}