package id.my.developer.imagepicker.image_picker_activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import id.my.developer.imagepicker.models.Media;
import id.my.developer.imagepicker.utils.MediaStoreManager;

/**
 * Created by light on 27/07/2017.
 */

public class ImagePickerPresenter {
    private ImagePickerInterface.Callback callback;
    private AppCompatActivity activity;
    List<Media> mediaList = new ArrayList<>();
    public int delimiter = 0;
    private CameraHandler cameraHandler;
    private VideoHandler videoHandler;

    public ImagePickerPresenter(ImagePickerInterface.Callback callback) {
        this.callback = callback;
        this.activity = (AppCompatActivity) callback;
        this.cameraHandler = new CameraHandler(this.activity);
        this.videoHandler = new VideoHandler(this.activity);
    }

    public void loadAllMedia() {
        mediaList.addAll(MediaStoreManager.getAllVideos(activity));//loadAllVideos();
        delimiter+=mediaList.size();
        mediaList.addAll(MediaStoreManager.getAllImages(activity));//loadALlImages();
        if (mediaList != null && mediaList.size() > 0) {
            callback.onPicturesLoaded(mediaList);
        }
    }

    public CameraHandler getCameraHandler() {
        return cameraHandler;
    }

    public VideoHandler getVideoHandler(){
        return videoHandler;
    }

}
