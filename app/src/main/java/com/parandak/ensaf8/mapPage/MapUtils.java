package com.parandak.ensaf8.mapPage;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.osmdroid.util.GeoPoint;

/**
 * Phase 1E-4: Utility behavior extracted from MapActivity.
 * Existing behavior is intentionally preserved.
 */
public class MapUtils {

    /**
     * Phase 1E-4: Extracted from MapActivity.
     * Existing keyboard-hiding behavior is intentionally preserved.
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();

        if (view != null) {
            imm.hideSoftInputFromWindow(
                    view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
            );
        }
    }

    /**
     * Phase 1E-4: Extracted from MapActivity.
     * Existing distance calculation is intentionally preserved.
     */
    public static int distance(GeoPoint geoPoint01, GeoPoint geoPoint02) {
        double lat1 = geoPoint01.getLatitude(), lon1 = geoPoint01.getLongitude();
        double lat2 = geoPoint02.getLatitude(), lon2 = geoPoint02.getLongitude();

        double theta = lon1 - lon2;

        double dist =
                Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                        + Math.cos(deg2rad(lat1))
                        * Math.cos(deg2rad(lat2))
                        * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        return (int) (dist * 1609.344);
    }

    /**
     * Phase 1E-4: Extracted from MapActivity.
     * Existing calculation is intentionally preserved.
     */
    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * Phase 1E-4: Extracted from MapActivity.
     * Existing calculation is intentionally preserved.
     */
    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}

