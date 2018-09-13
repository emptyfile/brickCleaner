package com.brick.cleaner;

import javafx.scene.image.ImageView;

public class CustomImage {
    private ImageView image;

    CustomImage(ImageView img) {
        this.image = img;
        this.image.setFitHeight(16);
        this.image.setFitWidth(16);
    }

    public void setImage(ImageView value) {
        image = value;
    }

    public ImageView getImage() {
        return image;
    }
}
