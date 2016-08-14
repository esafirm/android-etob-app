package com.etob.android.util.rx.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

/**
 * Created by esafirm on 8/13/16.
 */
public class RxSensor {

  private final SensorManager sensorManager;

  @Inject public RxSensor(SensorManager sensorManager) {
    this.sensorManager = sensorManager;
  }

  public Observable<RxSensorEvent> observe(final int sensorType, final int samplingPeriodUs) {
    return Observable.create(new Observable.OnSubscribe<RxSensorEvent>() {
      @Override public void call(final Subscriber<? super RxSensorEvent> subscriber) {

        final Sensor sensor = sensorManager.getDefaultSensor(sensorType);

        if (sensor == null) {
          subscriber.onError(new IllegalStateException("Sensor must be known by android"));
        }

        final SensorEventListener sensorEventListener = new SensorEventListener() {
          @Override public void onSensorChanged(SensorEvent event) {
            subscriber.onNext(RxSensorEvent.from(event));
          }

          @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {
          }
        };

        sensorManager.registerListener(sensorEventListener, sensor, samplingPeriodUs);

        subscriber.add(
            Subscriptions.create(() -> sensorManager.unregisterListener(sensorEventListener)));
      }
    }).onBackpressureLatest();
  }
}
