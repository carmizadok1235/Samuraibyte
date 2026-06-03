package com.example.SamuraiByte.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.SamuraiByte.R;
import com.example.SamuraiByte.interfaces.BitmapMethods;
import com.example.SamuraiByte.main.MainActivity;
import com.example.SamuraiByte.GameConstants;

public enum Weapons implements BitmapMethods {
    LANCE(R.drawable.lance);

    private Bitmap weaponImage;
    private Bitmap up, down, left, right;
    Weapons(int resId) {
        options.inScaled = false;
        this.weaponImage = this.getBitmapResized(BitmapFactory.decodeResource(MainActivity.getContext().getResources(), resId, options));
        this.down  = this.weaponImage;
        this.up    = this.rotateBitmap(this.weaponImage, 180);
        this.left  = this.rotateBitmap(this.weaponImage, 90);
        this.right = this.rotateBitmap(this.weaponImage, -90);
    }

    public Bitmap getWeaponImage(int dir){
        return switch (dir){
            case GameConstants.WalkingDirection.DOWN -> this.down;
            case GameConstants.WalkingDirection.UP -> this.up;
            case GameConstants.WalkingDirection.LEFT -> this.left;
            case GameConstants.WalkingDirection.RIGHT -> this.right;
            default -> throw new IllegalStateException("Unexpected Direction");
        };
    }
    public int getWidth(){
        return this.weaponImage.getWidth();
    }
    public int getHeight(){
        return this.weaponImage.getHeight();
    }
}
