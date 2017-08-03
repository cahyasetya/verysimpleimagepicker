package id.my.developer.imagepicker.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by light on 31/07/2017.
 */

public class SimplePermissionChecker {
    private Activity activity;

    public SimplePermissionChecker(Activity activity) {
        this.activity = activity;
    }

    public void checkPermission(String permission){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    ActivityCompat.requestPermissions(activity, new String[]{permission}, 100);
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{permission}, 100);
                }
            }
        }
    }
}
