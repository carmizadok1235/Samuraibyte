package com.example.learning_gamedev.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.learning_gamedev.R;
import com.example.learning_gamedev.interfaces.BitmapMethods;
import com.example.learning_gamedev.main.MainActivity;

public enum HeartImages implements BitmapMethods {
    HEART_ZERO(R.drawable.heart_zero),
    HEART_ONE(R.drawable.heart_one),
    HEART_TWO(R.drawable.heart_two),
    HEART_THREE(R.drawable.heart_three),
    HEART_FOUR(R.drawable.heart_four);

    private Bitmap image;
    private float height, width;
    HeartImages(int resId) {
        options.inScaled = false;
        this.image = this.getBitmapResized(BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resId, options));
        this.height = this.image.getHeight();
        this.width = this.image.getWidth();
    }
    public float getHeight(){
        return this.height;
    }
    public float getWidth(){
        return this.width;
    }
    public Bitmap getImage(){
        return this.image;
    }
}
