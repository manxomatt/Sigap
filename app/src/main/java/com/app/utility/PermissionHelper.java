package com.app.utility;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class PermissionHelper {
    public static final int MY_PERMISSION_REQUEST_STORAGE = 100;

    public static boolean requestReadWriteStoragePermissions(Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(activity, "Please allow a requested permission to upload.", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(activity,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSION_REQUEST_STORAGE);
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(activity, "Please allow a requested permission to upload.", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(activity,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSION_REQUEST_STORAGE);
                } else {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSION_REQUEST_STORAGE);
                }

                return false;
            }
        }

        return true;
    }
}
