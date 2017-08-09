package id.my.developer.imagepicker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import id.my.developer.imagepicker.image_picker_activity.ImagePickerActivity;
import id.my.developer.imagepicker.models.Constants;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by light on 02/08/2017.
 */

public class ImagePicker {
    private Activity activity;
    private int requestCode;

    public ImagePicker(Activity activity, int requestCode) {
        this.activity = activity;
        this.requestCode = requestCode;
    }

    public void setMaxVideoDuration(int maxDuration){
        Constants.maxVideoDuration = maxDuration;
    }

    public void setMaxVideoSize(int videoSize){
        Constants.maxVideoSize = videoSize;
    }

    public void start(){
        activity.startActivityForResult(new Intent(activity, ImagePickerActivity.class), requestCode);
    }
}
