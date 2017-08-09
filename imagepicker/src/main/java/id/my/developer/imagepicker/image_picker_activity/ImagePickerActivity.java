package id.my.developer.imagepicker.image_picker_activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import id.my.developer.imagepicker.R;
import id.my.developer.imagepicker.adapter.MediaListAdapter;
import id.my.developer.imagepicker.models.Media;
import id.my.developer.imagepicker.utils.MediaStoreManager;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class ImagePickerActivity extends AppCompatActivity implements ImagePickerInterface.Callback, EasyPermissions.PermissionCallbacks{
    private Button doneButton;

    private RecyclerView mediaRecyclerView;
    private List<Media> mediaList = null;

    private ImagePickerPresenter presenter;
    private MediaListAdapter adapter;
    private GridLayoutManager lm;
    private String[] cameraAndWritePermissions = new String[]{Manifest.permission.CAMERA};
    private int maxVideoDuration = 15;
    private int maxVideoSize = 15;

    private static final int RC_CAMERA_AND_WRITE_FILE= 123;
    private static final int RC_VIDEO_AND_WRITE_FILE= 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ImagePickerPresenter(this);
        setContentView(R.layout.activity_image_picker);
        init();
    }

    private void init(){
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        doneButton = (Button) findViewById(R.id.done_button);
        mediaRecyclerView = (RecyclerView) findViewById(R.id.media_recyclerview);
        presenter.loadAllMedia();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPicturesLoaded(List<Media> mediaList) {
        this.mediaList = mediaList;
        lm = new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);
        lm.setAutoMeasureEnabled(true);
        mediaRecyclerView.setLayoutManager(lm);
        adapter = new MediaListAdapter(this,this.mediaList);
        mediaRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onMediaSelected() {
        doneButton.setVisibility(View.VISIBLE);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnResult(adapter.getSelectedMedia());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.imagepicker_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.camera_icon){
            if(EasyPermissions.hasPermissions(this, cameraAndWritePermissions))
                presenter.getCameraHandler().startCamera();
            else requestCameraPermission(RC_CAMERA_AND_WRITE_FILE);
        }else if(item.getItemId()==R.id.video_icon){
            if(EasyPermissions.hasPermissions(this, cameraAndWritePermissions))
                presenter.getVideoHandler().startVideo();
            else requestCameraPermission(RC_VIDEO_AND_WRITE_FILE);
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestCameraPermission(int requestCode){
        EasyPermissions.requestPermissions(this, getString(R.string.camera_permission), requestCode, cameraAndWritePermissions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode == RESULT_OK){
            new Runnable() {
                @Override
                public void run() {
                    MediaStoreManager.insertImage(ImagePickerActivity.this,presenter.getCameraHandler().getCurrentFilePath(),presenter.getCameraHandler().getCurrentFilename());
                    returnResult(presenter.getCameraHandler().getCurrentFilePath());
                }
            }.run();
        }else if(requestCode==2 && resultCode == RESULT_OK){
            final Uri videoUri = data.getData();
            new Runnable(){
                @Override
                public void run() {
                    String path = MediaStoreManager.getFilenameFromUri(ImagePickerActivity.this,videoUri);
                    returnResult(path);
                }
            }.run();
        }
    }

    private void returnResult(String path){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("path",path);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(requestCode==RC_CAMERA_AND_WRITE_FILE)
            presenter.getCameraHandler().startCamera();
        else if(requestCode==RC_VIDEO_AND_WRITE_FILE)
            presenter.getVideoHandler().startVideo();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
