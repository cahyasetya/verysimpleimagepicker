package id.my.developer.imagepicker.utils.file_manager;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.my.developer.imagepicker.utils.file_manager.FileSaveListener;

/**
 * Created by light on 31/07/2017.
 */

public class FileManager {
    private Context context;
    private FileSaveListener callback;

    public FileManager(Context context, FileSaveListener callback) {
        this.context = context;
        this.callback = callback;
    }

    public void saveImageToInternalStorage(String directoryPath, String filePath, Bitmap bitmap){
        ImageCompressor compressor = new ImageCompressor(context,directoryPath,filePath);
        compressor.execute(new Bitmap[]{bitmap});
    }

    public String generateImageFilename(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        String filename = formatter.format(now)+".png";
        return filename;
    }

    class ImageCompressor extends AsyncTask<Bitmap, Void, String>{
        private Context context;
        private String directoryPath;
        private String filePath;

        public ImageCompressor(Context context, String directoryPath, String filePath) {
            this.context = context;
            this.directoryPath = directoryPath;
            this.filePath = filePath;
        }

        @Override
        protected String doInBackground(Bitmap... bitmaps) {
            ContextWrapper wrapper = new ContextWrapper(context.getApplicationContext());

            //creating dir
            File directory = new File(directoryPath);
            if(!directory.exists())
                directory.mkdirs();

            File file = new File(directory, filePath);

            FileOutputStream fos=null;
            try {
                fos = new FileOutputStream(file);
                bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(file));
                context.sendBroadcast(intent);
                callback.onFileSuccessfullySaved(file.getAbsolutePath());
                return file.getAbsolutePath();
            }catch (Exception e){
                e.printStackTrace();
                callback.onFileSaveFailed(e);
                return null;
            }finally {
                try {
                    fos.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}