package id.my.developer.imagepicker;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import id.my.developer.imagepicker.models.Media;

/**
 * Created by light on 27/07/2017.
 */

public class ImagePickerPresenter {
    private ImagePickerInterface.Callback callback;
    private Context context;
    List<Media> mediaList = new ArrayList<>();

    public ImagePickerPresenter(ImagePickerInterface.Callback callback) {
        this.callback = callback;
        this.context = (ImagePickerActivity)callback;
    }

    public void loadAllMedia(){
        loadAllVideos();
        loadALlImages();
        if(mediaList!=null&&mediaList.size()>0){
            callback.onPicturesLoaded(mediaList);
        }
    }

    private void loadALlImages(){
        String[] projections = new String[]{MediaStore.Images.Media.DATA};
        String orderBy = MediaStore.Images.Media.DATE_ADDED+" DESC";

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cur = context.getContentResolver().query(uri,projections,null,null,orderBy);

        Log.d("ListingImages","query count = "+cur.getCount());

        if(cur.moveToFirst()){
            int dataColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);
            do{
                Media media = new Media();
                media.setType(2);
                media.setPath(cur.getString(dataColumn));
                mediaList.add(media);
            }while(cur.moveToNext());
        }
        cur.close();
    }

    private void loadAllVideos(){
        String[] projections = new String[]{MediaStore.Video.Thumbnails.DATA};
        String orderBy = MediaStore.Video.Thumbnails.DEFAULT_SORT_ORDER;

        Uri uri = MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;

        Cursor cur = context.getContentResolver().query(uri,projections,null,null,orderBy);

        Log.d("ListingVideos", "query count = "+cur.getCount());

        if(cur.moveToFirst()){
            int dataColumn = cur.getColumnIndex(MediaStore.Video.Thumbnails.DATA);
            do{
                Media media = new Media();
                media.setType(1);
                media.setPath(cur.getString(dataColumn));
                mediaList.add(media);
            }while (cur.moveToNext());
        }
        cur.close();
    }
}
