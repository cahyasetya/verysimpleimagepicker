package id.my.developer.imagepickersample;

import android.Manifest;
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

import id.my.developer.imagepicker.ImagePickerActivity;

public class MainActivity extends AppCompatActivity {

    Button imagepickerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagepickerButton = (Button)findViewById(R.id.imagepicker_button);
        imagepickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                    startActivity(new Intent(MainActivity.this, ImagePickerActivity.class));
                else checkPermission();
            }
        });
    }

    private void checkPermission(){
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    ActivityCompat.requestPermissions(this, new String[]{permission}, 100);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{permission}, 100);
                }
            }else{
                navigateToImagePicker();
            }
        }else{
            navigateToImagePicker();
        }
    }

    private void navigateToImagePicker(){
        startActivity(new Intent(this, ImagePickerActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 100:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    startActivity(new Intent(this, ImagePickerActivity.class));
                }
        }
    }
}
