package id.my.developer.imagepicker.image_picker_activity;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by light on 02/08/2017.
 */

public class VideoHandler {
    private AppCompatActivity activity;

    public VideoHandler(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void startVideo(){
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,0);
        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);//0 for low, 1 higth
        //takeVideoIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, limit*1048576L);
        if (takeVideoIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takeVideoIntent, 2);
        }
    }
}
