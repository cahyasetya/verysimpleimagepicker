package id.my.developer.imagepicker.models;

import java.util.Date;

/**
 * Created by light on 27/07/2017.
 */

public class ImageModel {
    private int id;
    private String bucketName;
    private Date dateTaken;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }
}
