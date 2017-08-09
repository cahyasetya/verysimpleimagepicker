package id.my.developer.imagepicker.models;

import android.support.constraint.ConstraintLayout;

/**
 * Created by light on 09/08/2017.
 */

public class Constants {
    public static int maxVideoDuration = 15;
    public static int maxVideoSize = 15;
    public static int videoQuality = 0;

    public static final int VIDEO_QUALITY_LOW = 0;
    public static final int VIDEO_QUALITY_HIGH = 1;

    private static final Constants ourInstance = new Constants();

    public static Constants getInstance() {
        return ourInstance;
    }

    private Constants() {
    }
}
