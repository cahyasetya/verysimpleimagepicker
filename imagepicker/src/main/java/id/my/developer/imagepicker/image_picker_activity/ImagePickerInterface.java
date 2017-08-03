package id.my.developer.imagepicker.image_picker_activity;

import java.util.List;

import id.my.developer.imagepicker.models.Media;

/**
 * Created by light on 27/07/2017.
 */

public interface ImagePickerInterface {
    interface Callback{
        void onPicturesLoaded(List<Media> mediaList);
        void onMediaSelected();
    }
}
