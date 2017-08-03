package id.my.developer.imagepicker;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by light on 03/08/2017.
 */

public abstract class PermissionListener implements EasyPermissions.PermissionCallbacks{
    private AppCompatActivity activity;

    public PermissionListener(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public abstract void onPermissionsDenied(int requestCode, List<String> perms);

    @Override
    public abstract void onPermissionsGranted(int requestCode, List<String> perms);

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
