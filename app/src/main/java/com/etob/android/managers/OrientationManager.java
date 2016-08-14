package com.etob.android.managers;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import com.etob.android.util.rx.sensor.LowPassFilter;
import com.etob.android.util.rx.sensor.RxSensor;
import com.etob.android.util.rx.sensor.RxSensorEvent;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by esafirm on 8/14/16.
 */
public class OrientationManager {

  private final float[] mAccelerometerReading = new float[3];
  private final float[] mMagnetometerReading = new float[3];

  private final float[] mRotationMatrix = new float[9];
  private final float[] mOrientationAngles = new float[3];

  private final RxSensor rxSensor;

  @Inject public OrientationManager(RxSensor rxSensor) {
    this.rxSensor = rxSensor;
  }

  public Observable<OrientationEvent> getOrientation() {
    Observable<RxSensorEvent> acceleroObs =
        rxSensor.observe(Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL)
            .compose(LowPassFilter.makeDefault())
            .doOnNext(event -> System.arraycopy(event.values, 0, mAccelerometerReading, 0,
                mAccelerometerReading.length));

    Observable<RxSensorEvent> magneticObs =
        rxSensor.observe(Sensor.TYPE_MAGNETIC_FIELD, SensorManager.SENSOR_DELAY_NORMAL)
            .compose(LowPassFilter.makeDefault())
            .doOnNext(event -> System.arraycopy(event.values, 0, mMagnetometerReading, 0,
                mMagnetometerReading.length));

    return acceleroObs.zipWith(magneticObs, (rxSensorEvent, rxSensorEvent2) -> {
      SensorManager.getRotationMatrix(mRotationMatrix, null, mAccelerometerReading,
          mMagnetometerReading);
      SensorManager.getOrientation(mRotationMatrix, mOrientationAngles);
      return new OrientationEvent(mOrientationAngles);
    });
  }

  public class OrientationEvent {

    private final float azimuth;
    private final float roll;
    private final float pitch;

    public OrientationEvent(float[] orientationAngles) {
      this.azimuth = orientationAngles[0];
      this.roll = orientationAngles[1];
      this.pitch = orientationAngles[2];
    }

    public float getAzimuth() {
      return azimuth;
    }

    public float getRoll() {
      return roll;
    }

    public float getPitch() {
      return pitch;
    }
  }

  public static float getDegreeValue(float radiansValue) {
    return (float) (Math.toDegrees(radiansValue) + 360) % 360;
  }
}
