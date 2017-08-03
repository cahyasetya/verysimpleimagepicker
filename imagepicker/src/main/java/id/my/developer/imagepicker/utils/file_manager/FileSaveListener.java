package id.my.developer.imagepicker.utils.file_manager;

/**
 * Created by light on 31/07/2017.
 */

public interface FileSaveListener {
    void onFileSuccessfullySaved(String path);
    void onFileSaveFailed(Throwable t);
}
