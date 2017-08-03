package id.my.developer.imagepicker.image_picker_activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.my.developer.imagepicker.BuildConfig;

/**
 * Created by light on 02/08/2017.
 */

public class CameraHandler {
    private AppCompatActivity activity;
    private String currentFilePath = null;
    private String currentFilename = null;

    public CameraHandler(AppCompatActivity activity) {
        this.activity = activity;
    }

    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String imageFilename = "JPEG_"+timestamp+"_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFilename,".jpg",storageDir);
        currentFilePath = image.getAbsolutePath();
        currentFilename = imageFilename;
        return image;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public String getCurrentFilename() {
        return currentFilename;
    }

    public void startCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(activity.getPackageManager())!=null){
            File photoFile = null;
            try{
                photoFile = createImageFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            if(photoFile!=null){
                Uri photoUri = FileProvider.getUriForFile(activity, activity.getPackageName()+".provider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                activity.startActivityForResult(takePictureIntent,1);
            }
        }
    }
}
