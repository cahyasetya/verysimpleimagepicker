package id.my.developer.imagepicker.utils;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import id.my.developer.imagepicker.models.Constants;
import id.my.developer.imagepicker.models.Media;

/**
 * Created by light on 02/08/2017.
 */

public class MediaStoreManager {
    private static final MediaStoreManager instance = new MediaStoreManager();

    public MediaStoreManager() {
    }

    public static void insertImage(AppCompatActivity activity,String filePath, String fileName){
        try {
            MediaStore.Images.Media.insertImage(activity.getContentResolver(),filePath,fileName,null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getFilenameFromUri(AppCompatActivity activity,Uri uri){
        Cursor cursor = null;
        try{
            String[] proj = {MediaStore.Video.Media.DATA};
            cursor = activity.getContentResolver().query(uri, proj,null,null,null);
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            if(cursor.moveToFirst())
                return cursor.getString(columnIndex);
            else return null;
        }finally {
            if(cursor!=null)
                cursor.close();
        }
    }

    public static List<Media> getAllImages(AppCompatActivity activity){
        List<Media> mediaList = new ArrayList<>();
        String[] projections = new String[]{MediaStore.Images.Media.DATA};
        String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cur = activity.getContentResolver().query(uri, projections, null, null, orderBy);

        Log.d("ListingImages", "query count = " + cur.getCount());

        if (cur.moveToFirst()) {
            int dataColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);
            do {
                Media media = new Media();
                media.setType(2);
                media.setPath(cur.getString(dataColumn));
                mediaList.add(media);
            } while (cur.moveToNext());
        }
        cur.close();
        return mediaList;
    }

    public static List<Media> getAllVideos(AppCompatActivity activity){
        Context context = (Context) activity;

        List<Media> mediaList = new ArrayList<>();
        String[] projections = new String[]{MediaStore.Video.Media.DATA};
        String selection = MediaStore.Video.Media.MIME_TYPE+" =?";
        String[] selectionArgs = new String[]{"video/mp4"};

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        Cursor cur = activity.getContentResolver().query(uri, projections, null, null, null);

        if (cur.moveToFirst()) {
            int dataColumn = cur.getColumnIndex(MediaStore.Video.Media.DATA);
            do {
                File file = new File(cur.getString(dataColumn));
                long length = file.length()/(1024*1024);

                Uri videoUri = Uri.fromFile(file);

                try {
                    retriever.setDataSource(context,videoUri);
                    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    long timeInMilis = Long.parseLong(time);
                    if(timeInMilis/1000<= Constants.maxVideoDuration && length<=Constants.maxVideoSize){
                        Media media = new Media();
                        media.setType(1);
                        media.setPath(file.getAbsolutePath());
                        media.setDuration(timeInMilis);
                        mediaList.add(media);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            } while (cur.moveToNext());
        }
        cur.close();
        retriever.release();
        return mediaList;
    }
}
