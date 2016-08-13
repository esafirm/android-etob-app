package com.etob.android.data.local;

import android.app.Application;
import com.etob.android.BuildConfig;
import com.etob.android.data.model.Config;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

public class Preferences {

  public class Key {
    public static final String CONFIG = "Key.Config";
  }

  private static Preferences INSTANCE;

  public static Preferences getInstance() {
    if (INSTANCE == null) INSTANCE = new Preferences();
    return INSTANCE;
  }

  /* --------------------------------------------------- */
  /* > Setup */
  /* --------------------------------------------------- */

  public static void setup(Application application) {
    Hawk.init(application)
        .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
        .setLogLevel(BuildConfig.DEBUG
            ? LogLevel.FULL
            : LogLevel.NONE)
        .buildRx()
        .subscribe();
  }

  /* --------------------------------------------------- */
  /* > Data */
  /* --------------------------------------------------- */

  public static void saveConfig(Config config) {
    put(Key.CONFIG, config);
  }

  public static Config getConfig() {
    return get(Key.CONFIG);
  }

  /* --------------------------------------------------- */
  /* > Common */
  /* --------------------------------------------------- */

  private static void put(String key, Object o) {
    Hawk.putObservable(key, o).subscribe();
  }

  private static <T> T get(String key) {
    return Hawk.get(key);
  }
}
