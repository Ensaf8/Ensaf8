package com.parandak.ensaf8.mapPage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapPermissionManager {

    public List<String> checkPermissions(Context context) {
        List<String> permissions = new ArrayList<>();

        // Check which permissions have been granted.
        // This method only evaluates permission state and does not change Activity state.

        // check for location permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // add missing permission
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        // check for storage permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // add missing permission
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        return permissions;
    }

    public boolean arePermissionsGranted(
            String[] permissions,
            int[] grantResults) {

        // Evaluate the Android permission result without changing Activity state.
        // UI, lifecycle actions, and mPermissionsGranted remain in MapActivity.

        Map<String, Integer> perms = new HashMap<>();

        // Preserve the existing default-granted initialization.
        perms.put(Manifest.permission.ACCESS_FINE_LOCATION,
                PackageManager.PERMISSION_GRANTED);

        perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                PackageManager.PERMISSION_GRANTED);

        // Preserve the existing result mapping behavior.
        for (int i = 0; i < permissions.length; i++) {
            perms.put(permissions[i], grantResults[i]);
        }

        // Preserve the existing permission evaluation.
        boolean location =
                perms.get(Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        boolean storage =
                perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED;

        return location && storage;
    }
}