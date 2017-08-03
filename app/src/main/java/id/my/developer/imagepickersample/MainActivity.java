package id.my.developer.imagepickersample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.my.developer.imagepicker.ImagePicker;
import id.my.developer.imagepicker.PermissionListener;
import id.my.developer.imagepicker.image_picker_activity.ImagePickerActivity;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    Button imagepickerButton;
    ImageView mediaHolder;
    String[] perms = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagepickerButton = (Button)findViewById(R.id.imagepicker_button);
        mediaHolder = (ImageView) findViewById(R.id.media_holder);
        imagepickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToImagePicker();
            }
        });
    }

    private void navigateToImagePicker(){
        if(EasyPermissions.hasPermissions(this,perms))
            new ImagePicker(this, 1).start();
        else EasyPermissions.requestPermissions(this,"We need permission to use the feature",1,perms);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode==Activity.RESULT_OK&&data!=null){
            String path = data.getStringExtra("path");
            Glide.with(this).load(path).centerCrop().into(mediaHolder);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this).build();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        navigateToImagePicker();
    }
}
