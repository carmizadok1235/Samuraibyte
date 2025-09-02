package com.example.learning_gamedev.interfaces;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.example.learning_gamedev.GameConstants;

public interface BitmapMethods {
    BitmapFactory.Options options = new BitmapFactory.Options();
    default Bitmap getBitmapResized(Bitmap bitmap){
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()* GameConstants.SpriteSizes.SCALE_SIZE, bitmap.getHeight()*GameConstants.SpriteSizes.SCALE_SIZE, false);
    }
    default Bitmap rotateBitmap(Bitmap bitmap, int degrees){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
    }
}
