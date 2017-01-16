package model;

import android.graphics.Bitmap;

/**
 * Created by Marlon on 26/12/2016.
 */

public class ImageItem {

    private Bitmap image;

    public ImageItem(Bitmap image) {
        super();
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
