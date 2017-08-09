package id.my.developer.imagepicker.models;

import android.support.constraint.ConstraintLayout;

/**
 * Created by light on 09/08/2017.
 */

public class Constants {
    public static int maxVideoDuration = 15;
    public static int maxVideoSize = 15;

    private static final Constants ourInstance = new Constants();

    public static Constants getInstance() {
        return ourInstance;
    }

    private Constants() {
    }
}
