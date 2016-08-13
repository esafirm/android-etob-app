package com.etob.android.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

/**
 * Created by esafirm on 8/13/16.
 */
public class BitmapUtil {
  public static Bitmap changeBitmapColor(Bitmap bitmap, int color) {
    Paint paint = new Paint();
    paint.setColorFilter(new LightingColorFilter(color, 1));

    Bitmap resultBitmap =
        Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

    Canvas canvas = new Canvas(resultBitmap);
    canvas.drawBitmap(bitmap, 0, 0, paint);
    return resultBitmap;
  }
}
