package com.etob.android.util.maps;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import com.etob.android.R;
import com.etob.android.data.local.Preferences;
import com.etob.android.data.model.Config;
import com.etob.android.util.BitmapUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.incendiary.androidcommon.android.ContextProvider;

/**
 * Created by esafirm on 8/13/16.
 */
public class GoogleMapUtils {

  private static final int ZOOM_LEVEL = 11;

  public static void moveCamera(GoogleMap googleMap, double lat, double lng) {
    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), ZOOM_LEVEL));
  }

  public static Marker addMyLocationMarker(GoogleMap googleMap, Location location) {
    Config config = Preferences.getConfig();

    Drawable drawable =
        ContextCompat.getDrawable(ContextProvider.get(), R.drawable.ic_navigation).mutate();

    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
    bitmap = BitmapUtil.changeBitmapColor(bitmap, config.getColor());

    MarkerOptions options = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitmap))
        .position(new LatLng(location.getLatitude(), location.getLongitude()))
        .title("My Location")
        .rotation(location.getBearing())
        .flat(true);

    return googleMap.addMarker(options);
  }
}
